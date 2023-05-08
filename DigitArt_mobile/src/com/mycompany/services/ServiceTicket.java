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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Ticket;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class ServiceTicket {
    //singleton 
    public static ServiceTicket instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceTicket getInstance() {
        if(instance == null )
            instance = new ServiceTicket();
        return instance ;
    }
    
  
    public ServiceTicket() {
        req = new ConnectionRequest();
    }
    
    public void addTicket(Ticket ticket) {
        
        String url =Statics.BASE_URL+"/ticketAdd?ticketdate="+ticket.getTicket_date()+"&ticketEdate="+ticket.getTicket_edate()+"&ticketType="+ticket.getTicket_type()+"&price="+ticket.getPrice();  
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json 
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution request
    }
     
     
    public ArrayList<Ticket>DisplayTicket() {
        ArrayList<Ticket> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/ticketDisplay";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                
                        //Date date = new Date();
                        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        //String dateString = formatter.format(date);
                       
                    for(Map<String, Object> obj : listOfMaps) {
                        Ticket ti = new Ticket();
      
                        // Parse the fields of each ticket from the JSON respons
                        float ticket_id = Math.round(Float.parseFloat(obj.get("ticketId").toString()));
                        String ticketType = obj.get("ticketType").toString();
                        float price = Float.parseFloat(obj.get("price").toString());
                        String Startdate = obj.get("ticketDate").toString().substring(0, 10);
                        String Enddate = obj.get("ticketEdate").toString().substring(0, 10);
                        
                        ti.setTicket_id((int)ticket_id);
                        ti.setTicket_type(ticketType);
                        ti.setPrice((int)price);
                        ti.setTicket_date(Startdate);
                        ti.setTicket_edate(Enddate);
                        
                        //insert data into ArrayList result
                        result.add(ti);
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
    }
    
   
      //Delete 
    public boolean DeleteTicket(float id ) {
        String url = Statics.BASE_URL +"/ticketDelete/"+id;
        
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
    public boolean updateTicket(Ticket ticket) {
    String url = Statics.BASE_URL + "/ticketUpdate/" + ticket.getTicket_id() + "?ticketdate=" + ticket.getTicket_date() + "&ticketEdate=" + ticket.getTicket_edate() + "&ticketType=" + ticket.getTicket_type() + "&price=" + ticket.getPrice();
    req.setUrl(url);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOk = req.getResponseCode() == 200;  // Code response Http 200 ok
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
}

}
