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
import com.mycompany.entities.Auction;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fedi1
 */
public class BidServices {

    public ArrayList<Bid> bids;

    public static BidServices instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private BidServices() {
        req = new ConnectionRequest();
    }

    public static BidServices getInstance() {
        if (instance == null) {
            instance = new BidServices();
        }
        return instance;
    }

    public Bid parseBid(Map<String, Object> obj) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int id_bid = Math.round(Float.parseFloat(obj.get("id").toString()));
        int offer = Math.round(Float.parseFloat(obj.get("offer").toString()));
        int id_user = Math.round(Float.parseFloat(obj.get("idUser").toString()));
        Date date = formatter.parse(obj.get("date").toString());

        //public Bid(int id_bid, int id_user, int id_auction, int offer, Date date) 
        return new Bid(id_bid, id_user, offer, date);
    }

    public ArrayList<Bid> parseBids(String jsonText, int id) throws IOException, ParseException {
        ArrayList<Bid> Bids = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) jsonMap.get("root");
        // System.out.println("--------jsonArray size: " + jsonArray.size());
        for (Map<String, Object> obj : jsonArray) {
            Bid bid = parseBid(obj);
            bid.setId_auction(id);
            Bids.add(bid);
        }
        return Bids;
    }

    public ArrayList<Bid> getBids(Auction auction) {
        String url = Static.BASE_URL + "auction/mobile/" + auction.getId_auction() + "/bid";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    bids = parseBids( new String(req.getResponseData()) , auction.getId_auction());
                } catch (IOException | ParseException e) {
                    e.printStackTrace(); // or handle the exception in some other way
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return bids;
    }
    
    
    public boolean addBid(Bid bid) {
        String url = Static.BASE_URL + "auction/mobile/bid/add";
        req.setUrl(url);
        req.addArgument("offer", String.valueOf(bid.getOffer()));
        req.addArgument("id_auction", String.valueOf(bid.getId_auction()));
        req.addArgument("id_user", String.valueOf(bid.getId_user()));

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    

}
