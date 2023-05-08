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
public class ServiceEvent {
    
    //singleton 
    public static ServiceEvent instance = null ;
    
    public static boolean resultOk;
    
    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceEvent getInstance() {
        if(instance == null )
            instance = new ServiceEvent();
        return instance ;
    }
    
     public ServiceEvent() {
        req = new ConnectionRequest();
        
    }
     
     //ajout 
    public void ajoutEvent(Event event) {
        
      
        String url =Statics.Base_URL+"/event/addEvent/Json?event_name="+event.getEventName()+"&nb_participants="+event.getNbParticipants()+"&start_time="+event.getStartTime()+"&detail="+event.getDetail()+"&color="+event.getColor();


// aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    
       public ArrayList<Event>affichageEvent() {
        ArrayList<Event > result = new ArrayList<>();
        
        String url = Statics.Base_URL+"/event/DisplayEvent/Json";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapEvent = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapEvent.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Event re = new Event();
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String event_name = obj.get("eventName").toString();
                                int nb_participants = Integer.parseInt(obj.get("nbParticipants").toString());
                                double start_time = Double.parseDouble(obj.get("startTime").toString());

                                String detail = obj.get("detail").toString();
                                String color = obj.get("color").toString();  
                                String image = obj.get("image").toString();

                                re.setId((int)id);
                                re.setEventName(event_name);
                                re.setNbParticipants(nb_participants);
                                re.setDetail(detail);
                                re.setColor(color);
                                re.setStartTime((int)start_time);
                                re.setImage(image);

                        
                        //Date 
                       String Start =  obj.get("startDate").toString().substring(obj.get("startDate").toString().indexOf("timestamp") + 10 , obj.get("startDate").toString().lastIndexOf("}"));
                        String end =  obj.get("endDate").toString().substring(obj.get("endDate").toString().indexOf("timestamp") + 10 , obj.get("endDate").toString().lastIndexOf("}"));

                        Date currentTimes = new Date(Double.valueOf(Start).longValue() * 1000);
                        Date currentTimee = new Date(Double.valueOf(end).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String startString = formatter.format(currentTimes);
                         String endString = formatter.format(currentTimee);
                        re.setStartDate(startString);
                        re.setEndDate(endString);
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
      
    public Event DetailEvent( int id , Event event) {
        
        String url = Statics.Base_URL+"/event/detailEvent/Json?"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
                event.setEventName(obj.get("event_name").toString());
                event.setDetail(obj.get("detail").toString());
                event.setNbParticipants(Integer.parseInt(obj.get("nb_participants").toString()));
                event.setStartTime(Integer.parseInt(obj.get("start_time").toString()));
                event.setColor(obj.get("color").toString());

                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return event;
        
        
    }
    
     //Delete 
    public boolean deleteEvent(int id ) {
        String url = Statics.Base_URL +"/event/deleteEvent/Json?id="+id;
        
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
    public boolean modifierReclamation(Event event) {
        String url =Statics.Base_URL+"/event/modifyEvent/Json?id="+event.getId()+"&event_name="+event.getEventName()+"&nb_participants="+event.getNbParticipants()+"&start_time="+event.getStartTime()+"&detail="+event.getDetail()+"&color="+event.getColor();
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
    
}
