/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Event;
import com.mycompany.entities.Participants;
import static com.mycompany.services.ServiceEvent.resultOk;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author kossay
 */
public class ServiceParticipate {
    
    //singleton 
    public static ServiceParticipate instance = null ;
    
    public static boolean resultOk;
    
    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceParticipate getInstancee() {
        if(instance == null )
            instance = new ServiceParticipate();
        return instance ;
    }
    
     public ServiceParticipate() {
        req = new ConnectionRequest();
        
    }
     
     //ajout 
    public void ajoutParticipate(Participants p) {
        
      
        String url =Statics.Base_URL+"/participants/addParticipant/Json?id_event="+p.getIdEvent()+"&id_user="+p.getIdUser()+"&first_name="+p.getFirstName()+"&last_name="+p.getLastName()+"&address="+p.getAdress()+"&gender="+p.getGender();
//127.0.0.1:8000/participants/addParticipant/Json?id_event=7&id_user=4&first_name="zizou"&last_name="loukil"&address="soukra"&gender="Female"
 
// aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
     //Delete 
    public boolean deletePart(int id ) {
        String url = Statics.Base_URL +"/participants/deleteParticipation/Json?id="+id;
        
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
    
}
