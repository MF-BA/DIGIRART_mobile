/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
import java.util.ArrayList;
import static java.util.Collections.list;

/**
 *
 * @author kossay
 */
public class ListEventForm extends BaseForm {

    Form current;

    public ListEventForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Event");
        getContentPane().setScrollVisible(false);

        // Set the background color to black
        this.getAllStyles().setBgColor(0x000000);
        tb.addSearchCommand(e -> {

        });
        addSideMenu(res);
        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("event_bg.jpg"), "", "", res);

        //
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("List of Events", barGroup);
        mesListes.setUIID("SelectBar");

        RadioButton partage = RadioButton.createToggle("Add Event", barGroup);
        partage.setUIID("SelectBar");

        partage.addActionListener((e) -> {

            AjoutEventForm a = new AjoutEventForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage)
        ));

        mesListes.setSelected(true);

        //Appel affichage methode
        ArrayList<Event> list = ServiceEvent.getInstance().affichageEvent();

        for (Event rec : list) {
            String urlImage = "event_bg.jpg";//image statique pour le moment ba3d taw fi  videos jayin nwarikom image 

            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(urlim, rec, res);

            ScaleImageLabel image = new ScaleImageLabel(urlim);

            Container containerImg = new Container();

            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }

        Button redirectButton = new Button("Display Statistics");
        redirectButton.addActionListener(e -> {
            StatisticsEvent statisticsEvent = new StatisticsEvent(res);
            statisticsEvent.show();
        });
        add(redirectButton);
    }

    private void addStringValue(String s, Component v) {
        Label L = new Label(s, "PaddedLabel");
        L.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        v.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        add(BorderLayout.west(L)
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0x353537));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Image img, Event event, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

//        Label event_name = new Label("Event name : "+event.getEventName(),"NewsTopLine2");
//        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(event_name)));
//        
//        Label detail = new Label("Detail : "+event.getDetail(),"NewsTopLine2");
//        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(detail)));
        Label event_name = new Label("Event_name: " + event.getEventName(), "NewsTopLine2");
        Label detail = new Label("Details : " + event.getDetail(), "NewsTopLine2");
        Label Start_date = new Label("Start Date : " + event.getStartDate(), "NewsTopLine2");
        Label End_date = new Label("End Date : " + event.getEndDate(), "NewsTopLine2");
        Label nbparticipants = new Label("Nb Participants : " + event.getNbParticipants(), "NewsTopLine2");
        Label start_time = new Label("Start Time: " + event.getStartTime() + "h", "NewsTopLine2");
        event_name.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        detail.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        Start_date.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        End_date.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        nbparticipants.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white
        start_time.getAllStyles().setFgColor(0xFFFFFF); // Set the foreground color to white

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

            if (dig.show("Suppression", "Vous voulez supprimer ce reclamation ?", "Annuler", "Oui")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //n3ayto l suuprimer men service Reclamation
            if (ServiceEvent.getInstance().deleteEvent(event.getId())) {
                new ListEventForm(res).show();
                refreshTheme();
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
            new ModifyEventForm(res, event).show();
        });

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(event_name),
                BoxLayout.encloseX(detail),
                BoxLayout.encloseX(nbparticipants),
                BoxLayout.encloseX(start_time),
                BoxLayout.encloseX(Start_date),
                BoxLayout.encloseX(End_date),
                BoxLayout.encloseX(lModifier, lSupprimer)));

        add(cnt);
    }

}
