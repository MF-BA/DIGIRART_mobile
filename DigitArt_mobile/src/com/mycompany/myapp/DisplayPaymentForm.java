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
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Payment;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServicePayment;
import com.mycompany.services.ServiceTicket;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class DisplayPaymentForm extends Form{
     Form current;

    public DisplayPaymentForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
        setTitle("Ticket Add");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("profile-background.jpg"), "", "", res);
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Ticket List", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Payment List", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Add Ticket", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {

            DisplayTicketForm a = new DisplayTicketForm(res);
            a.show();
            refreshTheme();
        });

        liste.addActionListener((e) -> {
            DisplayPaymentForm paymentListForm = new DisplayPaymentForm(res);
            paymentListForm.show();
        });

        partage.addActionListener((e) -> {
            AddTicketForm b = new AddTicketForm(res);
            b.show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        liste.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(liste, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
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

        //Appel affichage methode
        ArrayList<Payment> list = ServicePayment.getInstance().DisplayPayment();
        System.out.println(ServiceTicket.getInstance().DisplayTicket());
        for (Payment pay : list) {
            addButton(pay, res);

        }

    }

    private void addButton(Payment rec, Resources res) {
        Container newCnt = new Container();
        newCnt.setLayout(new BorderLayout());

        //kif nzidouh  ly3endo date mathbih fi codenamone y3adih string w y5alih f symfony dateTime w ytab3ni cha3mlt taw yjih
        Label dateTxt = new Label("PurchaseDate : " + rec.getPurchaseDate(), "NewsTopLine2");
        Label edatetTxt = new Label("Nb Adult: " + rec.getNbAdult(), "NewsTopLine2");
        Label priceTxt = new Label("Nb Teenager : " + rec.getNbTeenager(), "NewsTopLine2");
        Label typeTxt = new Label("Nb Student: " + rec.getNbStudent(), "NewsTopLine2");
        Label typeTxt1 = new Label("Total Payment: " + rec.getTotalPayment(), "NewsTopLine2");
      
        Label typeTxt2 = new Label("Paid: " + (rec.isPaid() ? "Yes" : "No"), "NewsTopLine2");
        
        newCnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(dateTxt),
                BoxLayout.encloseX(edatetTxt),
                BoxLayout.encloseX(priceTxt),
                BoxLayout.encloseX(typeTxt),
                BoxLayout.encloseX(typeTxt1),
                BoxLayout.encloseX(typeTxt2),
                BoxLayout.encloseX()));

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
