/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServiceTicket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author User
 */
public class AddTicketForm extends BaseForm {

    Form current;

    public AddTicketForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        setTitle("Ticket Add");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        s1.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        s2.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        int placeholderWidth = Display.getInstance().getDisplayWidth();
        int placeholderHeight = Display.getInstance().getDisplayHeight();
        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderHeight, placeholderWidth), false);
        String separURL = "http://127.0.0.1:8000/uploads/bc53385fe56f95467c51bbcb40b16412.jpg";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();
        content.add(imageLab);

        add(content);
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Ticket List", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Add Ticket", barGroup);
        partage.setUIID("SelectBar");

        mesListes.addActionListener((e) -> {

            DisplayTicketForm a = new DisplayTicketForm(res);
            a.show();
            refreshTheme();
        });

        partage.addActionListener((e) -> {
            AddTicketForm b = new AddTicketForm(res);
            b.show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage)
        ));

        partage.setSelected(true);

        //
        Picker startDatePicker = new Picker();
        startDatePicker.setType(Display.PICKER_TYPE_DATE);
        startDatePicker.setUIID("TextFieldBlack");
        addStringValue("Start Date", startDatePicker);

        Picker endDatePicker = new Picker();
        endDatePicker.setType(Display.PICKER_TYPE_DATE);
        endDatePicker.setUIID("TextFieldBlack");
        addStringValue("End Date", endDatePicker);

        // create the combo box component for ticket type
        ComboBox<String> ticketTypeComboBox = new ComboBox<>("Teen", "Adult", "Student");
        addStringValue("Ticket Type", ticketTypeComboBox);

        TextField price = new TextField("", "entrer price!!");
        price.setUIID("TextFieldBlack");
        addStringValue("price", price);

        Button btnAjouter = new Button("ADD");
        addStringValue("", btnAjouter);

        Button btnReturn = new Button("Return");
        addStringValue("", btnReturn);

        //onclick button event 
        btnAjouter.addActionListener((e) -> {

            try {
                // Check if start date is before end date
                if (startDatePicker.getDate().getTime() > endDatePicker.getDate().getTime()) {
                    Dialog.show("Invalid date range", "Start date should be before end date", "Cancel", "OK");
                }

                // Check if price is greater than 0
                if (Integer.parseInt(price.getText()) <= 0) {
                    Dialog.show("Invalid price", "Price should be greater than 0", "Cancel", "OK");
                    return;
                }

                if (startDatePicker.getDate() == null || endDatePicker.getDate() == null || price.getText().equals("")) {
                    Dialog.show("Please check the data", "", "Cancel", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Date startDateValue = startDatePicker.getDate();
                    Date endDateValue = endDatePicker.getDate();
                    int priceValue = Integer.parseInt(price.getText());
                    String typeValue = ticketTypeComboBox.getSelectedItem();

                    String startDateString = dateFormat.format(startDateValue);
                    String endDateString = dateFormat.format(endDateValue);

                    // create a new Ticket object with the entered data
                    Ticket r = new Ticket(startDateString, endDateString, priceValue, typeValue);

                    System.out.println("data ticket == " + r);

                    // call the addTicket method from the Ticket service to add the data to the database
                    ServiceTicket.getInstance().addTicket(r);

                    iDialog.dispose(); // dismiss the loading dialog after the data is added

                    // show the updated DisplayTicketForm with the new data
                    new DisplayTicketForm(res).show();

                    refreshTheme(); // actualisation

                }

            } catch (NumberFormatException ex) {
                Dialog.show("Invalid input", "Please enter a valid number for price", "Cancel", "OK");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

    }

    private void addStringValue(String s, Component v) {
        Label L = new Label(s, "PaddedLabel");
        L.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
                v.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

        add(BorderLayout.west(L)
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x353537));
    }
    
    

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

}
