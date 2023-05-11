/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
/*import java.util.Properties;
import java.util.Random;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/
/*import com.mycompany.services.ServiceUsers;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {
    
    TextField Email;
    public ActivateForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
            TextField code = new TextField("", "saisir le code", 20, TextField.ANY);
            TextField newPassword = new TextField("", "saisir le nouveau mot de passe", 20, TextField.PASSWORD);

        Email = new TextField("","saisir votre email",20,TextField.ANY);
        Email.setSingleLineTextArea(false);
        
        Button sendmailbtn = new Button("Send");
        
        Button signIn = new Button("Return to sign in");
        signIn.addActionListener( e-> previous.showBack());//yarja3 lel window ely9ablha
        signIn.setUIID("CenterLink");
        
        Container content = BoxLayout.encloseY(
        
                new FloatingHint(Email),
                createLineSeparator(),
                sendmailbtn,
                
                signIn
        );
        
        content.setScrollableY(true);
        
        add(BorderLayout.CENTER,content);
        
        sendmailbtn.requestFocus();
        
        sendmailbtn.addActionListener(e -> {
            
          /*InfiniteProgress ip = new InfiniteProgress();
            
          final Dialog ipDialog =  ip.showInfiniteBlocking();*/
            
            
          content.replaceAndWait(Email, code, CommonTransitions.createFade(500));
          
          String codepass = ServiceUsers.getInstance().getPasswordCodeByEmail(Email.getText(), res);
          
            /* ipDialog.dispose();
            Dialog.show("Password","Nous avons envoyé le mot de passe a votre e-mail. Veuillez vérifier votre boite de réception",new Command("OK"));
            new SignInForm(res).show();
            refreshTheme();*/
            
        });
    }
    //sendMail
    /*
    public void sendMail(Resources res) {
       try {
            
        //  Optional<String> result = dialog.showAndWait();
        Properties props = new Properties();
        //props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");



Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("digitart.primes@gmail.com","ktknrunncnveaidz");
            }
        });

 //String recipientEmail = result.get();

Random random = new Random();
int resetCode = random.nextInt(1000000); // Generate a random 6-digit code
String emailContent = "Your reset code is: " + resetCode;

Message message = new MimeMessage(session);
message.setFrom(new InternetAddress("digitart.primes@gmail.com"));

message.setRecipients(Message.RecipientType.TO,
InternetAddress.parse(Email.getText()));
message.setSubject("Reset Password");
message.setText(emailContent);
Transport.send(message);
          
        }catch(Exception e ) {
            e.printStackTrace();
        }
    }
    */
}
