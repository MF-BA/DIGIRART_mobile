package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServiceTicket;
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

        // Add ComboBoxes and labels for each ticket type
        Label studentLabel = new Label("Student ticket (price: $5)");
        
        ComboBox<Integer> comboBoxS = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxS.addItem(i);
        }
        add(studentLabel);
        add(comboBoxS);

        Label adultLabel = new Label("Adult ticket (price: $10)");
        ComboBox<Integer> comboBoxA = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxA.addItem(i);
        }
        add(adultLabel);
        add(comboBoxA);

        Label teenagerLabel = new Label("Teenager ticket (price: $7)");
        ComboBox<Integer> comboBoxT = new ComboBox<>();
        for (int i = 0; i <= 10; i++) {
            comboBoxT.addItem(i);
        }
        add(teenagerLabel);
        add(comboBoxT);

        // Add a space between the ComboBoxes and the total label
        add(new Label(""));

        // Add a label to display the total price
        Label totalLabel = new Label("Total price: $0");
        add(totalLabel);

        // Add a listener to update the total price when a ComboBox value changes
        comboBoxS.addActionListener(e -> updateTotalPrice(totalLabel, comboBoxS, comboBoxA, comboBoxT));
        comboBoxA.addActionListener(e -> updateTotalPrice(totalLabel, comboBoxS, comboBoxA, comboBoxT));
        comboBoxT.addActionListener(e -> updateTotalPrice(totalLabel, comboBoxS, comboBoxA, comboBoxT));
    }

    private void updateTotalPrice(Label totalLabel, ComboBox<Integer> comboBoxS, ComboBox<Integer> comboBoxA, ComboBox<Integer> comboBoxT) {
        int studentPrice = comboBoxS.getSelectedItem() * 5;
        int adultPrice = comboBoxA.getSelectedItem() * 10;
        int teenagerPrice = comboBoxT.getSelectedItem() * 7;
        int totalPrice = studentPrice + adultPrice + teenagerPrice;
        totalLabel.setText("Total price: $" + totalPrice);
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
