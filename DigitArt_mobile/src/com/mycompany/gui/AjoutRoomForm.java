/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

/**
 *
 * @author mohamed
 */

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Room;

import com.mycompany.services.ServiceRoom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class AjoutRoomForm extends BaseForm {
    
    
      Form current;
    public AjoutRoomForm(Resources res ) {
          super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Rooms");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        
        int placeholderWidth = Display.getInstance().getDisplayWidth(); 
        int placeholderHeight = Display.getInstance().getDisplayHeight();
         EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderHeight, placeholderWidth), false);
        String separURL = "http://127.0.0.1:8000/uploads/cf0e6d8a486debf84483cc5caaf34552.jpg";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);
        add(content);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Add a new Room", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("List of Rooms", barGroup);
        partage.setUIID("SelectBar");
       


        partage.addActionListener((e) -> {
               
         
         ListRoomForm a = new ListRoomForm(res);
            a.show();
            refreshTheme();
           
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage)
              
        ));
        mesListes.setSelected(true);
 
        //
        
      
        TextField nameRoom = new TextField("", "enter Room Name!!");
        nameRoom.setUIID("TextFieldBlack");
        addStringValue("Room Name", nameRoom);

        TextField areaField = new TextField("", "enter Area!!");
        areaField.setUIID("TextFieldBlack");
         areaField.setConstraint(TextField.NUMERIC);
        addStringValue("Area", areaField);
        
        
        ComboBox etatCombo = new ComboBox();
        etatCombo.addItem("Available");
        etatCombo.addItem("Unvailable");
        addStringValue("State", etatCombo);
        
       
        
        TextField description = new TextField("", "enter Description!!");
        description.setUIID("TextFieldBlack");
        addStringValue("Description", description);

        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);

        
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                if(nameRoom.getText().equals("") || description.getText().equals("") || areaField.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                 
                    
                    //njibo iduser men session (current user)
                    Room room = new Room();
                    room.setNameRoom(nameRoom.getText());
                    room.setDescription(description.getText());
                    room.setArea(Integer.parseInt(areaField.getText())); // Set the default area value
                     if(etatCombo.getSelectedIndex() == 0 ) {
                            room.setState("Available");
                        }
                        else 
                            room.setState("Unavailable");
                    
                    System.out.println("Room data: " + room);

                    ServiceRoom.getInstance().addRoom(room); // Call the method to add the room
                    iDialog.dispose(); // Hide the loading dialog after adding the room
                    new ListRoomForm(res).show(); // Open the ListRoomForm
                    refreshTheme(); // Refresh the theme

                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }

   

}
