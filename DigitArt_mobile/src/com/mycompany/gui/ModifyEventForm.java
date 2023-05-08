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
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
/**
 *
 * @author kossay
 */
public class ModifyEventForm extends BaseForm{
     
    Form current;
    public ModifyEventForm(Resources res , Event r) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
        
        
        super.addSideMenu(res);
        
        TextField event_name = new TextField(r.getEventName() , "Event Name" , 20 , TextField.ANY);
        TextField nb_participants = new TextField(String.valueOf(r.getNbParticipants()), "Nb Participants" , 20 , TextField.ANY);
        TextField detail = new TextField(r.getDetail() , "Details" , 20 , TextField.ANY);
        TextField start_time = new TextField(String.valueOf(r.getStartTime()) , "Start Time" , 20 , TextField.ANY);
        TextField color = new TextField(r.getColor() , "Color" , 20 , TextField.ANY);
 
        //etat bch na3mlo comobbox bon lazm admin ya3mlleha approuver mais just chnwarikom ComboBox
        
       
        
        
        
        
        event_name.setUIID("NewsTopLine");
        nb_participants.setUIID("NewsTopLine");
        detail.setUIID("NewsTopLine");
        start_time.setUIID("NewsTopLine");
        color.setUIID("NewsTopLine");
        
        event_name.setSingleLineTextArea(true);
        nb_participants.setSingleLineTextArea(true);
        detail.setSingleLineTextArea(true);
        start_time.setSingleLineTextArea(true);
        color.setSingleLineTextArea(true);
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setEventName(event_name.getText());
           r.setNbParticipants(Integer.parseInt(nb_participants.getText()));
           r.setDetail(detail.getText());
           r.setStartTime(Integer.parseInt(start_time.getText()));
           r.setColor(color.getText());
           
      
       
       //appel fonction modfier reclamation men service
       
       if(ServiceEvent.getInstance().modifierReclamation(r)) { // if true
           new ListEventForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListEventForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(event_name),
                createLineSeparator(),
                new FloatingHint(detail),
                createLineSeparator(),
                new FloatingHint(color),
                createLineSeparator(),//ligne de séparation
                 new FloatingHint(nb_participants),
                createLineSeparator(),
                 new FloatingHint(start_time),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
        
        
    }
}
