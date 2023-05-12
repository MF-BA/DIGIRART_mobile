package com.mycompany.services;


import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.mycompany.services.ServiceStats;
import static com.mycompany.services.ServiceStats.resultOk;
import com.mycompany.utils.Statics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class ServiceTest {
    
       //singleton 
    public static ServiceTest instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    public static ServiceTest getInstance() {
        if(instance == null )
            instance = new ServiceTest();
        return instance ;
    }
    
    public ServiceTest() {
        req = new ConnectionRequest();
    }   
    
    public boolean Email() {
   
    String url = Statics.BASE_URL +"/mailJSON";
    req.setUrl(url);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
        
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);

    return resultOk;
}
    
}
