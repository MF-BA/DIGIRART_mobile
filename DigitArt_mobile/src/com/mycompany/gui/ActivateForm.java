package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
import com.mycompany.utils.Statics;

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
       

        Email = new TextField("", "Enter your Email", 20, TextField.ANY);
        Email.setSingleLineTextArea(false);

        Button sendmailbtn = new Button("Send");

        Button signIn = new Button("Return to sign in");
        signIn.addActionListener(e -> previous.showBack());//yarja3 lel window ely9ablha
        signIn.setUIID("CenterLink");

        Container content = BoxLayout.encloseY(
                new FloatingHint(Email),
                createLineSeparator(),
                sendmailbtn,
                signIn
        );

        content.setScrollableY(true);

        add(BorderLayout.CENTER, content);

        sendmailbtn.requestFocus();

        sendmailbtn.addActionListener(e -> {

            /*InfiniteProgress ip = new InfiniteProgress();
            
          final Dialog ipDialog =  ip.showInfiniteBlocking();*/
            

            String codepass = ServiceUsers.getInstance().getPasswordCodeByEmail(Email.getText(), res);
           
             int decimalIndex = codepass.indexOf(".");
              String integerPart = codepass.substring(0, decimalIndex);

            Statics.codepwd = integerPart;
            Statics.Emailpwd = Email.getText();
            
            new CodepwdForm(res).show();
             
        });
    }
    
}
