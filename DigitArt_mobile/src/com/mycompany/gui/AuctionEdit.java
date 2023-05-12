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
import com.codename1.ui.FontImage;
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
public class AuctionEdit extends BaseForm {

    public AuctionEdit(Resources res, Auction auction) {

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

        addForm(res, auction);

    }

    public void addForm(Resources res, Auction auction) {

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(auction.getDate());
        datePicker.setUIID("TextFieldBlack");

        TextField Starting_Price = new TextField(String.valueOf(auction.getStarting_price()), "Starting Price", 20, TextArea.ANY);
        Starting_Price.setUIID("TextFieldBlack");
        Starting_Price.setConstraint(TextField.NUMERIC);

        TextField Increment = new TextField(String.valueOf(auction.getIncrement()), "Increment", 20, TextArea.ANY);
        Increment.setUIID("TextFieldBlack");
        Increment.setConstraint(TextField.NUMERIC);

        TextField Description = new TextField(auction.getDescription(), "Description", 20, TextArea.ANY);
        Description.setUIID("TextFieldBlack");

        addStringValue("Ending Date", datePicker);

        addStringValue("Starting Price", Starting_Price);

        addStringValue("Increment", Increment);

        addStringValue("Description", Description);

        Button back = new Button(FontImage.MATERIAL_ARROW_BACK);
        back.addActionListener(e -> {
            new showAuction(res, auction).showBack();
        });
        Button save = new Button("Save");
        save.addActionListener(e -> {
            if (Starting_Price.getText().isEmpty()) {
                Dialog.show("Starting Price", "Starting Price is empty !!", "OK", null);
            } else if (DateUtil.compare(datePicker.getDate(), new Date()) < 0) {
                Dialog.show("Ending Date", "Ending Date must be after today's date!!", "OK", null);
            } else {

                Auction add = new Auction(auction.getId_auction(), Integer.valueOf(Starting_Price.getText()), Integer.valueOf(Increment.getText()), auction.getId_artwork(), datePicker.getDate(), Description.getText());

                if (AuctionServices.getInstance().EditAuction(add)) {
                    Dialog.show("Auction modified", "The Auction is saved successfully !!", "OK", null);
                    new AuctionDisplay(res).showBack();
                } else {
                    Dialog.show("Error while saving ", "Error submitting auction. Please try again later!!", "OK", null);
                    updateNativeOverlay();
                }
            }
        }
        );
        addStringValue("", back);
        addStringValue("", save);

    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
