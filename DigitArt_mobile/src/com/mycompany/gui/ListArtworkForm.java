/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Artwork;
import com.mycompany.services.ServiceArtwork;
import java.util.ArrayList;

/**
 *
 * @author mohamed
 */
public class ListArtworkForm extends BaseForm{
    
        Form current;
    public ListArtworkForm(Resources res ) {
          super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Artworks");
        getContentPane().setScrollVisible(false);
        
        
          super.addSideMenu(res);
        

        int placeholderWidth = Display.getInstance().getDisplayWidth(); 
        int placeholderHeight = Display.getInstance().getDisplayHeight();
         EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderHeight, placeholderWidth), false);
        String separURL = "http://127.0.0.1:8000/uploads/04c65335d567a6c9a3fbd0c6a42b3f7d.jpg";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);
        add(content);
      


        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Add a new Artwork", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("List of Artworks", barGroup);
        partage.setUIID("SelectBar");
        


        mesListes.addActionListener((e) -> {
               
        
         AjoutArtworkForm a = new AjoutArtworkForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage)
               
        ));

        partage.setSelected(true);
       
       
      
     
      
        //Appel affichage methode
        ArrayList<Artwork>list = ServiceArtwork.getInstance().displayArtworks();
        
        for(Artwork rec : list ) {

               addButton(rec,res);

                Container containerImg = new Container();
                
             
        }
        
        
        
    }
    
    

    private void addButton(Artwork rec , Resources res) {
        
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        
        
      Button image = new Button();
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        
        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label nameArtwork = new Label("Name : "+rec.getArtworkName(),"NewsTopLine2");
        Label ArtistName = new Label("","NewsTopLine2");
        if(!rec.getArtistName().isEmpty())
           ArtistName.setText("ArtistName :"+rec.getArtistName());
        
        else{ArtistName.setText("ArtistName : User");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(rec.getDateArt());
        Label dateTxt = new Label("reation date :"+formattedDate);
        dateTxt.setUIID("NewsTopLine2");

        
        Label nameRoom = new Label("Room name : "+rec.getIdRoom(),"NewsTopLine2");// nom depuis l id room
        Label DescriptionTxt = new Label("Description : "+rec.getDescription(),"NewsTopLine2" );
        
        createLineSeparator();
        

       
        
        //supprimer button
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce reclamation ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service 
                if(ServiceArtwork.getInstance().deleteArtwork(rec.getIdArt())) {
                    new ListArtworkForm(res).show();
                }
           
        });
        
        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);
        lModifier.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ModifierArtworkForm(res,rec).show();
        });
        
        
        
        Button more_info = new Button("more information");
         more_info.addPointerPressedListener(l -> {
            //System.out.println("hello update");
            new ShowArtwork(res,rec).show();
            refreshTheme();
        });  
        
       
        
        
        cnt.add(BorderLayout.WEST,BoxLayout.encloseY(
                
                BoxLayout.encloseX(nameArtwork),
                BoxLayout.encloseX(dateTxt),
                BoxLayout.encloseX(ArtistName),
                BoxLayout.encloseX(DescriptionTxt),
                BoxLayout.encloseX(nameRoom),
                BoxLayout.encloseX(lModifier,lSupprimer)
               
        
        ));
        
        
        add(cnt);
        add(more_info);
    }
    
   
   
    
    
    
}