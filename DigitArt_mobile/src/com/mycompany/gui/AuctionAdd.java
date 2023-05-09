/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Calendar;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import static com.codename1.ui.events.ActionEvent.Type.Calendar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.util.DateUtil;
import com.mycompany.entities.Artwork;
import com.mycompany.entities.Auction;
import com.mycompany.myapp.BaseForm;
import com.mycompany.services.AuctionServices;
import com.mycompany.utils.Static;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.DatePicker;

/**
 *
 * @author fedi1
 */
public class AuctionAdd extends BaseForm {

    public AuctionAdd(Resources res) {

        super("Auction", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton Display = RadioButton.createToggle("Auction", barGroup);
        Display.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Add to Auction", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(1000, 110), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);

        add(content);
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, Display, add),
                FlowLayout.encloseBottom(arrow)
        ));
        Display.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(add, arrow);
        });

        bindButtonSelection(Display, arrow);
        bindButtonSelection(add, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        Display.addActionListener(e -> {
            new AuctionDisplay(res, Static.cnt).showBack();
        });

        addForm(res);

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    public void addForm(Resources res) {

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
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
        ArrayList<Artwork> matchingArtworks = new ArrayList<Artwork>();
//        if(user is not admin )
// Create a new ArrayList to hold the matching Artwork objects

// Iterate through the artworks_array list and check each Artwork object for a match
        for (Artwork artwork : artworks_array) {
            if (artwork.getIdArtist() == 1) {
                // If the Artwork object matches the criteria, add it to the matchingArtworks list
                matchingArtworks.add(artwork);
            }
        }

        // else matchingArtworks = artworks_array ;
        for (int i = 0; i < matchingArtworks.size(); i++) {
            artworks.addItem(matchingArtworks.get(i).getArtworkName());
        }
//        //artworks

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add("datePicker");
        cnt.add(datePicker);
        cnt.add("Starting_Price");
        cnt.add(Starting_Price);
        cnt.add("Increment");
        cnt.add(Increment);
        cnt.add("Description");
        cnt.add(Description);
        cnt.add("artworks");
        cnt.add(artworks);

        Button save = new Button("Save");
        save.setUIID("Button2");
        save.addActionListener(e -> {

            if (Starting_Price.getText().isEmpty()) {
                Dialog.show("Starting Price", "Starting Price is empty !!", "OK", null);
            } else if (DateUtil.compare(datePicker.getDate(), new Date())< 0) {
                Dialog.show("Ending Date", "Ending Date must after today's date!!", "OK", null);
            } else {
                Artwork choisen = matchingArtworks.get(artworks.getSelectedIndex());
                Auction add = new Auction( Integer.valueOf(Starting_Price.getText()), Integer.valueOf(Increment.getText()),choisen.getIdArt(), datePicker.getDate(), Description.getText());
                //        public Auction(int starting_price, int increment, int id_artwork, Date date, String description) {

                if (AuctionServices.getInstance().addAuction(add)) {
                    Dialog.show("Auction submitted", "The Auction is saved successfully !!", "OK", null);
                    new AuctionDisplay(res).showBack();

                } else {
                    Dialog.show("Error while saving ", "Error submitting auction. Please try again later!!", "OK", null);
                    updateNativeOverlay();
                }
            }
            //System.out.println(choisen.getIdArt());
        }
        );

        cnt.add(save);

        add(cnt);

    }

}
