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
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
import com.mycompany.utils.Statics;

/**
 *
 * @author venom-1
 */
public class CodepwdForm extends BaseForm {
    
    
    public CodepwdForm(Resources res) {
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
        
        TextField codeverif = new TextField("", "Enter the code sent by email", 20, TextField.ANY);
        codeverif.setSingleLineTextArea(false);
       /* Email = new TextField("", "saisir votre email", 20, TextField.ANY);
        */

        Button confirmbtn = new Button("Confirm");

        Button signIn = new Button("Return to previous page");
        signIn.addActionListener(e -> previous.showBack());//yarja3 lel window ely9ablha
        signIn.setUIID("CenterLink");

        Container content = BoxLayout.encloseY(
                new FloatingHint(codeverif),
                createLineSeparator(),
                confirmbtn,
                signIn
        );

        content.setScrollableY(true);

        add(BorderLayout.CENTER, content);

        confirmbtn.requestFocus();

        confirmbtn.addActionListener(e -> {
   
            System.out.println(Statics.codepwd);
            System.out.println(codeverif.getText());
            if(Statics.codepwd.equals(codeverif.getText()))
            {
              Dialog.show("Password","You can now enter a new password",new Command("OK")); 
              new NewPwdForm(res).show();
            }
            else
            {
                Dialog.show("Password","wrong code please check again!",new Command("OK")); 
            }
            
        });
    }
    
}
