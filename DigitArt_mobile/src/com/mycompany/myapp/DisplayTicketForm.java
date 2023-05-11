/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServiceTicket;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class DisplayTicketForm extends BaseForm {

    Form current;

    public DisplayTicketForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        setTitle("Ticket Add");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
           super.addSideMenu(res);
        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        int placeholderWidth = Display.getInstance().getDisplayWidth(); 
        int placeholderHeight = Display.getInstance().getDisplayHeight();
         EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderHeight, placeholderWidth), false);
        String separURL = "http://127.0.0.1:8000/uploads/bc53385fe56f95467c51bbcb40b16412.jpg";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();
        content.add(imageLab);
        
        add(content);
        addTab(swipe, s1, res.getImage("profile-background.jpg"), "", "", res);
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Ticket List", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Add Ticket", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {

            DisplayTicketForm a = new DisplayTicketForm(res);
            a.show();
            refreshTheme();
        });

        partage.addActionListener((e) -> {
            AddTicketForm b = new AddTicketForm(res);
            b.show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes,partage),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

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

        //  ListReclamationForm a = new ListReclamationForm(res);
        //  a.show();
        refreshTheme();

        Button btnAjouter = new Button("Statistics");
        addStringValue("", btnAjouter);

        //onclick button event 
        btnAjouter.addActionListener((e) -> {
            
             StatisticsTicket a = new StatisticsTicket(res);
         a.show();
        });
        //Appel affichage methode
        ArrayList<Ticket> list = ServiceTicket.getInstance().DisplayTicket();
        System.out.println(ServiceTicket.getInstance().DisplayTicket());
        for (Ticket tick : list) {
            addButton(tick, res);

        }

    }

    
    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
    private void addButton(Ticket rec, Resources res) {
        Container newCnt = new Container();
        newCnt.setLayout(new BorderLayout());

        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label dateTxt = new Label("Start Date : " + rec.getTicket_date(), "NewsTopLine2");
        Label edatetTxt = new Label("End Date : " + rec.getTicket_edate(), "NewsTopLine2");
        Label priceTxt = new Label("Price : " + rec.getPrice(), "NewsTopLine2");
        Label typeTxt = new Label("Type : " + rec.getTicket_type(), "NewsTopLine2");
        //createLineSeparator();

        /*if(rec.getEtat() == 0 ) {
            etatTxt.setText("non Traitée");
        }
        else 
            etatTxt.setText("Traitée");
         */
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

            Dialog dig = new Dialog("Delete");

            if (dig.show("Delete", "Are you sure you want to delete ticket ?", "Cancel", "Yes")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //n3ayto l suuprimer men service Reclamation
            if (ServiceTicket.getInstance().DeleteTicket(rec.getTicket_id())) {
                new DisplayTicketForm(res).show();
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
            System.out.println("hello update");
            new UpdateTicketForm(res, rec).show();
        });

        newCnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(dateTxt),
                BoxLayout.encloseX(edatetTxt),
                BoxLayout.encloseX(priceTxt),
                BoxLayout.encloseX(typeTxt),
                BoxLayout.encloseX(lSupprimer, lModifier)));

        add(newCnt);
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

        swipe.addTab("", res.getImage("profile-background.jpg"), page1);

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

}
