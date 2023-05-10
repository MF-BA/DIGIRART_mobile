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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Payment;
import com.mycompany.entities.Ticket;
import static com.mycompany.services.ServiceTicket.resultOk;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class ServicePayment {
    //singleton 
    public static ServicePayment instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    public static ServicePayment getInstance() {
        if(instance == null )
            instance = new ServicePayment();
        return instance ;
    }
    
    public ServicePayment() {
        req = new ConnectionRequest();
    }
    
     public void addPayment(Payment payment) {
        
        String url =Statics.BASE_URL+"/paymentAddJSON?purchaseDate="
                +payment.getPurchaseDate()
                +"&nbAdult="+payment.getNbAdult()
                +"&nbTeenager=" +payment.getNbTeenager()
                +"&nbStudent="+payment.getNbStudent()
                +"&TotalPayment="+payment.getTotalPayment()
                +"&paid="+payment.isPaid();  
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json 
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution request
    }
     
     
      public ArrayList<Payment>DisplayPayment() {
        ArrayList<Payment> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/paymentDisplayJson";
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
                        Payment ti = new Payment();
      
                        // Parse the fields of each ticket from the JSON respons
                        float paymentid = Math.round(Float.parseFloat(obj.get("paymentId").toString()));
                        String purchaseDate = obj.get("purchaseDate").toString().substring(0, 10);
                        float nbAdult = Float.parseFloat(obj.get("nbAdult").toString());
                        float nbStudent = Float.parseFloat(obj.get("nbStudent").toString());
                        float nbTeenager = Float.parseFloat(obj.get("nbTeenager").toString());
                        float TotalPayment = Float.parseFloat(obj.get("totalPayment").toString());
                        Boolean paid = null;
                        Object paidObj = obj.get("paid");
                        if (paidObj instanceof Boolean) {
                            paid = (Boolean) paidObj;
                        } else if (paidObj instanceof String) {
                            paid = Boolean.parseBoolean((String) paidObj);
                        }

      
                        ti.setPaymentId((int)paymentid);
                        ti.setPurchaseDate(purchaseDate);
                        ti.setNbAdult((int)nbAdult);
                        ti.setNbTeenager((int)nbStudent);
                        ti.setNbStudent((int)nbTeenager);
                        ti.setTotalPayment((int)TotalPayment);
                        ti.setPaid(paid);
                         
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

    
}
