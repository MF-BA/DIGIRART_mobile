/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.users;
import com.mycompany.services.ServiceUsers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;



/**
 *
 * @author venom-1
 */
public class UpdateUserForm extends BaseForm{
     
    Form current;
    public UpdateUserForm(Resources res , users u) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Edit User");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField cin = new TextField(String.valueOf(u.getCin()) , "Cin" , 20 , TextField.ANY);
        TextField firstname = new TextField(u.getFirstname() , "First Name" , 30 , TextField.ANY);
        TextField lastname = new TextField(u.getLastname() , "Last Name" , 30 , TextField.ANY);
        TextField email = new TextField(u.getEmail() , "Email" , 70 , TextField.ANY);
        TextField address = new TextField(u.getAddress() , "Address" , 20 , TextField.ANY);
        TextField phone_num = new TextField(String.valueOf(u.getPhone_number()) , "Phone number" , 20 , TextField.ANY);
        TextField birth_date = new TextField(u.getBirth_date() , "Birth Date" , 20 , TextField.ANY);

        
        //Role 
        
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
        vectorRole.add("Artist");
        vectorRole.add("Subscriber");
        vectorRole.add("Gallery Manager");
        vectorRole.add("Auction Manager");
        vectorRole.add("Events Manager");
        vectorRole.add("Tickets Manager");
        vectorRole.add("Users Manager");
        vectorRole.add("Admin");
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        roles.setSelectedItem(u.getRole());
     
        
        //gender
        Vector<String> vectorGender;
        vectorGender = new Vector();
        
        vectorGender.add("Male");
        vectorGender.add("Female");
        
        ComboBox<String> gender = new ComboBox<>(vectorGender);
        
        gender.setSelectedItem(u.getGender());
      
        
        //status
        Vector<String> vectorStatus;
        vectorStatus = new Vector();
        
        vectorStatus.add("unblocked");
        vectorStatus.add("blocked");
        
        ComboBox<String> status = new ComboBox<>(vectorStatus);
        
        status.setSelectedItem(u.getStatus());
   
        
       
        
        
        
        
        cin.setUIID("NewsTopLine");
        firstname.setUIID("NewsTopLine");
        lastname.setUIID("NewsTopLine");
        email.setUIID("NewsTopLine");
        address.setUIID("NewsTopLine");
        phone_num.setUIID("NewsTopLine");
        birth_date.setUIID("NewsTopLine");
        
        
        
        cin.setSingleLineTextArea(true);
        firstname.setSingleLineTextArea(true);
        lastname.setSingleLineTextArea(true);
        email.setSingleLineTextArea(true);
        address.setSingleLineTextArea(true);
        phone_num.setSingleLineTextArea(true);
        birth_date.setSingleLineTextArea(true);
        
        Button btnModifier = new Button("Update");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
                 /*String dateString = .substring(0, 10); // Extract the date string from the full string
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Create a SimpleDateFormat object with the expected date format
                 Date birthDate = format.parse(dateString); // Parse the date string into a Date object
                 String formattedDate = format.format(birthDate); // Format the Date object into a String using the SimpleDateFormat object*/
                  
                 System.out.println(Integer.parseInt(cin.getText()));
                System.out.println(birth_date.getText());
                 
                 u.setCin(Integer.parseInt(cin.getText()));
                 u.setFirstname(firstname.getText());
                 u.setLastname(lastname.getText());
                 u.setEmail(email.getText());
                 u.setGender(gender.getSelectedItem());
                 u.setAddress(address.getText());
                 u.setStatus(status.getSelectedItem());
                 u.setRole(roles.getSelectedItem());
                 u.setPhone_number(Integer.parseInt(phone_num.getText()));
                 u.setBirth_date(birth_date.getText());
               
                 //appel fonction modfier reclamation men service
                 
                 if(ServiceUsers.getInstance().UpdateUser(u)) { // if true
                     new ListUsersForm(res).show();
                 }      
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListUsersForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(cin),
                createLineSeparator(),
                new FloatingHint(firstname),
                createLineSeparator(),
                new FloatingHint(lastname),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(address),
                createLineSeparator(),
                new FloatingHint(phone_num),
                createLineSeparator(),
                new FloatingHint(birth_date),
                createLineSeparator(),
                gender,
                createLineSeparator(),
                roles,
                createLineSeparator(),
                status,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
}
