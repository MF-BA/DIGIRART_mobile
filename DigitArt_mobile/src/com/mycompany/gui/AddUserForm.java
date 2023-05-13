/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.mycompany.entities.users;
import com.mycompany.services.ServiceUsers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Islem
 */
public class AddUserForm extends BaseForm{
      Form current;
    public AddUserForm(Resources res ) {
        super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add user");
        getContentPane().setScrollVisible(false);
        
        
        tb.addSearchCommand(e ->  {
            
        });
        addSideMenu(res);
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("back-couverture.jpg"),"","",res);
        
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
        RadioButton Listeusers = RadioButton.createToggle("Liste Of Users", barGroup);
        Listeusers.setUIID("SelectBar");
        RadioButton adduser = RadioButton.createToggle("Add User", barGroup);
        adduser.setUIID("SelectBar");
        RadioButton statsuser = RadioButton.createToggle("User stats", barGroup);
        statsuser.setUIID("SelectBar");
       


        Listeusers.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          ListUsersForm a = new ListUsersForm(res);
            a.show();
            refreshTheme();
        });

        adduser.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          AddUserForm a = new AddUserForm(res);
            a.show();
            refreshTheme();
        });
        
        statsuser.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
          UserStatsForm a = new UserStatsForm(res);
            a.show();
            refreshTheme();
        });
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, Listeusers, adduser,statsuser)
                
        ));

        
       
    

        
        //
        
      
         TextField cin = new TextField("", "Cin", 20, TextField.ANY);
         cin.setUIID("TextFieldBlack");
        addStringValue("Cin", cin);
        TextField firstname = new TextField("", "First Name", 20, TextField.ANY);
        firstname.setUIID("TextFieldBlack");
        addStringValue("First Name", firstname);
        TextField lastname = new TextField("", "Last Name", 20, TextField.ANY);
        lastname.setUIID("TextFieldBlack");
        addStringValue("Last Name", lastname);
        TextField Email = new TextField("", "Email", 20, TextField.EMAILADDR);
        Email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", Email);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password", password);
        TextField address = new TextField("", "Address", 20, TextField.ANY);
        address.setUIID("TextFieldBlack");
        addStringValue("Address", address);
        TextField phonenum = new TextField("", "Phone number", 20, TextField.ANY);
        phonenum.setUIID("TextFieldBlack");
        addStringValue("Phone number", phonenum);
           //Role 
        //Vector 3ibara ala array 7atit fiha roles ta3na ba3d nzidouhom lel comboBox
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
        vectorRole.add("Artist");
        vectorRole.add("Subscriber");
        vectorRole.add("Gallery Manager");
        vectorRole.add("Auction Manager");
        vectorRole.add("Events Manager");
        vectorRole.add("Tickets Manager");
        vectorRole.add("Users Manager");
        vectorRole.add("Admin");
        
        
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        addStringValue("choose a role", roles);
        //gender
        Vector<String> vectorGender;
        vectorGender = new Vector();
        
        vectorGender.add("Male");
        vectorGender.add("Female");
        
        ComboBox<String> gender = new ComboBox<>(vectorGender);
        addStringValue("Gender", gender);
        
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("Birth Date", datePicker);
        
        TextField birthDateField = new TextField("", "Birth Date", 20, TextField.ANY);
        birthDateField.setUIID("TextFieldBlack");
        addStringValue("Birth Date", birthDateField);
         
        
         // add a listener to the date picker to set the value of the text field
    datePicker.addActionListener(e -> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = dateFormat.format(datePicker.getDate());
    birthDateField.setText(dateString);
    
    });


        cin.setSingleLineTextArea(false);
        firstname.setSingleLineTextArea(false);
        lastname.setSingleLineTextArea(false);
        Email.setSingleLineTextArea(false);
        address.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        phonenum.setSingleLineTextArea(false);
        
      
        Button btnAjouter = new Button("Add User");
        addStringValue("", btnAjouter);
        
        
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                String cinText = cin.getText();
    String firstnameText = firstname.getText();
    String lastnameText = lastname.getText();
    String emailText = Email.getText().toString();
    String passwordText = password.getText();
    String addressText = address.getText();
    String phonenumText = phonenum.getText();
    String birthDateText = birthDateField.getText();
    
    if (cinText.isEmpty() || firstnameText.isEmpty() || lastnameText.isEmpty() || emailText.isEmpty() ||
        passwordText.isEmpty() || addressText.isEmpty() || phonenumText.isEmpty() || birthDateText.isEmpty()) {
        Dialog.show("Error", "All fields are required", "OK", null);
    } else if (cinText.isEmpty() && firstnameText.isEmpty() && lastnameText.isEmpty() && emailText.isEmpty() &&
        passwordText.isEmpty() && addressText.isEmpty() && phonenumText.isEmpty() && birthDateText.isEmpty()) {
        Dialog.show("Error", "All fields are required", "OK", null);
    }
    else if (!isNumeric(cinText)) {
        Dialog.show("Error", "cin must be numerical", "OK", null);
       
    } else  if(cinText.length() != 8)
        {
          Dialog.show("Error", "Cin must contain 8 digits", "OK", null);  
        }
    else if (!isNumeric(phonenumText)) {
        Dialog.show("Error", "phone number must be numerical", "OK", null);
        
    }
    else if(phonenumText.length() != 8)
        {
          Dialog.show("Error", "Phone number must contain 8 digits", "OK", null);  
        }/*else if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
        Dialog.show("Error", "Password must contain at least one uppercase letter, one lowercase letter, one special character, and be at least 8 characters long", "OK", null);
    } else if (!emailText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        Dialog.show("Error", "Email must be in the correct format", "OK", null);
    }*/
    else if (!StringUtil.replaceAll(passwordText, "[^0-9]", "").isEmpty() 
         && !StringUtil.replaceAll(passwordText, "[^a-z]", "").isEmpty() 
         && !StringUtil.replaceAll(passwordText, "[^A-Z]", "").isEmpty() 
         && !StringUtil.replaceAll(passwordText, "[^@#$%^&+=]", "").isEmpty()
         && passwordText.length() >= 8) {
    Dialog.show("Error", "Password must contain at least one uppercase letter, one lowercase letter, one special character, and be at least 8 characters long", "OK", null);
    } else if (!StringUtil.replaceAll(emailText, "[^a-zA-Z0-9._%+-@]", "").equals(emailText) 
          
           || emailText.indexOf("@") == -1) {
    Dialog.show("Error", "Email must be in the correct format", "OK", null);
    }
    else {
       InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    //njibo iduser men session (current user)
                    users u = new users(Integer.parseInt(cin.getText()),firstname.getText(),lastname.getText(),Email.getText(),password.getText(),address.getText(),Integer.parseInt(phonenum.getText()),birthDateField.getText(),gender.getSelectedItem().toString(),roles.getSelectedItem().toString());
                    
                    System.out.println("data  user == "+u);
                    
                    
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceUsers.getInstance().addusers(u);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListREclamationForm
                    new ListUsersForm(res).show();
                    
                    
                    refreshTheme();//Actualisation
    }
                
               
                    
                            
                
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("back-logo.jpeg"), page1);
        
        
        
        
    }
    
    
    private boolean isNumeric(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
    }
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
}
