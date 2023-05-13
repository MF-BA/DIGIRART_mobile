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
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.util.DateUtil;
import com.mycompany.entities.Artwork;
import com.mycompany.entities.Auction;
import com.mycompany.services.AuctionServices;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author fedi1
 */
public class AuctionAdd extends BaseForm {

    public AuctionAdd(Resources res) {

        super("Auction", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();

        //  ListReclamationForm a = new ListReclamationForm(res);
        //  a.show();
        //refreshTheme();
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
        add.setSelected(true);
        arrow.setVisible(false);

        Display.addActionListener(e -> {
            new AuctionDisplay(res).showBack();
        });

        addForm(res);

    }

    public void addForm(Resources res) {

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        datePicker.setUIID("TextFieldBlack");
        //datePicker

        TextField Starting_Price = new TextField("", "Starting Price", 20, TextArea.ANY);
        Starting_Price.setUIID("TextFieldBlack");
        Starting_Price.setConstraint(TextField.NUMERIC);
        //Starting_Price

        TextField Increment = new TextField("10", "Increment", 20, TextArea.ANY);
        Increment.setUIID("TextFieldBlack");
        Increment.setConstraint(TextField.NUMERIC);
        //Increment

        TextField Description = new TextField("", "Description", 20, TextArea.ANY);
        Description.setUIID("TextFieldBlack");
        //Description

        ComboBox<String> artworks = new ComboBox<>();

        ArrayList<Artwork> artworks_array = AuctionServices.getInstance().getArtworkNames();

        if (!Statics.back_end) {
            // Iterate through the artworks_array list and check each Artwork object for a match
            Iterator<Artwork> iterator = artworks_array.iterator();
            while (iterator.hasNext()) {
                Artwork artwork = iterator.next();
                if (artwork.getIdArtist() != 1) {
                    // Remove the Artwork object from the artworks_array list
                    iterator.remove();
                }
            }
        }
        // else matchingArtworks = artworks_array ;
        for (int i = 0; i < artworks_array.size(); i++) {
            artworks.addItem(artworks_array.get(i).getArtworkName());
        }
        addStringValue("Ending Date", datePicker);
        addStringValue("Starting Price", Starting_Price);
        addStringValue("Increment", Increment);
        addStringValue("Description", Description);
        addStringValue("artworks", artworks);
        Button save = new Button("Save");
        save.addActionListener(e -> {
            if (Starting_Price.getText().isEmpty()) {
                Dialog.show("Starting Price", "Starting Price is empty !!", "OK", null);
            } else if (DateUtil.compare(datePicker.getDate(), new Date()) < 0) {
                Dialog.show("Ending Date", "Ending Date must be after today's date!!", "OK", null);
            } else {
                Artwork choisen = artworks_array.get(artworks.getSelectedIndex());
                Auction add = new Auction(Integer.valueOf(Starting_Price.getText()), Integer.valueOf(Increment.getText()), choisen.getIdArt(), datePicker.getDate(), Description.getText());
                //        public Auction(int starting_price, int increment, int id_artwork, Date date, String description) {

                if (AuctionServices.getInstance().addAuction(add)) {
                    Dialog.show("Auction submitted", "The Auction is saved successfully !!", "OK", null);
                    new AuctionDisplay(res).showBack();

                } else {
                    Dialog.show("Error while saving ", "Error submitting auction. Please try again later!!", "OK", null);
                    updateNativeOverlay();
                }
            }
        }
        );
        addStringValue("",save);
    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
