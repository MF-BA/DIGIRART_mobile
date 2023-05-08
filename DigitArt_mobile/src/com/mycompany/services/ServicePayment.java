/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;

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
    
    
    
}
