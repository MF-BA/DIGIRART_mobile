/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUsers;



/**
 *
 * @author Islem
 */
public class BackuserForm extends BaseForm {
    
     public BackuserForm(Resources res) {
        super(new BorderLayout());
       Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        setTitle("Dashboard");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
      if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        
        
       add(BorderLayout.CENTER, new Label(res.getImage("Logo.png"), "LogoLabel"));
        
        Label Dash = new Label("Your Dashboard");
        //add(Dash);
        
    
        
    }
     
}
