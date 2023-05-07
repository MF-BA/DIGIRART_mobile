/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.mycompany.myapp.entities.Auction;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fedi1
 */
public class AuctionServices {

    public ArrayList<Auction> auctions;

    public static AuctionServices instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private AuctionServices() {
        req = new ConnectionRequest();
    }

    public static AuctionServices getInstance() {
        if (instance == null) {
            instance = new AuctionServices();
        }
        return instance;
    }

    public Auction parseAuction(Map<String, Object> obj) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int id_auction = Math.round(Float.parseFloat(obj.get("id_auction").toString()));
        int starting_price = Math.round(Float.parseFloat(obj.get("startingPrice").toString()));
        int increment = Math.round(Float.parseFloat(obj.get("increment").toString()));

//        JSONParser parser = new JSONParser();
//        System.out.println(obj.get("artwork").toString());
//        int id_artwork = -10 ;
//        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(obj.get("artwork").toString().toCharArray()));
//        List<Map<String, Object>> artwork = (List<Map<String, Object>>) jsonMap.get("root");
//        
//        for (Map<String, Object> object : artwork) {
//            System.out.println(object.get("idArt").toString());
//            id_artwork=Math.round(Float.parseFloat(object.get("idArt").toString()));
//        }
               // Cast the "artwork" object to a Map<String, Object>
    Map<String, Object> artwork = (Map<String, Object>) obj.get("artwork");
    
    // Extract the value of the "idArt" key as a string
    String idArtStr = artwork.get("idArt").toString();
    
    // Convert the "idArt" string to an integer
    int id_artwork = Math.round(Float.parseFloat(idArtStr));
  
        Date date = formatter.parse(obj.get("endingDate").toString());
        String description = obj.get("description").toString();

        return new Auction(id_auction, starting_price, increment, id_artwork, date, description);

    }

    public ArrayList<Auction> parseAuctions(String jsonText) throws IOException, ParseException {
        ArrayList<Auction> auctions = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) jsonMap.get("root");
        // System.out.println("--------jsonArray size: " + jsonArray.size());
        for (Map<String, Object> obj : jsonArray) {
            auctions.add(parseAuction(obj));
        }
        return auctions;
    }

    public ArrayList<Auction> getAllAuctions() {
        String url = Static.BASE_URL + "auction/mobile/Display";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    auctions = parseAuctions(new String(req.getResponseData()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace(); // or handle the exception in some other way
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return auctions;
    }

}
