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
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.io.Preferences;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;

public class PaymentForm2 extends BaseForm {
    
    private TextField cardNumberTextField;
    private TextField expirymTextField;
    private TextField expiryyTextField;
    private TextField cvcTextField;
    Form current;

    public PaymentForm2(Resources res, int totalPrice) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        System.out.println(totalPrice);
        setTitle("Payment Powered with STRIPE");
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
        
        
        System.out.println(SessionUser.getEmail());
        ServiceTest.getInstance().Email();
        
 
         // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);
        Label Card = new Label("Card Number");
        Card.getAllStyles().setAlignment(Component.CENTER); 
        Card.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Card.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        
        cardNumberTextField = new TextField();
        cardNumberTextField.getAllStyles().setAlignment(Component.CENTER);
        cardNumberTextField.getAllStyles().setFgColor(0xFFFFFF);
        cardNumberTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label Card1 = new Label("MonthExpiry");
        Card1.getAllStyles().setAlignment(Component.CENTER); 
        Card1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Card1.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        expirymTextField = new TextField();
        expirymTextField.getAllStyles().setAlignment(Component.CENTER);
        expirymTextField.getAllStyles().setFgColor(0xFFFFFF);
        expirymTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label Card2 = new Label("YearExpiry");
        Card2.getAllStyles().setAlignment(Component.CENTER); 
        Card2.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Card2.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        expiryyTextField = new TextField();
        expiryyTextField.getAllStyles().setAlignment(Component.CENTER);
        expiryyTextField.getAllStyles().setFgColor(0xFFFFFF);
        expiryyTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Label Card3 = new Label("CVC NUMBER");
        Card3.getAllStyles().setAlignment(Component.CENTER); 
        Card3.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        Card3.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        cvcTextField = new TextField();
        cvcTextField.getAllStyles().setAlignment(Component.CENTER);
        cvcTextField.getAllStyles().setFgColor(0xFFFFFF);
        cvcTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
 
      Button payButton = new Button("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                handlePayButtonAction(evt,totalPrice,res);
            }
        });
        
     add(Card);   
     add(cardNumberTextField);
     add(Card1); 
     add(expirymTextField);
     add(Card2); 
     add(expiryyTextField);
     add(Card3); 
     add(cvcTextField);
       
     add(payButton);
        
        
           String apiKey = getStripeAPIKey();

        // Initialize Stripe with your API key
        Stripe.apiKey = apiKey;
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
    
     private String getStripeAPIKey() {
        // Retrieve your Stripe API key from preferences or a configuration file
        // Replace this with your actual implementation
        return "sk_test_51MfRIsHcaMLPP7A1X3INIItKLbEljzGYdpTujtvwb4mrggNEJtwS1SG2C6MyxYdz8T2uPVh219jsg7LBZRWSh2Ye00QEgBJZmW";
    }

    private void handlePayButtonAction(ActionEvent event,int price,Resources res) {
        // Retrieve the card details from the text fields
        String cardNumber = cardNumberTextField.getText();
        String monthexpiry = expirymTextField.getText();
        String yearexpiry = expiryyTextField.getText();
        String cvc = cvcTextField.getText();

        // Create a token using the card details
        TokenCreateParams cardParams = TokenCreateParams.builder()
                .setCard(TokenCreateParams.Card.builder()
                        .setNumber(cardNumber)
                        .setExpMonth(monthexpiry)
                        .setExpYear(yearexpiry)
                        .setCvc(cvc)
                        .build())
                .build();

        try {
            Token token = Token.create(cardParams);

            // Confirm the payment with the user
            boolean confirmed = Dialog.show("Confirm Payment", "Are you sure you want to make this payment?", "Yes", "No");

            if (confirmed) {
                // Charge the user's card for the total amount
                int amount = price * 100; // Convert amount to cents
                String currency = "usd";

                ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                        .setAmount((long)amount)
                        .setCurrency(currency)
                        .setSource(token.getId())
                        .build();

                Charge charge = Charge.create(chargeParams);
                  // Payment successful, handle further processing
                 Dialog.show("Payment Success", "Your payment was successful!", "OK", null);
                          // Redirect to PaymentForm (or any other form you want)
                PaymentForm paymentForm = new PaymentForm(res);
                paymentForm.show();
            }
        } catch (StripeException ex) {
            // Handle the exception
            Dialog.show("Payment Error", "There was an error processing your payment. Please check your payment information and try again.", "OK", null);
        }
    }

}
