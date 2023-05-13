/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServiceTicket;
import java.util.Date;

/**
 *
 * @author User
 */
public class UpdateTicketForm extends BaseForm{
    
    Form current;
    public UpdateTicketForm(Resources res , Ticket t) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField date = new TextField(t.getTicket_date() , "Start Date" , 32 , TextField.ANY);
        date.getAllStyles().setAlignment(Component.CENTER);
        date.getAllStyles().setFgColor(0xff0000);
        date.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        TextField edate = new TextField(t.getTicket_edate() , "End Date" , 32 , TextField.ANY);
        edate.getAllStyles().setAlignment(Component.CENTER);
        edate.getAllStyles().setFgColor(0xff0000);
        edate.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        TextField price = new TextField(String.valueOf(t.getPrice()) , "Price" , 32 , TextField.ANY);
        price.getAllStyles().setAlignment(Component.CENTER);
        price.getAllStyles().setFgColor(0xff0000);
        price.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        TextField type = new TextField(String.valueOf(t.getTicket_type()) , "Type" , 20 , TextField.ANY);
        type.getAllStyles().setAlignment(Component.CENTER);
        type.getAllStyles().setFgColor(0xff0000);
        type.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
        ComboBox typeCombo = new ComboBox();
        typeCombo.addItem("Teen");
        typeCombo.addItem("Student");
        typeCombo.addItem("Adult");
        
        if (t.getTicket_type() == "Teen") {
            typeCombo.setSelectedIndex(0);
        } else if (t.getTicket_type() == "Student") {
            typeCombo.setSelectedIndex(1);
        } else if (t.getTicket_type() == "Adult"){
            typeCombo.setSelectedIndex(2);
         }
       
        date.setUIID("NewsTopLine");
        edate.setUIID("NewsTopLine");
        price.setUIID("NewsTopLine");
        type.setUIID("NewsTopLine");
        
        date.setSingleLineTextArea(true);
        edate.setSingleLineTextArea(true);
        price.setSingleLineTextArea(true);
        type.setSingleLineTextArea(true);
        
       
        Button btnModifier = new Button("Update");
        btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
    btnModifier.addPointerPressedListener(l -> { 

        // Check if price is greater than 0
        if (Integer.parseInt(price.getText()) <= 0) {
            Dialog.show("Invalid price", "Price should be greater than 0", "Cancel", "OK");
            return;
        }
        
         if (Integer.parseInt(price.getText()) <= 0) {
                    Dialog.show("Invalid price", "Price should be greater than 0", "Cancel", "OK");
                    return;
                }

        if (date == null || edate == null || price.getText().equals("")) {
                    Dialog.show("Please check the data", "", "Cancel", "OK");
          }
        t.setTicket_date(date.getText());
        t.setTicket_edate(edate.getText());
        t.setPrice(Integer.parseInt(price.getText()));

        if (typeCombo.getSelectedIndex() == 0) {
            t.setTicket_type("Teen");
        } else if (typeCombo.getSelectedIndex() == 1) {
            t.setTicket_type("Student");
        } else {
            t.setTicket_type("Adult");
        }

        // Call the updateTicket method from the Ticket service to update the data in the database
        if(ServiceTicket.getInstance().updateTicket(t)) { // if true
            new DisplayTicketForm(res).show();
        }
    });

       
       Button btnDelete = new Button("Delete");
       btnDelete.addActionListener(e -> {
           Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Delete","Are you sure you want to delete ticket ?","Cancel","Yes")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(ServiceTicket.getInstance().DeleteTicket(t.getTicket_id())) {
                    new DisplayTicketForm(res).show();
                }
       });
       
       Button btnAnnuler = new Button("Cancel");
       btnAnnuler.addActionListener(e -> {
           new DisplayTicketForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
       Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(date),
                createLineSeparator(),
                new FloatingHint(edate),
                createLineSeparator(),
                new FloatingHint(price),
                createLineSeparator(),
                typeCombo,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnDelete,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
    
}
