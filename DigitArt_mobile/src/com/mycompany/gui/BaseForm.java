/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.utils.Statics;
import com.mycompany.gui.AuctionDisplay;
import com.mycompany.gui.SessionUser;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "BlackSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "BlackSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        // Set the background color to black
        tb.getAllStyles().setBgColor(0x000000);
        

        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        int placeholderWidth = Display.getInstance().getDisplayWidth() / 6; // half the screen width
        int placeholderHeight = Display.getInstance().getDisplayHeight() / 12; // one quarter of the screen height
        EncodedImage placeholderImage = EncodedImage.createFromImage(Image.createImage(placeholderWidth, placeholderHeight), false);
        if (SessionUser.getImage() != null) {
            String imageURL = Statics.BASE_URL + "/uploads/" + SessionUser.getImage();
            Image x = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);
            tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                    sl,
                    FlowLayout.encloseCenterBottom(
                            new Label(x, "PictureWhiteBackgrond"))
            ));
        }
        if (SessionUser.getImage() == null) {
            String imageURL = Statics.BASE_URL + "/Back/images/icon-profile.png";
            Image x = URLImage.createToStorage(placeholderImage, imageURL, imageURL, URLImage.RESIZE_SCALE_TO_FILL);
            tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                    sl,
                    FlowLayout.encloseCenterBottom(
                            new Label(x, "PictureWhiteBackgrond"))
            ));
        }

        if (SessionUser.getRole().equals("Artist")) {
            SessionUser.back_end = false;
            SessionUser.artist = true;
            tb.addMaterialCommandToSideMenu("   Home Page", FontImage.MATERIAL_HOME, e -> new NewsfeedForm(res).show());
            tb.addMaterialCommandToSideMenu("   Ticket Purchase", FontImage.MATERIAL_EXIT_TO_APP, e -> new PaymentForm(res).show());
            tb.addMaterialCommandToSideMenu("   Auction", FontImage.MATERIAL_CREDIT_CARD, e -> new AuctionDisplay(res).show());
            tb.addMaterialCommandToSideMenu("   Event", FontImage.MATERIAL_EVENT, e -> new EventFrontForm(res).show());
            tb.addMaterialCommandToSideMenu("   Artwork", FontImage.MATERIAL_IMAGE, e -> new ListArtworkForm(res).show());
        } else if (SessionUser.getRole().equals("Subscriber")) {
            tb.addMaterialCommandToSideMenu("   Home Page", FontImage.MATERIAL_HOME, e -> new NewsfeedForm(res).show());
            tb.addMaterialCommandToSideMenu("   Ticket Purchase", FontImage.MATERIAL_EXIT_TO_APP, e -> new PaymentForm(res).show());
            tb.addMaterialCommandToSideMenu("   Auction", FontImage.MATERIAL_CREDIT_CARD, e -> new AuctionDisplay(res).show());
            tb.addMaterialCommandToSideMenu("   Event", FontImage.MATERIAL_EVENT, e -> new EventFrontForm(res).show());
            tb.addMaterialCommandToSideMenu("   Artwork", FontImage.MATERIAL_IMAGE, e -> new ListArtworkForm(res).show());
            SessionUser.back_end = false;
            SessionUser.artist = false;
        } else if (SessionUser.getRole().equals("Admin")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Users Management", FontImage.MATERIAL_VERIFIED_USER, e -> new ListUsersForm(res).show());
            tb.addMaterialCommandToSideMenu("   Ticket Management", FontImage.MATERIAL_MONEY, e -> new DisplayTicketForm(res).show());
            tb.addMaterialCommandToSideMenu("   Artwork Management", FontImage.MATERIAL_IMAGE, e -> new ListArtworkForm(res).show());
            tb.addMaterialCommandToSideMenu("   Event Management", FontImage.MATERIAL_EVENT, e -> new AjoutEventForm(res).show());
            tb.addMaterialCommandToSideMenu("   Payment Management", FontImage.MATERIAL_CREDIT_CARD, e -> new DisplayPaymentForm(res).show());
            tb.addMaterialCommandToSideMenu("   Room Management", FontImage.MATERIAL_HOME_WORK, e -> new ListRoomForm(res).show());
            tb.addMaterialCommandToSideMenu("   Auction Management", FontImage.MATERIAL_CREDIT_CARD, e -> new AuctionDisplay(res).show());

        } else if (SessionUser.getRole().equals("Users Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Users Management", FontImage.MATERIAL_VERIFIED_USER, e -> new ListUsersForm(res).show());

        } else if (SessionUser.getRole().equals("Tickets Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Ticket Management", FontImage.MATERIAL_MONEY, e -> new DisplayTicketForm(res).show());

        } else if (SessionUser.getRole().equals("Artwork Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Artwork Management", FontImage.MATERIAL_IMAGE, e -> new ListArtworkForm(res).show());
        } else if (SessionUser.getRole().equals("Events Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Event Management", FontImage.MATERIAL_EVENT, e -> new AjoutEventForm(res).show());

        } else if (SessionUser.getRole().equals("Payment Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Payment Management", FontImage.MATERIAL_CREDIT_CARD, e -> new DisplayPaymentForm(res).show());

        } else if (SessionUser.getRole().equals("Room Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Room Management", FontImage.MATERIAL_HOME_WORK, e -> new ListRoomForm(res).show());

        } else if (SessionUser.getRole().equals("Auction Manager")) {
            SessionUser.back_end = true;
            tb.addMaterialCommandToSideMenu("   Auction Management", FontImage.MATERIAL_CREDIT_CARD, e -> new AuctionDisplay(res).show());
        }
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            new SignInForm(res).show();
            SessionUser.pref.clearAll();
            Storage.getInstance().clearStorage();
            Storage.getInstance().clearCache();
        });

        refreshTheme();
    }
}
