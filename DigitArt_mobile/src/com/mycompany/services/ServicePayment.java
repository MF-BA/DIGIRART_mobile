/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.mycompany.entities.Payment;
import com.mycompany.entities.Ticket;
import com.mycompany.utils.Statics;

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
    
}
