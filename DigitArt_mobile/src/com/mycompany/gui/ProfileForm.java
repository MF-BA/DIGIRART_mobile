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

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;
import java.util.Vector;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        
        Button modiff = new Button("Edit");
        Button Supprimer = new Button("Delete Account");
        
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3, 
                            
                            FlowLayout.encloseCenter(
                                new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
                    )
                )
        ));

        int cn = SessionUser.getCin();
        int ph_num = SessionUser.getPhonenum();
        System.out.println(cn);
         String fn = SessionUser.getFirstname();
        System.out.println(fn);
        String ln = SessionUser.getLastname();
        System.out.println(ln);
        System.out.println(SessionUser.getEmail());
         System.out.println(SessionUser.getPassword());
         System.out.println(SessionUser.getAddress());
         System.out.println(SessionUser.getPhonenum());
         System.out.println(SessionUser.getGender());
         
        TextField cin = new TextField(String.valueOf(cn));
        cin.setUIID("TextFieldBlack");
        addStringValue("Cin", cin);
       
        TextField firstname = new TextField(fn);
        firstname.setUIID("TextFieldBlack");
        addStringValue("First Name", firstname);
        
        TextField lastname = new TextField(ln);
        lastname.setUIID("TextFieldBlack");
        addStringValue("Last Name", lastname);
        
        Label Email = new Label(SessionUser.getEmail());
        Email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", Email);
        
        TextField address = new TextField(SessionUser.getAddress());
        address.setUIID("TextFieldBlack");
        addStringValue("Address", address);
        
        TextField phone = new TextField(String.valueOf(ph_num));
        phone.setUIID("TextFieldBlack");
        addStringValue("Phone number", phone);
        
        //Role 
        //Vector 3ibara ala array 7atit fiha roles ta3na ba3d nzidouhom lel comboBox
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
        vectorRole.add("Artist");
        vectorRole.add("Subscriber");
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        roles.setSelectedItem(SessionUser.getRole());
        addStringValue("Your role", roles);
        
        //gender
        Vector<String> vectorGender;
        vectorGender = new Vector();
        
        vectorGender.add("Male");
        vectorGender.add("Female");
        
        ComboBox<String> gender = new ComboBox<>(vectorGender);
        
        gender.setSelectedItem(SessionUser.getGender());
        addStringValue("Gender", gender);
         
        TextField birthdate = new TextField(SessionUser.getBirthDate());
        birthdate.setUIID("TextFieldBlack");
        addStringValue("Birth Date", birthdate);
        
       /* float Cin;
        Cin = Integer.parseInteger(cin.getText());
        float Phone;
        Phone = Float.parseFloat(phone.getText());*/
       
        addStringValue("",Supprimer);
        addStringValue("",modiff);
        
        modiff.addActionListener((ActionEvent edit)->{
        InfiniteProgress ip = new InfiniteProgress();
        
        //final Dialog ipDlg = ip.showInifinieteBlooking();
        ServiceUsers.EditUser(SessionUser.getId(),cin.getText(),phone.getText(),firstname.getText(),lastname.getText(),address.getText(),gender,roles,birthdate.getText());
        SessionUser.setCin(Integer.valueOf(cin.getText()));
        SessionUser.setFirstname(firstname.getText());
        SessionUser.setLastname(lastname.getText());
        SessionUser.setAddress(address.getText());
        SessionUser.setPhonenum(Integer.valueOf(phone.getText()));
        SessionUser.setGender(gender.getSelectedItem());
        SessionUser.setRole(roles.getSelectedItem());
        SessionUser.setBirthDate(birthdate.getText());
        //SessionUser.setPassword(Password.getText());
        //SessionUser.setEmail(Email.getText());
        Dialog.show("Succes","your profile has been updated successfully! ","OK",null);
       // ipDlg.dispose();
        refreshTheme();
         
    });
        
        Supprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Deletion");
            
            if(dig.show("Deletion","Do you want to delete your account?","Cancel","Yes")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
               
                if(ServiceUsers.getInstance().deleteUser(SessionUser.getId())) {
                    new SignInForm(res).show();
                }
           
        });
       
        
        
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
