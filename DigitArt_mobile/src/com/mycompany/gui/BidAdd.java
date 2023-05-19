/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Auction;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Statics;
import com.mycompany.services.AuctionServices;
import com.mycompany.services.BidServices;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author fedi1
 */
public class BidAdd extends BaseForm {

    Label message = new Label("Currency change");
    ArrayList<String> symbols = new ArrayList<String>();

    public BidAdd(Resources res, Auction auction, ArrayList<Bid> bids) {
        super("Newsfeed", BoxLayout.y());
        set_symbols();
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);
        
        // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);
        
        super.addSideMenu(res);
        InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();

        
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
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/Empty.jpeg" : Statics.BASE_URL + "/uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");
        Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");
        title.getUnselectedStyle().setFont(largeBoldSystemFont);
        title.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

        Button offer = new Button("Make an offer");

        Button backButton = new Button(FontImage.MATERIAL_ARROW_BACK);
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
        highest_bid.setVerticalAlignment(CENTER);
        highest_bid.getUnselectedStyle().setFont(mediumBoldSystemFont);
        highest_bid.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

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
                    if (BidServices.getInstance().addBid(new Bid(SessionUser.getId(), auction.getId_auction(), Integer.parseInt(bid.getText())))) {
                        Dialog.show("Offer submitted", "The offer is submitted successfully !!", "OK", null);
                        new showAuction(res, auction).showBack();
                    } else {
                        Dialog.show("Error while submitted", "Error submitting offer. Please try again later !!", "OK", null);
                        updateNativeOverlay();
                    }

                } else {
                    Dialog.show("Offer is too low", "The offer should be at least " + next_offer, "OK", null);

                }

            }

        });
        add(cnt);
        add(createLineSeparator(0xeeeeee));

        ComboBox<String> currencies = new ComboBox();
        for (int i = 0; i < symbols.size(); i++) {
            currencies.addItem(symbols.get(i));
        }
        currencies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double result = BidServices.getInstance().currSymbols(symbols.get(currencies.getSelectedIndex()), Integer.parseInt(bid.getText()));

                message.setText(bid.getText() + "$ is equivalent to " + result + symbols.get(currencies.getSelectedIndex()));
            }
        });
        message.setVerticalAlignment(CENTER);
        message.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        addStringValue("", currencies);
        addStringValue("", message);

        addStringValue("", offer);
        addStringValue("", backButton);

    }

    private void addStringValue(String s, Component v) {
        Label L = new Label(s, "PaddedLabel");
        L.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
                v.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

        add(BorderLayout.west(L)
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x353537));
    }

    private void set_symbols() {
        symbols.add("AED");
        symbols.add("AFN");
        symbols.add("ALL");
        symbols.add("AMD");
        symbols.add("ANG");
        symbols.add("AOA");
        symbols.add("ARS");
        symbols.add("AUD");
        symbols.add("AWG");
        symbols.add("AZN");
        symbols.add("BAM");
        symbols.add("BBD");
        symbols.add("BDT");
        symbols.add("BGN");
        symbols.add("BHD");
        symbols.add("BIF");
        symbols.add("BMD");
        symbols.add("BND");
        symbols.add("BOB");
        symbols.add("BRL");
        symbols.add("BSD");
        symbols.add("BTC");
        symbols.add("BTN");
        symbols.add("BWP");
        symbols.add("BYN");
        symbols.add("BYR");
        symbols.add("BZD");
        symbols.add("CAD");
        symbols.add("CDF");
        symbols.add("CHF");
        symbols.add("CLF");
        symbols.add("CLP");
        symbols.add("CNY");
        symbols.add("COP");
        symbols.add("CRC");
        symbols.add("CUC");
        symbols.add("CUP");
        symbols.add("CVE");
        symbols.add("CZK");
        symbols.add("DJF");
        symbols.add("DKK");
        symbols.add("DOP");
        symbols.add("DZD");
        symbols.add("EGP");
        symbols.add("ERN");
        symbols.add("ETB");
        symbols.add("EUR");
        symbols.add("FJD");
        symbols.add("FKP");
        symbols.add("GBP");
        symbols.add("GEL");
        symbols.add("GGP");
        symbols.add("GHS");
        symbols.add("GIP");
        symbols.add("GMD");
        symbols.add("GNF");
        symbols.add("GTQ");
        symbols.add("GYD");
        symbols.add("HKD");
        symbols.add("HNL");
        symbols.add("HRK");
        symbols.add("HTG");
        symbols.add("HUF");
        symbols.add("IDR");
        symbols.add("ILS");
        symbols.add("IMP");
        symbols.add("INR");
        symbols.add("IQD");
        symbols.add("IRR");
        symbols.add("ISK");
        symbols.add("JEP");
        symbols.add("JMD");
        symbols.add("JOD");
        symbols.add("JPY");
        symbols.add("KES");
        symbols.add("KGS");
        symbols.add("KHR");
        symbols.add("KMF");
        symbols.add("KPW");
        symbols.add("KRW");
        symbols.add("KWD");
        symbols.add("KYD");
        symbols.add("KZT");
        symbols.add("LAK");
        symbols.add("LBP");
        symbols.add("LKR");
        symbols.add("LRD");
        symbols.add("LSL");
        symbols.add("LTL");
        symbols.add("LVL");
        symbols.add("LYD");
        symbols.add("MAD");
        symbols.add("MDL");
        symbols.add("MGA");
        symbols.add("MKD");
        symbols.add("MMK");
        symbols.add("MNT");
        symbols.add("MOP");
        symbols.add("MRO");
        symbols.add("MUR");
        symbols.add("MVR");
        symbols.add("MWK");
        symbols.add("MXN");
        symbols.add("MYR");
        symbols.add("MZN");
        symbols.add("NAD");
        symbols.add("NGN");
        symbols.add("NIO");
        symbols.add("NOK");
        symbols.add("NPR");
        symbols.add("NZD");
        symbols.add("OMR");
        symbols.add("PAB");
        symbols.add("PEN");
        symbols.add("PGK");
        symbols.add("PHP");
        symbols.add("PKR");
        symbols.add("PLN");
        symbols.add("PYG");
        symbols.add("QAR");
        symbols.add("RON");
        symbols.add("RSD");
        symbols.add("RUB");
        symbols.add("RWF");
        symbols.add("SAR");
        symbols.add("SBD");
        symbols.add("SCR");
        symbols.add("SDG");
        symbols.add("SEK");
        symbols.add("SGD");
        symbols.add("SHP");
        symbols.add("SLE");
        symbols.add("SLL");
        symbols.add("SOS");
        symbols.add("SRD");
        symbols.add("STD");
        symbols.add("SVC");
        symbols.add("SYP");
        symbols.add("SZL");
        symbols.add("THB");
        symbols.add("TJS");
        symbols.add("TMT");
        symbols.add("TND");
        symbols.add("TOP");
        symbols.add("TRY");
        symbols.add("TTD");
        symbols.add("TWD");
        symbols.add("TZS");
        symbols.add("UAH");
        symbols.add("UGX");
        symbols.add("UYU");
        symbols.add("UZS");
        symbols.add("VEF");
        symbols.add("VES");
        symbols.add("VND");
        symbols.add("VUV");
        symbols.add("WST");
        symbols.add("XAF");
        symbols.add("XAG");
        symbols.add("XAU");
        symbols.add("XCD");
        symbols.add("XDR");
        symbols.add("XOF");
        symbols.add("XPF");
        symbols.add("YER");
        symbols.add("ZAR");
        symbols.add("ZMK");
        symbols.add("ZMW");
        symbols.add("ZWL");
    }

}
