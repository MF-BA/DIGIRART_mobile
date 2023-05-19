package com.mycompany.gui;

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

public class PaiementStripe extends Form {
    private TextField cardNumberTextField;
    private TextField expirymTextField;
    private TextField expiryyTextField;
    private TextField cvcTextField;
  

    public PaiementStripe(Resources theme) {
        super(new BorderLayout());

        cardNumberTextField = new TextField();
        cardNumberTextField.getAllStyles().setAlignment(Component.CENTER);
        cardNumberTextField.getAllStyles().setFgColor(0xff0000);
        cardNumberTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        expirymTextField = new TextField();
        expirymTextField.getAllStyles().setAlignment(Component.CENTER);
        expirymTextField.getAllStyles().setFgColor(0xff0000);
        expirymTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        expiryyTextField = new TextField();
        expiryyTextField.getAllStyles().setAlignment(Component.CENTER);
        expiryyTextField.getAllStyles().setFgColor(0xff0000);
        expiryyTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        cvcTextField = new TextField();
        cvcTextField.getAllStyles().setAlignment(Component.CENTER);
        cvcTextField.getAllStyles().setFgColor(0xff0000);
        cvcTextField.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        
            // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);
        
        Button payButton = new Button("Pay");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                handlePayButtonAction(evt);
            }
        });

        // Set up the form layout
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        setTitle("Payment");
        add(new SpanLabel("Enter your payment details:"));
        add(cardNumberTextField);
        add(expirymTextField);
        add(expiryyTextField);
        add(cvcTextField);
       
        add(payButton);

        // Load your Stripe API key from preferences or a configuration file
        String apiKey = getStripeAPIKey();

        // Initialize Stripe with your API key
        Stripe.apiKey = apiKey;
    }

    private String getStripeAPIKey() {
        // Retrieve your Stripe API key from preferences or a configuration file
        // Replace this with your actual implementation
        return "sk_test_51MfRIsHcaMLPP7A1X3INIItKLbEljzGYdpTujtvwb4mrggNEJtwS1SG2C6MyxYdz8T2uPVh219jsg7LBZRWSh2Ye00QEgBJZmW";
    }

    private void handlePayButtonAction(ActionEvent event) {
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
                int amount = 100 * 100; // Convert amount to cents
                String currency = "usd";

                ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                        .setAmount((long)amount)
                        .setCurrency(currency)
                        .setSource(token.getId())
                        .build();

                Charge charge = Charge.create(chargeParams);
                  // Payment successful, handle further processing
                handlePaymentSuccess(charge);
            }
        } catch (StripeException ex) {
            // Handle the exception
            Dialog.show("Payment Error", "There was an error processing your payment. Please check your payment information and try again.", "OK", null);
        }
    }

    private void handlePaymentSuccess(Charge charge) {
        // Handle the payment success
        // This method will be called when the payment is successfully processed
        Dialog.show("Payment Success", "Your payment was successful!", "OK", null);

        // Add your own logic here for further processing, such as updating the UI or storing the payment details
    }
}

               

