/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import static com.mycompany.services.ServiceTicket.resultOk;
import com.mycompany.utils.Statics;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class GetTicketPricesServices {
    
    
         //singleton 
    public static GetTicketPricesServices instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    public static GetTicketPricesServices getInstance() {
        if(instance == null )
            instance = new GetTicketPricesServices();
        return instance ;
    }
    
    public GetTicketPricesServices() {
        req = new ConnectionRequest();
    }   
    
    
    private int priceStudent;
    private int priceTeen;
    private int priceAdult;
    
    private int nbStudent;
    private int nbTeen;
    private int nbAdult;
    
    public int getPriceStudent() {
        return priceStudent;
    }

    public int getPriceTeen() {
        return priceTeen;
    }

    public int getPriceAdult() {
        return priceAdult;
    }
    
    public int getnbStudent() {
        return nbStudent;
    }

    public int getnbTeen() {
        return nbTeen;
    }

    public int getnbAdult() {
        return nbAdult;
    }
    public interface Callback {
    void onComplete(boolean success);
}

    public boolean getPrices(Date date ) {
    String url = Statics.BASE_URL + "/ticket/priceJSON/" + new SimpleDateFormat("dd-MM-yyyy").format(date);
    req.setUrl(url);
    
    req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == "+str);
        str = str.substring(1, str.length()-1); // remove the brackets from the string
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        priceStudent = Integer.parseInt(tokenizer.nextToken());
        priceAdult = Integer.parseInt(tokenizer.nextToken());
        priceTeen = Integer.parseInt(tokenizer.nextToken());
    });
    
    NetworkManager.getInstance().addToQueue(req);

    return resultOk;
}
}
