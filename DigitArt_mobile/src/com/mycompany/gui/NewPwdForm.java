/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
import com.mycompany.utils.Statics;

/**
 *
 * @author venom-1
 */
public class NewPwdForm extends BaseForm {
    
     public NewPwdForm(Resources res) {
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
        
        TextField newPassword = new TextField("", "Enter new password", 20, TextField.PASSWORD);
        newPassword.setSingleLineTextArea(false);
       /* Email = new TextField("", "saisir votre email", 20, TextField.ANY);
        */

        Button confirmbtn = new Button("Confirm");

        Button signIn = new Button("Return to previous page");
        signIn.addActionListener(e -> previous.showBack());//yarja3 lel window ely9ablha
        signIn.setUIID("CenterLink");

        Container content = BoxLayout.encloseY(
                new FloatingHint(newPassword),
                createLineSeparator(),
                confirmbtn,
                signIn
        );

        content.setScrollableY(true);

        add(BorderLayout.CENTER, content);

        confirmbtn.requestFocus();

        confirmbtn.addActionListener(e -> {

           if(ServiceUsers.getInstance().UpdateUserPwd(Statics.Emailpwd,newPassword.getText()))
           {
               Dialog.show("Password","Password changed successfully ",new Command("OK")); 
              new SignInForm(res).show();
           }
           else
           {
               Dialog.show("Password","something went wrong",new Command("OK"));
           }
            
        });
    }
}
