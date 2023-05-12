package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.users;
import com.mycompany.gui.BackuserForm;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.gui.SessionUser;
import com.mycompany.gui.SignInForm;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author venom-1
 */
public class ServiceUsers {
     //singleton 
    public static ServiceUsers instance = null ;
    
    public static boolean resultOk = true;
    String json;
    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceUsers getInstance() {
        if(instance == null )
            instance = new ServiceUsers();
        return instance ;
    }
    
    
    
    public ServiceUsers() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void addusers(users user) {
        
        String url =Statics.BASE_URL+"/addUserJSON/new?cin="+user.getCin()+"&firstname="+user.getFirstname()+"&lastname="+user.getLastname()+"&email="+user.getEmail()
                +"&password="+user.getPwd()
                +"&address="+user.getAddress()
                +"&phoneNum="+user.getPhone_number()
                +"&role="+user.getRole()
                +"&gender="+user.getGender()
                +"&birthDate="+user.getBirth_date(); 
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    //affichage
    
    public ArrayList<users>Displayusers() {
        ArrayList<users> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/Allusers";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapusers = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapusers.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        users usr = new users();
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        double cin = Double.parseDouble(obj.get("cin").toString());
                        String firstname = obj.get("firstname").toString();
                        String lastname = obj.get("lastname").toString();
                        String email = obj.get("email").toString();
                        String address = obj.get("address").toString();
                        //String BirthDate = obj.get("birthDate").toString();
                        double phone_num = Double.parseDouble(obj.get("phoneNum").toString());
                        String gender = obj.get("gender").toString();
                        String role = obj.get("role").toString();
                        String status = obj.get("status").toString();
                        
                        usr.setId((int)id);
                        usr.setCin((int)cin);
                        usr.setFirstname(firstname);
                        usr.setLastname(lastname);
                        usr.setEmail(email);
                        usr.setGender(gender);
                        usr.setAddress(address);
                        usr.setStatus(status);
                        usr.setRole(role);
                        usr.setPhone_number((int)phone_num);
                        
                        //usr.setBirth_date(BirthDate);     
                        //Date 
                        System.out.println(obj.get("birthDate").toString());
                      String dateString = obj.get("birthDate").toString().substring(0, 10); // Extract the date string from the full string
                      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Create a SimpleDateFormat object with the expected date format
                      Date birthDate = format.parse(dateString); // Parse the date string into a Date object
                      String formattedDate = format.format(birthDate); // Format the Date object into a String using the SimpleDateFormat object
                      usr.setBirth_date(formattedDate);
                 
                   
                        //insert data into ArrayList result
                        result.add(usr);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
     NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
     
    
    public users UserDetails( int id , users user) {
        
        String url = Statics.BASE_URL+"/userdetail?"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                user.setCin(Integer.parseInt(obj.get("cin").toString()));
                user.setFirstname(obj.get("firstname").toString());
                user.setLastname(obj.get("lastname").toString());
                user.setEmail(obj.get("email").toString());
                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return user;
        
        
    }
    
    
    //Delete 
    public boolean deleteUser(int id ) {
        String url = Statics.BASE_URL +"/deleteUserJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
   /* public boolean modifierReclamation(users user) {
        String url = Statics.BASE_URL +"/updateUserJSON?id="+user.getId()+"&cin="+user.getObjet()+"&description="+user.getDescription()+"&etat="+user.getEtat();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    */
    
    //Signup
    public void signup(TextField cin,TextField firstname,TextField lastname,TextField email,TextField password,TextField address,TextField phoneNum,TextField birthDate,ComboBox<String>  gender,ComboBox<String> roles, Resources res) {
        
        String url = Statics.BASE_URL+"/user/signup?cin="+cin.getText()+"&firstname="+firstname.getText()+
                "&lastname="+lastname.getText()+"&email="+email.getText()+"&password="+password.getText()+"&address="+address.getText()
                +"&phoneNum="+phoneNum.getText()
                +"&birthDate="+birthDate.getText()
                +"&gender="+gender.getSelectedItem().toString()
                +"&role="+roles.getSelectedItem().toString();
        
        req.setUrl(url);
       
        //Control saisi
        if(cin.getText().equals(" ") && firstname.getText().equals(" ") && lastname.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ")) {
            
            Dialog.show("Erreur","Please fill the fields","OK",null);
            
        }
        
        //hethi wa9t tsir execution ta3 url 
        req.addResponseListener((e)-> {
         
            //njib data ly7atithom fi form 
            byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
            String responseData = new String(data);//ba3dika na5o content 
            if(responseData == "Email already used!")
            {
                Dialog.show("Email","Email Already used!","OK",null);
            }
             if(responseData == "email invalid!")
            {
                Dialog.show("Email","Invalid Email!","OK",null);
            }
              if(responseData == "Account is created")
              {Dialog.show("Success","account is saved","OK",null);
        new SignInForm(res).show();}
            System.out.println("data ===>"+responseData);
        }
        );
        
        
        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        
            
        
    }
    
    
    //SignIn
    
    public void signin(TextField email,TextField password, Resources rs ) {
    
        String url = Statics.BASE_URL+"/user/signin?email="+email.getText()+"&password="+password.getText();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        
        req.addResponseListener((e) ->{
            
            JSONParser j = new JSONParser();
            
            String json = new String(req.getResponseData()) + "";
            
            
            try {
            
            if(json.equals("user not found") || json.equals("password not found")) {
                Dialog.show("Failed to connect!","Email or password incorrect! ","OK",null);
            }
            else {
                System.out.println("data =="+json);
                
                Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
               
        
              // Get the user's timezone
/*TimeZone userTimeZone = TimeZone.getDefault();

// Create a formatter that uses the desired timezone
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(userTimeZone.toZoneId());*/

// Format the date value
//String formattedDate = formatter.format(user.get("birthDate"));
        
        
                //Session 
                float id = Float.parseFloat(user.get("id").toString());
                double cin = Double.parseDouble(user.get("cin").toString());
                double phone_num = Double.parseDouble(user.get("phoneNum").toString());
                SessionUser.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
                
                SessionUser.setPassword(user.get("password").toString());
                SessionUser.setCin((int)cin);
                SessionUser.setEmail(user.get("email").toString());
                SessionUser.setFirstname(user.get("firstname").toString());
                SessionUser.setLastname(user.get("lastname").toString());
                SessionUser.setAddress(user.get("address").toString());
                SessionUser.setGender(user.get("gender").toString());
                SessionUser.setRole(user.get("role").toString());
                SessionUser.setStatus(user.get("status").toString());
                SessionUser.setPhonenum((int)phone_num);
                //SessionUser.setBirthDate(user.get("birthDate").toString());
                
                 //Date 
                      String DateConverter =  user.get("birthDate").toString().substring(user.get("birthDate").toString().indexOf("timestamp") + 10 , user.get("birthDate").toString().lastIndexOf("}"));
                        
                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                        
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);
                        SessionUser.setBirthDate(dateString);
                //photo 
                
                if(user.get("image") != null)
                    SessionUser.setImage(user.get("image").toString());
                
               
                if(user.size() >0 )// l9a user
                {
                    if (user.get("role").toString().equals("Artist") || user.get("role").toString().equals("Subscriber"))
                    {
                       new NewsfeedForm(rs).show(); 
                    }
                    if (user.get("role").toString().equals("Admin"))
                    {
                       new BackuserForm(rs).show(); 
                    }
                    if (user.get("status").toString().equals("blocked"))
                    {
                       Dialog.show("Failed to login","your account is blocked","OK",null); 
                    }
                }
                   // new ListReclamationForm(rs).show();//yemchi lel list reclamation
                     /*new AjoutReclamationForm(rs).show();
                    */
                    }
            
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            
            
            
        });
    
         //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
     public static void EditUser(int id, String cin, String phoneNum,String firstname, String lastname, String address,ComboBox<String>  gender,ComboBox<String> roles,String birthDate ) {      
    
  
       String url = Statics.BASE_URL+"/user/edituser?id="+id+"&cin="+cin+"&firstname="+firstname+"&lastname="+lastname+"&address="+address
               +"&gender="+gender.getSelectedItem().toString()
               +"&role="+roles.getSelectedItem().toString()
               +"&phoneNum="+phoneNum
               +"&birthDate="+birthDate;
       
        MultipartRequest req = new MultipartRequest();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("id",String.valueOf(SessionUser.getId()));
        req.addArgument("cin",String.valueOf(SessionUser.getCin()));
        req.addArgument("phoneNum",String.valueOf(SessionUser.getPhonenum()));
        req.addArgument("firstname",firstname);
        req.addArgument("lastname",lastname);
        req.addArgument("address",address);
        req.addArgument("gender",gender.getSelectedItem().toString());
        req.addArgument("role",roles.getSelectedItem().toString());
        req.addArgument("birthDate",birthDate);
        //req.addArgument("email",Email);
       // req.addArgument("password",Password);
        
        
        req.addResponseListener((response)-> {
            
            byte[] data = (byte[]) response.getMetaData();
            String s = new String(data);
            System.out.println(s);
            //if(s.equals("success")){}
            //else {
                //Dialog.show("Erreur","Echec de modification", "Ok", null);
            //}
        });
        NetworkManager.getInstance().addToQueueAndWait(req); 
    
    
    }
  
     public boolean UpdateUser(users usr) {
         
        String url = Statics.BASE_URL +"/updateUserJSON?id="+usr.getId()+"&cin="+usr.getCin()+"&firstname="+usr.getFirstname()+"&lastname="+usr.getLastname()+"&email="+usr.getEmail()
                +"&address="+usr.getAddress()
                +"&gender="+usr.getGender()
                +"&role="+usr.getRole()
                +"&phoneNum="+usr.getPhone_number()
                +"&birthDate="+usr.getBirth_date()
                +"&status="+usr.getStatus();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
     
     public boolean UpdateUserPwd(String email, String newpwd) {
         
        String url = Statics.BASE_URL +"/user/editpwd?email="+email+"&password="+newpwd;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
      public String getPasswordCodeByEmail(String email, Resources rs ) {
 
        String url = Statics.BASE_URL+"/user/getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false); 
        req.setUrl(url);
        
         final String[] code = new String[1];
         
        req.addResponseListener((e) ->{
            
             JSONParser j = new JSONParser();
        String json = new String(req.getResponseData());

        try {
            Map<String, Object> result = j.parseJSON(new CharArrayReader(json.toCharArray()));
            code[0] = String.valueOf(result.get("code"));
            if(result.get("message") == "Code sent successfully.")
            {
                Dialog.show("Code"," Code sent by email ",new Command("OK")); 
            }
            if(result.get("message") == "User not found.")
            {
                Dialog.show("wrong email","Please enter a valid email",new Command("OK")); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
  
        });
    
        
        NetworkManager.getInstance().addToQueueAndWait(req);
     return code[0];
    }
       public void getStats() {
 
        String url = Statics.BASE_URL+"/user/stats";
        req = new ConnectionRequest(url, false); 
        req.setUrl(url);
        final String[] totalMale = new String[1];
         final String[] totalFemale = new String[1];
         
        req.addResponseListener((e) ->{
             JSONParser j = new JSONParser();
        String json = new String(req.getResponseData());
             try {
            if (e.getResponseCode() == 200) { // HTTP OK status code
            Map<String, Object> result = j.parseJSON(new CharArrayReader(json.toCharArray()));
            totalMale[0] = String.valueOf(result.get("totalMale"));
            totalFemale[0] = String.valueOf(result.get("totalFemale"));
            
            int decimalIndex = totalMale[0].indexOf(".");
            String integerPart = totalMale[0].substring(0, decimalIndex);
            
            Statics.Malenb = Integer.parseInt(integerPart);
            
            int decimalIndex2 = totalFemale[0].indexOf(".");
            String integerPart2 = totalFemale[0].substring(0, decimalIndex2);
            
            Statics.Femalenb = Integer.parseInt(integerPart2);
            // Use the retrieved values here
        }
             }catch (Exception ex) {
            ex.printStackTrace();
        }
             
  
        });
    
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    
    }
}
