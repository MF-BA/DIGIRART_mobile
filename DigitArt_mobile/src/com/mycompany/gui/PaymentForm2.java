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
import com.mycompany.services.ServiceTest;
import com.mycompany.services.ServiceTicket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentForm2 extends BaseForm {

    Form current;

    public PaymentForm2(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        setTitle("Payment");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Step 1", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Step 2", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Step 3", barGroup);
        partage.setUIID("SelectBar");
    
      
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage)
        ));

        partage.setSelected(true);
        
        
        
        ServiceTest.getInstance().Email();
        
 
         // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);



        // Add the thank you text
        Label thankYouLabel = new Label("Thanks for your purchase!");
        thankYouLabel.setUIID("ThankYouLabel");
        thankYouLabel.getAllStyles().setAlignment(Component.CENTER); 
        thankYouLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        // Set the foreground color of the thank you text to white
        thankYouLabel.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

        // Add the thank you text
        Label Email = new Label("An email has been sent to you ! ");
        Email.setUIID("ThankYouLabel");
        Email.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        Email.getAllStyles().setAlignment(Component.CENTER); 
        Email.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        
        Label Check = new Label("Check it Out!");
        Check.setUIID("ThankYouLabel");
        Check.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        Check.getAllStyles().setAlignment(Component.CENTER); 
        Check.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));

        add(thankYouLabel);
        add(Email);
        add(Check);
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
