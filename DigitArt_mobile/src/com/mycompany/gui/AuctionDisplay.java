/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.mycompany.services.AuctionServices;

/**
 *
 * @author fedi1
 */
public class AuctionDisplay extends Form {

    public AuctionDisplay(Form previous) {
        setTitle("Auction");
         System.out.println(AuctionServices.getInstance().getAllAuctions());
//        SpanLabel sp = new SpanLabel();
//        sp.setText(AuctionServices.getInstance().getAllAuctions().toString());
//        add(sp);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public AuctionDisplay(Resources theme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
