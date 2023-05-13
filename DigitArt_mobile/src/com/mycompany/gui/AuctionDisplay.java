/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Auction;
import com.mycompany.utils.Statics;
import com.mycompany.services.AuctionServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author fedi1
 */
public class AuctionDisplay extends BaseForm {


    public AuctionDisplay(Resources res) {

        super("Auction", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);

        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        int placeholderWidthh = Display.getInstance().getDisplayWidth();
        int placeholderHeightt = Display.getInstance().getDisplayHeight();
        EncodedImage placeholderImageseparatorr = EncodedImage.createFromImage(Image.createImage(placeholderHeightt, placeholderWidthh), false);
        String separURLL = "http://127.0.0.1:8000/uploads/cf0e6d8a486debf84483cc5caaf34552.jpg";
        Image separatorIMGG = URLImage.createToStorage(placeholderImageseparatorr, separURLL, separURLL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabb = new ScaleImageLabel(separatorIMGG);
        imageLabb.setUIID("LogoLabel");

        Container contentt = new Container();

        contentt.add(imageLabb);
        add(contentt);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton Display = RadioButton.createToggle("Auction", barGroup);
        Display.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Add to Auction", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, Display, add),
                FlowLayout.encloseBottom(arrow)
        ));
        Display.setSelected(true);
        arrow.setVisible(false);

        InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();

        //  ListReclamationForm a = new ListReclamationForm(res);
        //  a.show();
//            refreshTheme();
        ArrayList<Auction> auctions = AuctionServices.getInstance().getAllAuctions();
        for (int i = 0; i < auctions.size(); i++) {
            addauction(auctions.get(i), res);
        }

        add.addActionListener(e -> {
            Statics.previous = this;
            new AuctionAdd(res).show();
        });
    }

    private void addauction(Auction auction, Resources res) {
//        int height = Display.getInstance().convertToPixels(11.5f);
//        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? Statics.BASE_URL + "uploads/Empty.jpeg" : Statics.BASE_URL + "/uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");
        title.getStyle().setAlignment(Component.CENTER);
        title.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        Date date = auction.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(date);
        Label countdownLabel = new Label(formattedDate);
        countdownLabel.setUIID("CenterLabel");

        // create a new thread that will run the countdown timer
        Thread countdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String countdownTime = findDifference(date);
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            countdownLabel.setText(countdownTime);
                        }
                    });
                    try {
                        Thread.sleep(1000); // pause for 1 second
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        countdownThread.start(); // start the countdown timer thread

         
        
        Button more_info = new Button("more information");
       

        more_info.addActionListener(e -> {
            Statics.previous = this;
            new showAuction(res, auction).show();
        });

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderWidth, 100), false);
        String separURL = Statics.BASE_URL + "/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(countdownLabel);
        cnt.add(imageLab);
        add(cnt);
        addStringValue("", more_info);
    }

    public static String findDifference(Date endDateTime) {
        // Calculate time difference in milliseconds
        long differenceInMillis = endDateTime.getTime() - new Date().getTime();

        long differenceInSeconds = differenceInMillis / 1000;
        long differenceInMinutes = differenceInSeconds / 60;
        long differenceInHours = differenceInMinutes / 60;
        long differenceInDays = differenceInHours / 24;

        // Calculate remaining hours, minutes and seconds
        differenceInHours = differenceInHours % 24;
        differenceInMinutes = differenceInMinutes % 60;
        differenceInSeconds = differenceInSeconds % 60;

        // Build the result string
        String result = differenceInDays + " days, "
                + differenceInHours + " hours, "
                + differenceInMinutes + " minutes, "
                + differenceInSeconds + " seconds.";

        // Return the result string
        return result;
    }
    
     private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
