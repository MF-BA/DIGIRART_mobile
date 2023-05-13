/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Artwork;
import com.mycompany.services.AuctionServices;
import com.mycompany.services.ServiceArtwork;
import com.mycompany.utils.Statics;
import java.util.ArrayList;

/**
 *
 * @author mohamed
 */
public class ShowArtwork extends BaseForm {

    Form current;

    public ShowArtwork(Resources res, Artwork artwork) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle(artwork.getArtworkName());
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
        addartwork(artwork, res);
    }

    private void addartwork(Artwork artwork, Resources res) {

        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(artwork.getIdArt());
        String imageURL = images.isEmpty() ? Statics.BASE_URL + "/uploads/Empty.jpeg" : Statics.BASE_URL + "/uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);

           
        Label nameArtwork = new Label("Name : "+artwork.getArtworkName(),"NewsTopLine2");
        Label ArtistName = new Label("","NewsTopLine2");
        if(!artwork.getArtistName().isEmpty())
           ArtistName.setText("ArtistName :"+artwork.getArtistName());
        
        else{ArtistName.setText("ArtistName : User");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(artwork.getDateArt());
        Label dateTxt = new Label("Creation date :"+formattedDate);
        dateTxt.setUIID("NewsTopLine2");

        

        String description = artwork.getDescription();

        Label desc = new Label("Description : ","NewsTopLine2");
        TextArea descriptionLabel = new TextArea(description);
        descriptionLabel.setEditable(false);
        
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
            
            if(dig.show("Suppression","Vous voulez supprimer cet oeuvre d'art ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service 
                if(ServiceArtwork.getInstance().deleteArtwork(artwork.getIdArt())) {
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
            new ModifierArtworkForm(res,artwork).show();
        });
        
        
        
        Container buttonsContainer = new Container(new FlowLayout(Component.CENTER));
        buttonsContainer.add(lModifier);
        buttonsContainer.add(lSupprimer);
        
        Button backButton = new Button(FontImage.MATERIAL_ARROW_BACK);
        backButton.addActionListener(e -> new ListArtworkForm(res).show());
        

        Container imgContainer = new Container(new FlowLayout(Component.CENTER));
        imgContainer.add(img);
        
         Container back = new Container(new FlowLayout(Component.CENTER));
        back.add(backButton);
        
        Container cntt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cntt.add(nameArtwork);
        cntt.add(imgContainer);
        cntt.add(dateTxt);
        cntt.add(ArtistName);
        cntt.add(desc);
        cntt.add(descriptionLabel);
        if(Statics.back_end == true)
        cntt.add(buttonsContainer);
        cntt.add(back);
        
        add(cntt);
        
    }

}
