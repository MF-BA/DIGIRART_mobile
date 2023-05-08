/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycomany.entities.Room;
import com.mycompany.services.ServiceRoom;

/**
 *
 * @author Mohamed
 */
public class ModifierRoomForm extends BaseForm {
    
    Form current;
    public ModifierRoomForm(Resources res , Room r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modif Room");
        getContentPane().setScrollVisible(false);
        
        
       // super.addSideMenu(res);
        
        TextField NameRoom = new TextField(r.getNameRoom() , "NameRoom" , 0 , TextField.ANY);
        TextField description = new TextField(r.getDescription() , "Description" , 0 , TextField.ANY);
        TextField Area = new TextField(String.valueOf(r.getArea()) , "Area" , 0 , TextField.ANY);
  ComboBox etatCombo = new ComboBox();
        etatCombo.addItem("Available");
        etatCombo.addItem("Unvailable");
        etatCombo.setSelectedItem(r.getState());
         addStringValue("State", etatCombo);
        
        
     
        
        
        
        
        NameRoom.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        Area.setUIID("NewsTopLine");
        
        NameRoom.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        Area.setSingleLineTextArea(true);
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent l) {
                 r.setNameRoom(NameRoom.getText());
                 r.setDescription(description.getText());
                 r.setArea(Integer.parseInt(Area.getText()));
                 r.setState(etatCombo.getSelectedItem().toString());
                 
                 
                 
                 
                 //appel fonction modfier reclamation men service
                 
                 if(ServiceRoom.getInstance().updateRoom(r.getIdRoom(),r)) { // if true
                     new ListRoomForm(res).show();
                 }     }
         });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListRoomForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(NameRoom),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                Area,
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }

    
         private void addStringValue(String s, Component v) {
        
        add(BorderLayout.north(new Label(s,"PaddedLabel"))
        .add(BorderLayout.SOUTH,v));
        add(createLineSeparator(0xeeeeee));
    }

    }


