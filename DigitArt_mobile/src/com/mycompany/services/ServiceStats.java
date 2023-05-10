/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import static com.mycompany.services.ServicePayment.resultOk;
import com.mycompany.utils.Statics;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class ServiceStats {
    
       //singleton 
    public static ServiceStats instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    public static ServiceStats getInstance() {
        if(instance == null )
            instance = new ServiceStats();
        return instance ;
    }
    
    public ServiceStats() {
        req = new ConnectionRequest();
    }
    
          
    private int nbStudent;
    private int nbTeen;
    private int nbAdult;
    
    public int getnbStudent() {
        return nbStudent;
    }

    public int getnbTeen() {
        return nbTeen;
    }

    public int getnbAdult() {
        return nbAdult;
    }
    
    
    public boolean getStats() {
   
    String url = Statics.BASE_URL +"/ticket/statistics/JSON";
    req.setUrl(url);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == " + str);
        
        // Remove the brackets and quotation marks from the string
        str = str.substring(2, str.length() - 2);
        
        // Use StringTokenizer to extract individual numbers
        StringTokenizer tokenizer = new StringTokenizer(str, "\",\"");
        
        // Parse the numbers and assign them to variables
        nbAdult = Integer.parseInt(tokenizer.nextToken());
        nbStudent = Integer.parseInt(tokenizer.nextToken());
        nbTeen = Integer.parseInt(tokenizer.nextToken());

    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);

    return resultOk;
}
}
