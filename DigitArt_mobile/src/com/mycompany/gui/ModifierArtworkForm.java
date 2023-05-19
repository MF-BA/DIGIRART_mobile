/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Artwork;
import com.mycompany.entities.Room;
import com.mycompany.entities.users;
import com.mycompany.services.ServiceArtwork;
import com.mycompany.services.ServiceRoom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javafx.scene.control.DatePicker;

/**
 *
 * @author mohamed
 */
public class ModifierArtworkForm extends BaseForm {

    Form current;

    public ModifierArtworkForm(Resources res, Artwork r) {
        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modif Artwork");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

// Set the background color to black
        this.getAllStyles().setBgColor(0x000000);

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
        RadioButton mesListes = RadioButton.createToggle("Add a new Artwork", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("List of Artwork", barGroup);
        partage.setUIID("SelectBar");

        partage.addActionListener((e) -> {

            ListArtworkForm a = new ListArtworkForm(res);
            a.show();
            refreshTheme();

        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage)
        ));
        mesListes.setSelected(true);

        //
        TextField NameArtwork = new TextField(r.getArtworkName());
        NameArtwork.setUIID("TextFieldBlack");
        addStringValue("Artwork Name :", NameArtwork);

        ComboBox<String> owner = new ComboBox<>("Museum", "Artist");
        addStringValue("Owner :", owner);

        TextField ArtistName = new TextField(r.getArtistName());
        ArtistName.setUIID("TextFieldBlack");
        addStringValue("Artist Name :", ArtistName);
        ArtistName.setVisible(false);

        ComboBox<String> artists = new ComboBox<>();
        ArrayList<users> artistsArray = ServiceRoom.getInstance().getArtists();
        System.out.println("Artists Array: " + artistsArray);
        Collections.sort(artistsArray, new Comparator<users>() {
            @Override
            public int compare(users u1, users u2) {
                // Compare the ID values of the two rooms
                return Integer.compare(u1.getId(), u2.getId());
            }
        });

        for (users user : artistsArray) {
            artists.addItem(user.getLastname());
        }
        artists.setVisible(false);
        addStringValue("Artists: ", artists);

        owner.addActionListener(evt -> {
            String selectedItem = owner.getSelectedItem();
            if (selectedItem.equals("Museum")) {
                ArtistName.setVisible(true);
                artists.setVisible(false);
            } else if (selectedItem.equals("Artist")) {
                ArtistName.setVisible(false);
                artists.setVisible(true);
            } else {
                ArtistName.setVisible(false);
                artists.setVisible(false);
            }
        });

        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(new Date());
        addStringValue("Date: ", datePicker);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(datePicker.getDate());
        datePicker.setUIID("TextFieldBlack");

        TextArea description = new TextArea(r.getDescription());
        description.setUIID("TextFieldBlack");
        addStringValue("Description: ", description);

        ComboBox<String> rooms = new ComboBox<>();
        ArrayList<Room> roomsArray = ServiceRoom.getInstance().getAvailableRooms();
        System.out.println("Rooms Array: " + roomsArray);
        Collections.sort(roomsArray, new Comparator<Room>() {
            @Override
            public int compare(Room room1, Room room2) {
                return Integer.compare(room1.getIdRoom(), room2.getIdRoom());
            }
        });

        for (Room room : roomsArray) {
            rooms.addItem(room.getNameRoom());
        }
        addStringValue("Room: ", rooms);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
        addStringValue("", btnModifier);

        //Event onclick btnModifer
        Date today = new Date();
        btnModifier.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {

                if (NameArtwork.getText().isEmpty() || description.getText().isEmpty()) {
                    Dialog.show("Veuillez vérifier les données", "Vous devez remplir tous les champs", "OK", null);
                    return;
                } else if (datePicker.getDate().getTime() >= today.getTime()) {
                    Dialog.show("Veuillez vérifier les données", "the date of creation must be earlier than today", "OK", null);
                    return;
                } else if (NameArtwork.getText().length() <= 5) {
                    Dialog.show("Veuillez vérifier les données", "Le nom de l'œuvre d'art doit comporter plus de 5 caractères", "OK", null);
                    return;
                } else if (description.getText().length() <= 7) {
                    Dialog.show("Veuillez vérifier les données", "La description doit comporter plus de 7 caractères", "OK", null);
                    return;
                }

                r.setArtworkName(NameArtwork.getText());
                r.setArtistName(ArtistName.getText());
                r.setDescription(description.getText());
                r.setDateArt(dateString);
                r.setIdArtist(artistsArray.get(artists.getSelectedIndex()).getId());
                r.setIdRoom(roomsArray.get(rooms.getSelectedIndex()).getIdRoom());

                //appel fonction modfier reclamation men service
                if (ServiceArtwork.getInstance().updateArtwork(r.getIdArt(), r)) { // if true
                    new ListArtworkForm(res).show();
                }
            }
        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListArtworkForm(res).show();
        });

        show();

    }

    private void addStringValue(String s, Component v) {
        Label L = new Label(s, "PaddedLabel");
        L.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        v.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        add(BorderLayout.west(L)
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x353537));
    }

}
