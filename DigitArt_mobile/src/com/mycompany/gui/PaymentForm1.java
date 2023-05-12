package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Payment;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServicePayment;
import com.mycompany.services.ServiceTicket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentForm1 extends Form {

    Form current;

    public PaymentForm1(Resources res, java.util.Date selectedDate) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        setTitle("Payment");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Step 1", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Step 2", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Step 3", barGroup);
        partage.setUIID("SelectBar");
    
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        liste.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            liste.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Label selectedDateLabel = new Label("Selected Date: " + dateFormat.format(selectedDate));
        selectedDateLabel.getAllStyles().setAlignment(Component.CENTER);
        selectedDateLabel.getAllStyles().setFgColor(0x000000);
        selectedDateLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        add(selectedDateLabel);

        // Create a hidden label to store the selected date
        Label DateDB = new Label();
        DateDB.setVisible(false);
        DateDB.setText(dateFormat.format(selectedDate));
        // Add the hidden label to the form
        add(DateDB);

        ServiceTicket.getInstance().getPrices(selectedDate);

        // Access prices through getter methods in ServiceTicket class
        int priceStudent = ServiceTicket.getInstance().getPriceStudent();
        int priceAdult = ServiceTicket.getInstance().getPriceTeen();
        int priceTeen = ServiceTicket.getInstance().getPriceAdult();
        
        System.out.println(priceStudent);
        System.out.println(priceTeen);
        System.out.println(priceAdult);
        Label PaymentForm = new Label("Payment Form");
        PaymentForm.getAllStyles().setAlignment(Component.CENTER);
        PaymentForm.getAllStyles().setFgColor(0x000000);
        PaymentForm.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        add(PaymentForm);
        // Add ComboBoxes and labels for each ticket type
        Label teenagerLabel = new Label("Adult(price: $" + priceTeen + "/per Ticket)");
        
        ComboBox<Integer> comboBoxS = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxS.addItem(i);
        }
        if (priceTeen == 0) {
        comboBoxS.setVisible(false);
        teenagerLabel.setVisible(false);
        } else {
        comboBoxS.setEnabled(true);
        add(teenagerLabel);
        add(comboBoxS);
        }


        Label studentLabel = new Label("Student(price: $" + priceStudent + "/per Ticket)");
        ComboBox<Integer> comboBoxA = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxA.addItem(i);
        }
         if (priceStudent == 0) {
        comboBoxA.setVisible(false);
        studentLabel.setVisible(false);
        } else {
        comboBoxA.setEnabled(true);
        add(studentLabel);
        add(comboBoxA);
        }
 

        Label adultLabel = new Label("Teenager (price: $" + priceAdult + "/per Ticket)");
        ComboBox<Integer> comboBoxT = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxT.addItem(i);
        }
        if (priceAdult == 0) {
        comboBoxT.setVisible(false);
        adultLabel.setVisible(false);
        } else {
        comboBoxT.setEnabled(true);
        add(adultLabel);
        add(comboBoxT);
        }

        // Add a space between the ComboBoxes and the total label
        add(new Label(""));

        // Add a label to display the total price as a string with a dollar sign
        Label totalLabel = new Label("Total price: $0");
        totalLabel.getAllStyles().setAlignment(Component.CENTER);
        totalLabel.getAllStyles().setFgColor(0xff0000);
        totalLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));

        add(totalLabel);
        Label priceLabel = new Label("0");
                


        // Add a listener to update the total price when a ComboBox value changes
        comboBoxS.addActionListener(e -> updateTotalPrice(priceLabel,totalLabel, comboBoxS, comboBoxA, comboBoxT, priceStudent, priceTeen, priceAdult));
        comboBoxA.addActionListener(e -> updateTotalPrice(priceLabel,totalLabel, comboBoxS, comboBoxA, comboBoxT, priceStudent, priceTeen, priceAdult));
        comboBoxT.addActionListener(e -> updateTotalPrice(priceLabel,totalLabel, comboBoxS, comboBoxA, comboBoxT, priceStudent, priceTeen, priceAdult));

        Button btnAjouter = new Button("ADD");
        addStringValue("", btnAjouter);

        //onclick button event 
        btnAjouter.addActionListener((e) -> {

            try {

                if (priceLabel.getText().equals("0")) {
                    Dialog.show("Please select tickets before proceeding payment!", "", "Cancel", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    int userid=0;
                    int nbAdult = comboBoxS.getSelectedItem();
                    int nbTeenager = comboBoxA.getSelectedItem();
                    int nbStudent = comboBoxT.getSelectedItem();
                    String selectedDateStr = DateDB.getText();
                    int totalPayment = Integer.parseInt(priceLabel.getText());
                    boolean paid=true;
                    
                    
                     Payment p = new Payment( userid,selectedDateStr, nbAdult,nbTeenager,nbStudent, totalPayment, paid);
                            
                    System.out.println("data ticket == " + p);

                    // call the addTicket method from the Ticket service to add the data to the database
                    ServicePayment.getInstance().addPayment(p);

                    iDialog.dispose(); // dismiss the loading dialog after the data is added

                    // show the updated DisplayTicketForm with the new data
                    PaymentForm2 form = new PaymentForm2(res);
                    form.show();

                    refreshTheme(); // actualisation

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        
        Button btnReturn = new Button("Return");
        btnReturn.addActionListener(e -> {
        PaymentForm form = new PaymentForm(res);
            form.show();
        });
        addStringValue("", btnReturn);

    }

    private void updateTotalPrice(Label priceLabel,Label totalLabel, ComboBox<Integer> comboBoxS, ComboBox<Integer> comboBoxA, ComboBox<Integer> comboBoxT,int price1,int price2,int price3) {
        int studentPrice = comboBoxS.getSelectedItem() * price2;
        int adultPrice = comboBoxA.getSelectedItem() * price1;
        int teenagerPrice = comboBoxT.getSelectedItem() * price3;
        int totalPrice = studentPrice + adultPrice + teenagerPrice;
        totalLabel.setText("Total price: $" + totalPrice);
        totalLabel.setText("Total price: $" + totalPrice);
        priceLabel.setText(String.valueOf(totalPrice));
    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
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
