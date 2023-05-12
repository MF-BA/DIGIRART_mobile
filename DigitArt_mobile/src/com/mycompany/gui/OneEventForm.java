/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.entities.Participants;
import com.mycompany.services.ServiceEvent;
import com.mycompany.services.ServiceParticipate;
import com.mycompany.utils.Statics;
import java.text.SimpleDateFormat;

/**
 *
 * @author kossay
 */
public class OneEventForm extends Form {

    public OneEventForm(Resources res,Event event) {
        setTitle("Event Details");

        // Create UI components
        Label eventNameLabel = new Label("Event Name: " + event.getEventName(), "NewsTopLine2");
        Label startDateLabel = new Label("Start Date: " + event.getStartDate(), "NewsTopLine2");
        Label endDateLabel = new Label("End Date: " + event.getEndDate(), "NewsTopLine2");
        Label nbParticipantsLabel = new Label("Number of Participants: " + event.getNbParticipants(), "NewsTopLine2");
        Label detailsLabel = new Label("Details: " + event.getDetail(), "NewsTopLine2");

// Set layout and add components to the form
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        addComponent(eventNameLabel);
        addComponent(startDateLabel);
        addComponent(endDateLabel);
        addComponent(nbParticipantsLabel);
        addComponent(detailsLabel);

// Set the dimensions of the poster
        int posterWidth = Display.getInstance().getDisplayWidth(); // Use full screen width
        int posterHeight = Display.getInstance().getDisplayHeight() / 2; // Use half of the screen height

// Create a placeholder image
        Image placeholder = Image.createImage(posterWidth, posterHeight);

        EncodedImage placeholderImage = EncodedImage.createFromImage(placeholder, false);
        String imageURL = Statics.BASE_URL + "/uploads/" + event.getImage();
        Image x = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

// Scale the image to fit the desired size
        x = x.scaled(posterWidth, posterHeight);

// Create a label to hold the image
        Label imageLabel = new Label(x);

// Add the image label to the form
        addComponent(imageLabel);

      Button button = new Button("Participate");
button.addActionListener(e -> {
    // Action to be performed when the button is clicked
    System.out.println("Button clicked!");
          ServiceEvent.getInstance().getPasswordCodeByEmail(SessionUser.getEmail(),res);
    Dialog.show("Congratulations", "You just participated in the event", "OK", null);
});

        add(button);
       
        Button backButton = new Button("Map");
        backButton.addActionListener(e -> {
            MapEvent a = new MapEvent();
            a.show();
        });

        
        //onclick button event 

        button.addActionListener((e) -> {

            try {

//                if(event_name.getText().equals("") || detail.getText().equals("")) {
//                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
//                }
                InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data

                final Dialog iDialog = ip.showInfiniteBlocking();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                //String firstName, String lastName, String adress, String gender, int idEvent, String idUser
                //njibo iduser men session (current user)
                Participants p = new Participants(event.getId(), SessionUser.getId(), SessionUser.getFirstname(), SessionUser.getLastname(), SessionUser.getAddress(), SessionUser.getGender());
                //127.0.0.1:8000/participants/addParticipant/Json?id_event=7&id_user=4&first_name="zizou"&last_name="loukil"&address="soukra"&gender="Female"

                System.out.println("data  reclamation == " + p);

                //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                ServiceParticipate.getInstancee().ajoutParticipate(p);

                iDialog.dispose(); //na7io loading ba3d ma3mlna ajout

                refreshTheme();//Actualisation

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
                Button Display = new Button("Return");

        Display.addActionListener(e -> {
            new EventFrontForm(res).showBack();
        });
        add(Display);
    }

}
