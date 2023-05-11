/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.BaseForm;
import com.mycompany.entities.Auction;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Statics;
import com.mycompany.services.AuctionServices;
import com.mycompany.services.BidServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author fedi1
 */
public class showAuction extends BaseForm {

    public showAuction(Resources res, Auction auction) {

        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();

        //  ListReclamationForm a = new ListReclamationForm(res);
        //  a.show();
//            refreshTheme();
        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(1000, 110), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);
        add(content);
        addauction(auction, res);
    }

    private void addauction(Auction auction, Resources res) {
        Container btn = new Container(new BoxLayout(BoxLayout.X_AXIS));
        ArrayList<Bid> bids;
        bids = BidServices.getInstance().getBids(auction);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/Empty.jpeg" : Statics.BASE_URL + "/uploads/" + images.get(0);
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

        Button backButton = new Button(FontImage.MATERIAL_ARROW_BACK);
        backButton.setUIID("Button2");
        backButton.addActionListener(e -> Statics.previous.showBack());
        btn.add(backButton);
        if (Statics.back_end) {
            if (bids.isEmpty()) {
                        Container btn1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                Button delete = new Button("Delete");
                delete.setUIID("Button2");
                delete.addActionListener(e -> {
                    if (AuctionServices.getInstance().DeleteAuction(auction)) {
                        Dialog.show("Auction Deleted", "The Auction is deleted successfully", "OK", null);
                        new AuctionDisplay(res).showBack();
                    } else {
                        Dialog.show("ERROR", "Error while deleting auction. Please try again later !!", "OK", null);
                    }
                });
                Button edit = new Button("Edit");
                edit.setUIID("Button2");
                edit.addActionListener(e -> {
                    new AuctionEdit(res, auction).show();
                });
                btn1.add(edit);
                btn1.add(delete);
                btn.add(btn1);
            }
        } else {
            Button offer = new Button("Offer");
            offer.setUIID("Button2");
            offer.addActionListener(e -> {
                new BidAdd(res, auction, bids).showBack();
            });
            btn.add(offer);
        }
        String description = auction.getDescription();
// Create a label to display the description
        TextArea descriptionLabel = new TextArea(description);
        descriptionLabel.setEditable(false);

        Bid highestBid = null;
        int highestOffer = Integer.MIN_VALUE;
        for (Bid bid : bids) {
            if (bid.getOffer() > highestOffer) {
                highestBid = bid;
                highestOffer = bid.getOffer();
            }
        }
        Label highest_bid;
        if (highestBid != null) {
            highest_bid = new Label("Highest Offer : " + String.valueOf(highestBid.getOffer()) + "$");
        } else {
            highest_bid = new Label("No offers yet !");
        }
        highest_bid.setUIID("CenterLabel");
        highest_bid.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        Label Description = new Label("Description : ");
        Description.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(countdownLabel);

        cnt.add(highest_bid);

        cnt.add(Description);
        cnt.add(descriptionLabel);

        add(cnt);
        add(btn);
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

}
