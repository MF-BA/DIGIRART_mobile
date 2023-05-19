/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
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
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Room;

import com.mycompany.services.ServiceRoom;

/**
 *
 * @author Mohamed
 */
public class ModifierRoomForm extends BaseForm {

    Form current;

    public ModifierRoomForm(Resources res, Room r) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modif Room");
        getContentPane().setScrollVisible(false);

        // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);

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
        TextField nameRoom = new TextField(r.getNameRoom());
        nameRoom.setUIID("TextFieldBlack");
        addStringValue("Room Name", nameRoom);

        TextField areaField = new TextField(r.getArea());
        areaField.setUIID("TextFieldBlack");
        addStringValue("Area", areaField);

        ComboBox etatCombo = new ComboBox();
        etatCombo.addItem("Available");
        etatCombo.addItem("Unvailable");
        addStringValue("State", etatCombo);
        etatCombo.setSelectedItem(r.getState());

        TextArea description = new TextArea(r.getDescription());
        description.setUIID("TextFieldBlack");
        addStringValue("Description", description);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
        addStringValue("", btnModifier);

        //Event onclick btnModifer
        btnModifier.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {
                r.setNameRoom(nameRoom.getText());
                r.setDescription(description.getText());
                r.setArea(Integer.parseInt(areaField.getText()));
                r.setState(etatCombo.getSelectedItem().toString());

                //appel fonction modfier reclamation men service
                if (ServiceRoom.getInstance().updateRoom(r.getIdRoom(), r)) { // if true
                    new ListRoomForm(res).show();
                }
            }
        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListRoomForm(res).show();
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
