package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Ticket;
import com.mycompany.services.ServiceTicket;
import java.util.Date;

public class PaymentForm extends Form {

    Form current;

    public PaymentForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        setTitle("Payment");
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Step 1", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Step 2", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Step 3", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            mesListes.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //
        Picker DatePicker = new Picker();
        DatePicker.setType(Display.PICKER_TYPE_DATE);
        DatePicker.setUIID("TextFieldBlack");
        DatePicker.getAllStyles().setAlignment(Component.CENTER);
        DatePicker.getAllStyles().setFgColor(0xff0000);
        DatePicker.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
//        DatePicker.setDate(new java.util.Date());
        
        DatePicker.addActionListener(evt -> {
            if (evt.getSource() instanceof Picker) {
                Picker p = (Picker) evt.getSource();
                java.util.Date date = (java.util.Date) p.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                System.out.println(dateFormat.format(date));
                boolean test=ServiceTicket.getInstance().getPrices(date);

                PaymentForm1 form = new PaymentForm1(res,date);
                form.show();
            }
        });
        Label selectDateLabel = new Label("Please select a date:");
        selectDateLabel.getAllStyles().setAlignment(Component.CENTER);
        selectDateLabel.getAllStyles().setFgColor(0x000000);
        selectDateLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        add(selectDateLabel);

        addStringValue("", DatePicker);

       for (Component cmp : getContentPane()) {
        cmp.getAllStyles().setAlignment(Component.CENTER);
        cmp.getAllStyles().setPadding(50, 0, 0, 0);
    }
    }

    private void addStringValue(String s, Component v) {

  add(BorderLayout.west(new Label(s, "PaddedLabel"))
            .add(BorderLayout.CENTER, v));
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
 