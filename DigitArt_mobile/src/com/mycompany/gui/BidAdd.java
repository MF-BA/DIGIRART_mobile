/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Auction;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Static;
import com.mycompany.myapp.BaseForm;
import com.mycompany.services.AuctionServices;
import com.mycompany.services.BidServices;
import java.util.ArrayList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author fedi1
 */
public class BidAdd extends BaseForm {

    public BidAdd(Resources res, Auction auction, ArrayList<Bid> bids) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
     
        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(1000, 110), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);
        add(content);

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/Empty.jpeg" : Static.BASE_URL + "uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");

        Button offer = new Button("Make an offer");
        offer.setUIID("Button2");

        Button backButton = new Button("Cancel", FontImage.MATERIAL_ARROW_BACK, "Button2");
        backButton.addActionListener(e -> new showAuction(res, auction).showBack());

        bids = BidServices.getInstance().getBids(auction);
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
        Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        highest_bid.setVerticalAlignment(CENTER);
        highest_bid.getUnselectedStyle().setFont(mediumBoldSystemFont);
        
        
        int next_offer;
        if (highestBid != null) {
            next_offer = highestBid.getOffer() + auction.getIncrement();

        } else {
            next_offer = auction.getStarting_price();

        }

        TextField bid = new TextField(String.valueOf(next_offer));
        bid.setUIID("TextFieldBlack");
        bid.setConstraint(TextField.NUMERIC);

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(highest_bid);
        cnt.add(bid);
        offer.addActionListener(e -> {
            if (bid.getText().isEmpty()) {
                Dialog.show("Offer is empty", "The offer should be filled !!", "OK", null);

            } else {
                if (Integer.parseInt(bid.getText()) >= next_offer) {
                    if (BidServices.getInstance().addBid(new Bid(1, auction.getId_auction(), Integer.parseInt(bid.getText())))) {
                        Dialog.show("Offer submitted", "The offer is submitted successfully !!", "OK", null);
                    }
                    new showAuction(res, auction).showBack();

                } else {
                    Dialog.show("Offer is too low", "The offer should be at least " + next_offer, "OK", null);

                }

            }

        });
        cnt.add(offer);
        cnt.add(backButton);
        add(cnt);

    }
}
