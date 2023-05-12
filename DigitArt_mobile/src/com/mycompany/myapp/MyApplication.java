package com.mycompany.myapp;


import com.mycompany.gui.ListRoomForm;
import com.mycompany.gui.AjoutEventForm;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.mycompany.gui.EventFrontForm;
import com.mycompany.gui.ListEventForm;
import com.mycompany.gui.MapEvent;
import com.mycompany.gui.StatisticsEvent;
import com.mycompany.gui.WalkthruForm;
import com.mycompany.myapp.WalkthruForm;
import com.mycompany.gui.BackuserForm;
import com.mycompany.gui.ListUsersForm;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.gui.SessionUser;
import com.mycompany.gui.SignInForm;
import com.mycompany.gui.WalkthruForm;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
       if(current != null){
            current.show();
            return;
        }
       if (SessionUser.getEmail() == null)
       {
           new SignInForm(theme).show();
       }
       else
       {
            if (SessionUser.getRole().equals("Artist") || SessionUser.getRole().equals("Subscriber"))
                    {
                       new NewsfeedForm(theme).show(); 
                    }
                    if (SessionUser.getRole().equals("Admin"))
                    {
                       new BackuserForm(theme).show(); 
                    }
          //new WalkthruForm(theme).show();
       //new ListUsersForm(theme).show();  
       }
       
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
