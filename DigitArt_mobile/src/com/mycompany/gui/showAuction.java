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
        
        ArrayList<Bid> bids;
        bids = BidServices.getInstance().getBids(auction);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/Empty.jpeg" : Statics.BASE_URL + "uploads/" + images.get(0);
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
        
        Label Ending_date = new Label(formattedDate);
        Ending_date.setUIID("CenterLabel");
        
        Button offer = new Button("Offer");
        offer.setUIID("Button2");
        offer.addActionListener(e -> {
            new BidAdd(res, auction, bids).showBack();
        });
        
        String description = auction.getDescription();

// Create a label to display the description
        TextArea descriptionLabel = new TextArea(description);
        descriptionLabel.setEditable(false);
        
        Button backButton = new Button(FontImage.MATERIAL_ARROW_BACK);
        backButton.setUIID("Button2");
        backButton.addActionListener(e -> Statics.previous.showBack());
        
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
        Container btn = new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(Ending_date);
        
        cnt.add(highest_bid);
        
        cnt.add(Description);
        cnt.add(descriptionLabel);
        btn.add(backButton);
        btn.add(offer);
        add(cnt);
        add(btn);
    }
    
    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
}
