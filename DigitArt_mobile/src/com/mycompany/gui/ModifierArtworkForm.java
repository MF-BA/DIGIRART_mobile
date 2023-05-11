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
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Artwork;
import com.mycompany.services.ServiceArtwork;
import java.util.Date;
import javafx.scene.control.DatePicker;

/**
 *
 * @author mohamed
 */
public class ModifierArtworkForm extends BaseForm {
    
    Form current;
    public ModifierArtworkForm(Resources res , Artwork r) {
         super("Newsfeed",BoxLayout.y()); 
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modif Artwork");
        getContentPane().setScrollVisible(false);
        
        
       super.addSideMenu(res);
       
       

        TextField ArtistName = new TextField();
        ArtistName.setUIID("TextFieldBlack");
        
        TextField NameArtwork = new TextField(r.getArtworkName() , "NameArtwork" , 0 , TextField.ANY);
        NameArtwork.setUIID("TextFieldBlack");
        
        if(!r.getArtistName().isEmpty())
           ArtistName.setText(r.getArtistName());
        
        else{
            ComboBox<String> artists = new ComboBox<>();
            
        } 
        
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        
        TextField description = new TextField(r.getDescription() , "Description" , 0 , TextField.ANY);
        description.setUIID("TextFieldBlack");
         
        ComboBox<String> rooms = new ComboBox<>();
        
      
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent l) {
                 r.setArtworkName(NameArtwork.getText());
                 r.setArtistName(ArtistName.getText());
                 r.setDescription(description.getText());
               //  r.setDateArt();
              //   r.setIdArtist();
             //    r.setIdRoom();
                 
                 
                 
                 
                 //appel fonction modfier reclamation men service
                 
                 if(ServiceArtwork.getInstance().updateArtwork(r.getIdArt(),r)) { // if true
                     new ListArtworkForm(res).show();
                 }     }
         });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListArtworkForm(res).show();
       });
       
    
        
        Container content = BoxLayout.encloseY(
              
                new FloatingHint(NameArtwork),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                 new FloatingHint(ArtistName),
                createLineSeparator(),
               
                
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


