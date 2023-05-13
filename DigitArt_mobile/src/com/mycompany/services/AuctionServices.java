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
import com.mycompany.entities.Artwork;
import com.mycompany.entities.Bid;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import com.mycompany.utils.Statics;
import com.mycompany.entities.Auction;
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
    public ArrayList<String> images;
    public ArrayList<Artwork> artworks;

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
        String url = Statics.BASE_URL + "/auction/mobile/" + id_artwork + "/images";
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
        String url = Statics.BASE_URL + "/auction/mobile/Display";
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

    public ArrayList<Artwork> parseArtworks(String jsonText) throws IOException, ParseException {
        ArrayList<Artwork> artworks = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Map<String, Object> jsonMap = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> jsonArray = (List<Map<String, Object>>) jsonMap.get("root");
        // System.out.println("--------jsonArray size: " + jsonArray.size());
        for (Map<String, Object> obj : jsonArray) {
            // Extract the value of the "idArt" key as a string
            String idArtStr = obj.get("idArt").toString();

            // Convert the "idArt" string to an integer
            int id_artwork = Math.round(Float.parseFloat(idArtStr));
            int id_Artist = -1;
            if(obj.get("idArtist") != null)
            {
                Map<String, Object> artist = (Map<String, Object>) obj.get("idArtist");
                // Extract the value of the "idArt" key as a string
                String id_userSTR = artist.get("id").toString();
                id_Artist = Math.round(Float.parseFloat(id_userSTR));
            }
            
            String artworkName = obj.get("artworkName").toString();

            //System.out.println(id_artwork + " " + artworkName + " " + id_Artist);

            artworks.add(new Artwork(id_artwork, artworkName, id_Artist));
        }

        // Define a custom comparator to compare Artwork objects based on their ID
        Comparator<Artwork> idComparator = new Comparator<Artwork>() {
            @Override
            public int compare(Artwork a1, Artwork a2) {
                return Integer.compare(a1.getIdArt(), a2.getIdArt());
            }
        };

// Sort the artworks list using the custom comparator
        Collections.sort(artworks, idComparator);
        return artworks;
    }

    public ArrayList<Artwork> getArtworkNames() {
        String url = Statics.BASE_URL + "/auction/mobile/artwork";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    artworks = parseArtworks(new String(req.getResponseData()));
                } catch (IOException | ParseException e) {
                    e.printStackTrace(); // or handle the exception in some other way
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return artworks;
    }
    
    
     public boolean addAuction(Auction auction) {
        String url = Statics.BASE_URL + "/auction/mobile/add";
        req.setUrl(url);
         System.out.println(String.valueOf(auction.getDate()));
        req.addArgument("StartingPrice", String.valueOf(auction.getStarting_price()));
        req.addArgument("Description", String.valueOf(auction.getDescription()));
        req.addArgument("EndingDate", String.valueOf(auction.getDate()));
        req.addArgument("Increment", String.valueOf(auction.getIncrement()));
        req.addArgument("Artwork", String.valueOf(auction.getId_artwork()));
        

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
     
     ///mobile/{id}/delete
     public boolean DeleteAuction(Auction auction) {
        String url = Statics.BASE_URL + "/auction/mobile/"+auction.getId_auction()+"/delete";
        req.setUrl(url);     

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
     
     
     public boolean EditAuction(Auction auction) {
        String url = Statics.BASE_URL + "/auction/mobile/"+auction.getId_auction()+"/edit";
        req.setUrl(url);
         System.out.println(String.valueOf(auction.getDate()));
        req.addArgument("StartingPrice", String.valueOf(auction.getStarting_price()));
        req.addArgument("Description", String.valueOf(auction.getDescription()));
        req.addArgument("EndingDate", String.valueOf(auction.getDate()));
        req.addArgument("Increment", String.valueOf(auction.getIncrement()));
        req.addArgument("Artwork", String.valueOf(auction.getId_artwork()));
        

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
