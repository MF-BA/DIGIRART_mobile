/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.mycompany.entities.Auction;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.utils.Static;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fedi1
 */
public class AuctionServices {

    public ArrayList<Auction> auctions;
    public ArrayList<String> images;
//    public ArrayList<Map<Integer, String>> artworks;

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
        Map<String, Object> artwork = (Map<String, Object>) obj.get("artwork");
        // Extract the value of the "idArt" key as a string
        String idArtStr = artwork.get("idArt").toString();
        // Convert the "idArt" string to an integer
        int id_artwork = Math.round(Float.parseFloat(idArtStr));
        String artworkName = artwork.get("artworkName").toString();
        Date date = formatter.parse(obj.get("endingDate").toString());
        String description = obj.get("description").toString();

        return new Auction(id_auction, starting_price, increment, id_artwork, date, description, artworkName);
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

    public ArrayList<String> parseArtworkImages(String jsonText) throws IOException, ParseException {
        ArrayList<String> images = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) jsonMap.get("root");
        for (Map<String, Object> obj : jsonArray) {
            String id_auction;
            if (obj.get("imageName").toString().isEmpty()) {
                id_auction = null;
            } else {
                id_auction = obj.get("imageName").toString();
            }
            images.add(id_auction);
        }
        return images;
    }

    public ArrayList<String> getArtworkImages(int id_artwork) {
        String url = Static.BASE_URL + "auction/mobile/" + id_artwork + "/images";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    images = parseArtworkImages(new String(req.getResponseData()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace(); // or handle the exception in some other way
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return images;
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

//    public ArrayList<Map<Integer, String>> parseArtworks(String jsonText) throws IOException, ParseException {
//        JSONParser parser = new JSONParser();
//
//        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
//
//        List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) jsonMap.get("root");
//        // System.out.println("--------jsonArray size: " + jsonArray.size());
//        for (Map<String, Object> obj : jsonArray) {
//            // Extract the value of the "idArt" key as a string
//            String idArtStr = obj.get("idArt").toString();
//
////            String idArtistStr = null;
////            if (obj.get("idArtist") == null) {
////            } else {
////                idArtistStr = obj.get("idArtist").toString();
////            }
//            // Convert the "idArt" string to an integer
//            int id_artwork = Math.round(Float.parseFloat(idArtStr));
//            int id_Artist;
////            if (obj.get("idArtist") == null) {
////                id_Artist=0;
////            } else {
////                 id_Artist = Math.round(Float.parseFloat(idArtistStr));
////            }
//            String artworkName = obj.get("artworkName").toString();
//            System.out.println(id_artwork + " " + artworkName);
//
//            // Create a map for the artwork and add it to the ArrayList
//            Map<Integer, String> artworkMap = new HashMap<Integer, String>();
//            artworkMap.put(id_artwork, artworkName);
//            artworks.add(artworkMap);
//        }
//
//        // Sort the ArrayList by id_artwork
//        Collections.sort(artworks, new Comparator<Map<Integer, String>>() {
//            @Override
//            public int compare(Map<Integer, String> o1, Map<Integer, String> o2) {
//                Integer id1 = o1.keySet().iterator().next();
//                Integer id2 = o2.keySet().iterator().next();
//                return id1.compareTo(id2);
//            }
//        });
//        return artworks;
//    }
//
//    public ArrayList<Map<Integer, String>> getArtworkNames() {
//        String url = Static.BASE_URL + "auction/mobile/artwork";
//        req.setUrl(url);
//        req.setPost(false);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                try {
//                    artworks = parseArtworks(new String(req.getResponseData()));
//                } catch (IOException | ParseException e) {
//                    e.printStackTrace(); // or handle the exception in some other way
//                }
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//        return artworks;
//    }

}
