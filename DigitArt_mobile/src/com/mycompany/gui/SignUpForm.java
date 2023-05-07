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
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        TextField cin = new TextField("", "Cin", 20, TextField.ANY);
        TextField firstname = new TextField("", "First Name", 20, TextField.ANY);
        TextField lastname = new TextField("", "Last Name", 20, TextField.ANY);
        TextField Email = new TextField("", "Email", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField address = new TextField("", "Address", 20, TextField.ANY);
        TextField phonenum = new TextField("", "Phone number", 20, TextField.ANY);
           //Role 
        //Vector 3ibara ala array 7atit fiha roles ta3na ba3d nzidouhom lel comboBox
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
        vectorRole.add("Artist");
        vectorRole.add("Subscriber");
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        
        //gender
        Vector<String> vectorGender;
        vectorGender = new Vector();
        
        vectorGender.add("Male");
        vectorGender.add("Female");
        
        ComboBox<String> gender = new ComboBox<>(vectorGender);
        
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        
        TextField birthDateField = new TextField("", "Birth Date", 20, TextField.ANY);
         birthDateField.setHidden(true); 
        
         // add a listener to the date picker to set the value of the text field
    datePicker.addActionListener(e -> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = dateFormat.format(datePicker.getDate());
    birthDateField.setText(dateString);
    birthDateField.setHidden(false);
    });


        cin.setSingleLineTextArea(false);
        firstname.setSingleLineTextArea(false);
        lastname.setSingleLineTextArea(false);
        Email.setSingleLineTextArea(false);
        Button next = new Button("SignUp");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> new SignInForm(res).show());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(cin),
                createLineSeparator(),
                new FloatingHint(firstname),
                createLineSeparator(),
                new FloatingHint(lastname),
                createLineSeparator(),
                new FloatingHint(Email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(address),
                createLineSeparator(),
                new FloatingHint(phonenum),
                createLineSeparator(),
                new FloatingHint(birthDateField),
                createLineSeparator(),
                datePicker,
                createLineSeparator(),
                gender,
                createLineSeparator(),
                roles//sinon y7otich role fi form ta3 signup
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e) -> {
            
            ServiceUsers.getInstance().signup(cin, firstname, lastname, Email, password,address,phonenum,birthDateField,gender,roles,  res);
            Dialog.show("Success","account is saved","OK",null);
            new SignInForm(res).show();
        });
    }
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
}
