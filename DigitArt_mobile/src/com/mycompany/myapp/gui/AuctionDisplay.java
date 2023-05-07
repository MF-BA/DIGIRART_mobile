/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.BaseForm;
import com.mycompany.myapp.entities.Auction;
import com.mycompany.myapp.entities.Static;
import com.mycompany.myapp.services.AuctionServices;
import java.util.ArrayList;

/**
 *
 * @author fedi1
 */
public class AuctionDisplay extends BaseForm {

    public AuctionDisplay(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Welcome");
        setTitle("Auction");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton Display = RadioButton.createToggle("Auction", barGroup);
        Display.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Add to Auction", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        Container content = new Container();
        
        content.add(new Container() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension();
            }
        });
        
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
        ArrayList<Auction> auctions = AuctionServices.getInstance().getAllAuctions();
        for (int i = 0; i < auctions.size(); i++) {
            addauction(auctions.get(i), res);
        }
    }

    private Container createArticles() {
        ArrayList<Auction> auctions = AuctionServices.getInstance().getAllAuctions();
        Container articleContainer = new Container();
        articleContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        articleContainer.setScrollableY(true);
        //-- Add articles to the articleContainer
        for (int i = 0; i < auctions.size(); i++) {
            Container article = new Container(new BorderLayout());
            //-- Add article image        
            int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
            int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
            EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
            ArrayList images = AuctionServices.getInstance().getArtworkImages(auctions.get(i).getId_artwork());
            String imageURL = images.isEmpty() ? Static.BASE_URL + "uploads/noimage.jpg" : Static.BASE_URL + "uploads/" + images.get(0);
            Image image = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);
            Label imageLabel = new ScaleImageLabel(image);
            article.addComponent(BorderLayout.CENTER, imageLabel);
            //-- Add article text
            Container articleTextContainer = new Container(new BorderLayout());
            String description = auctions.get(i).getDescription();
            if (description.length() > 50) {
                description = description.substring(0, 50) + "...";
            }
            articleTextContainer.addComponent(BorderLayout.CENTER, new Label(description));
            Button more_info = new Button("more information");
            //more_info.addActionListener(e-> new AddTaskForm(current).show());
            //-- Add article button
            articleTextContainer.addComponent(BorderLayout.SOUTH, more_info);
            article.addComponent(BorderLayout.SOUTH, articleTextContainer);
            articleContainer.add(article);
        }
        return articleContainer;
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addauction(Auction auction, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        int placeholderWidth = Display.getInstance().getDisplayWidth() / 2; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 4; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        ArrayList images = AuctionServices.getInstance().getArtworkImages(auction.getId_artwork());
        String imageURL = images.isEmpty() ? "http://127.0.0.1:8000/uploads/noimage.jpg" : Static.BASE_URL + "uploads/" + images.get(0);
        Image img = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLabel = new ScaleImageLabel(img);
        imageLabel.setUIID("LogoLabel");

        Label title = new Label(auction.getArtworkName());
        title.setUIID("CenterLabel");

        Label Ending_date = new Label(auction.getDate().toString());
        Ending_date.setUIID("CenterLabel");

        Button more_info = new Button("more information");
        more_info.setUIID("Button2");

        EncodedImage placeholderImageseparator = EncodedImage.createFromImage(Image.createImage(placeholderWidth, 100), false);
        String separURL = "http://127.0.0.1:8000/uploads/pngegg.png";
        Image separatorIMG = URLImage.createToStorage(placeholderImageseparator, separURL, separURL, URLImage.RESIZE_SCALE_TO_FILL);

        ScaleImageLabel imageLab = new ScaleImageLabel(separatorIMG);
        imageLab.setUIID("LogoLabel");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.add(title);
        cnt.add(imageLabel);
        cnt.add(Ending_date);
        cnt.add(more_info);
        cnt.add(imageLab);
        add(cnt);

//        Label title = new Label(auction.getArtworkName());
//        title.setUIID("CenterLabel");
//        cnt.addComponent(BorderLayout.NORTH, title);
//        String description = auction.getDescription();
//        if (description.length() > 50) {
//            description = description.substring(0, 50) + "...";
//        }
//        cnt.addComponent(BorderLayout.CENTER, new Label(description));
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
