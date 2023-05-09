/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.BaseForm;
import com.mycompany.entities.Auction;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Static;
import com.mycompany.services.AuctionServices;
import com.mycompany.services.BidServices;
import java.util.ArrayList;

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
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/Empty.jpeg" : Static.BASE_URL + "uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");

        Label Ending_date = new Label(auction.getDate().toString());
        Ending_date.setUIID("CenterLabel");

        Button offer = new Button("Make an offer");
        offer.setUIID("Button2");
        offer.addActionListener(e -> {
            new BidAdd(res, auction, bids).showBack();
        });

        String description = auction.getDescription();

// Create a label to display the description
        Label descriptionLabel = new Label(description);

// Create a container to hold the label
        Container container = new Container(new BorderLayout());
        container.add(BorderLayout.CENTER, descriptionLabel);

        Button backButton = new Button(FontImage.MATERIAL_ARROW_BACK);
        backButton.setUIID("Button2");
        backButton.addActionListener(e -> Static.previous.showBack());

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

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(Ending_date);
        cnt.add(description);
        cnt.add(highest_bid);
        cnt.add(offer);
        cnt.add(backButton);
        add(cnt);
    }

}
