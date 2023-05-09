/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.BaseForm;
import com.mycompany.entities.Auction;
import com.mycompany.utils.Static;
import com.mycompany.services.AuctionServices;
import java.util.ArrayList;

/**
 *
 * @author fedi1
 */
public class AuctionDisplay extends BaseForm {

    public AuctionDisplay(Resources res) {
        super("Auction", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton Display = RadioButton.createToggle("Auction", barGroup);
        Display.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Add to Auction", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(1000, 110), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);

        add(content);
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, Display, add),
                FlowLayout.encloseBottom(arrow)
        ));
        Display.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(Display, arrow);
        });

        bindButtonSelection(Display, arrow);
        bindButtonSelection(add, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        System.out.println("before");
        ArrayList<Auction> auctions = AuctionServices.getInstance().getAllAuctions();
        for (int i = 0; i < auctions.size(); i++) {
            addauction(auctions.get(i), res);
        }
        System.out.println("after");

        add.addActionListener(e -> {
            Static.previous = this;
            new AuctionAdd(res).show();
        });
    }

    public AuctionDisplay(Resources res, ArrayList<Container> cnt) {
        super("Auction", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton Display = RadioButton.createToggle("Auction", barGroup);
        Display.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Add to Auction", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(1000, 110), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container content = new Container();

        content.add(imageLab);

        add(content);
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, Display, add),
                FlowLayout.encloseBottom(arrow)
        ));
        Display.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(Display, arrow);
        });

        bindButtonSelection(Display, arrow);
        bindButtonSelection(add, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        System.out.println("before");
        //ArrayList<Auction> auctions = AuctionServices.getInstance().getAllAuctions();
        for (int i = 0; i < cnt.size(); i++) {
            Container container = cnt.get(i);
        // Check if the container is already added to a parent container
        if (container.getParent() != null) {
            // Remove the container from its parent container
            container.getParent().removeComponent(container);
        }
        // Add the container to the AuctionDisplay
        add(container);
        }
        System.out.println("after");

        add.addActionListener(e -> {
            Static.previous = this;
            new AuctionAdd(res).show();
        });
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addauction(Auction auction, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? Static.BASE_URL + "uploads/Empty.jpeg" : Static.BASE_URL + "uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");

        Label Ending_date = new Label(auction.getDate().toString());
        Ending_date.setUIID("CenterLabel");

        Button more_info = new Button("more information");
        more_info.setUIID("Button2");

        more_info.addActionListener(e -> {
            Static.previous = this;
            new showAuction(res, auction).show();
        });

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderWidth, 100), false);
        String separURL = Static.BASE_URL + "uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(Ending_date);
        cnt.add(more_info);
        cnt.add(imageLab);
        
        Static.cnt.add(cnt);
        add(cnt);
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
