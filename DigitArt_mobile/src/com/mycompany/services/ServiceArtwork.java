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
import com.mycompany.entities.Artwork;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 *
 * @author mohamed
 */
public class ServiceArtwork {
     // Singleton instance
    public static ServiceArtwork instance = null;
    
    public static boolean resultOk = true;

    // Initialization of the connection request
    private ConnectionRequest req;
     public ArrayList<String> images;
    
    public static ServiceArtwork getInstance() {
        if(instance == null)
            instance = new ServiceArtwork();
        return instance;
    }
    
    
    
    public ServiceArtwork() {
        req = new ConnectionRequest();
        
    }
    
    
    // Adding a Artwork
    public void addArtwork(Artwork artwork) {
        
        String url = Statics.BASE_URL + "/addArtworkJSON/new?artworkName="+artwork.getArtworkName()+"&artistName="+artwork.getArtistName()+ "&idArtist=" + artwork.getIdArtist() +
                 "&artistName=" + artwork.getArtistName() + "&dateArt=" + 
                 artwork.getDateArt() + "&description=" + artwork.getDescription() +
                 "&idRoom=" + artwork.getIdRoom();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
    // Displaying Artworks
 public ArrayList<Artwork> displayArtworks() {
    ArrayList<Artwork> result = new ArrayList<>();
    
    String url = Statics.BASE_URL + "/AllArtwork";
    req.setUrl(url);
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp;
            jsonp = new JSONParser();
            
            try {
                Map<String, Object> mapArtworks = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                
                List<Map<String, Object>> listOfMaps =  (List<Map<String, Object>>) mapArtworks.get("root");
                
                for (Map<String, Object> obj : listOfMaps) {
                    Artwork artwork = new Artwork();
                    
                   
                    int idArtwork = Math.round(Float.parseFloat(obj.get("idArt").toString()));
                    
                    String nameArtwork = obj.get("artworkName").toString();
                    
                    //int idArtist = Math.round(Float.parseFloat(obj.get("idArtist").toString()));
                    
                    String artistName ;
                    if(obj.get("artistName")!=null)
                    artistName =obj.get("artistName").toString();
                    else{artistName ="pasdenom";}
                    
                    // Parse the date from the string representation
                    String dateString = obj.get("dateArt").toString();
                    String description = obj.get("description").toString();
                    
//                    Map<String, Object> room = (Map<String, Object>) obj.get("idRoom");
//                    // Extract the value of the "idArt" key as a string
//                    String idRoomStr = room.get("idRoom").toString();
//                    // Convert the "idArt" string to an integer
//                    int id_room = Math.round(Float.parseFloat(idRoomStr));
//                    String nameRoom = room.get("nameRoom").toString();
//                    
                    artwork.setIdArt(idArtwork);
                    artwork.setArtworkName(nameArtwork);
                  // artwork.setIdArtist(idArtist);
                    artwork.setArtistName(artistName);
                    artwork.setDateArt(dateString);
                    artwork.setDescription(description);
//                    artwork.setIdRoom(id_room);
//                    artwork.setNameRoom(nameRoom);
//                    
                    result.add(artwork);
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return result;
}

    
    
   // Get Artwork details
public Artwork getArtworkDetails(int id, Artwork artwork) {
    String url = Statics.BASE_URL + "/artwork/ArtworkJson/" + id;
    req.setUrl(url);
      String str  = new String(req.getResponseData());
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                String str = new String(req.getResponseData());
                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(str.toCharArray()));

                int idArtwork = Integer.parseInt(obj.get("idArtwork").toString());
                String nameArtwork = obj.get("nameArtwork").toString();
                int idArtist = Integer.parseInt(obj.get("idArtist").toString());
                String artistName = obj.get("artistName").toString();
                
           
                
                String description = obj.get("description").toString();
                int idRoom = Integer.parseInt(obj.get("idRoom").toString());

                artwork.setIdArt(idArtwork);
                artwork.setArtworkName(nameArtwork);
                artwork.setIdArtist(idArtist);
                artwork.setArtistName(artistName);
                artwork.setDescription(description);
                artwork.setIdRoom(idRoom);
            } catch (IOException ex) {
                System.out.println("Error related to SQL: " + ex.getMessage());
            }

            System.out.println("Data === " + str);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return artwork;
}

// Delete Artwork
public boolean deleteArtwork(int id) {
    String url = Statics.BASE_URL + "/deleteArtworkJSON/"+id;

    req.setUrl(url);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            req.removeResponseCodeListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
}

// Update Artwork
public boolean updateArtwork(int id,Artwork artwork) {
    String url = Statics.BASE_URL + "/artwork/updateArtworkJSON/"+id+"?artworkName="+artwork.getArtworkName()+"&artistName="+artwork.getArtistName()+ "&idArtist=" + artwork.getIdArtist() +
                 "&artistName=" + artwork.getArtistName() + "&dateArt=" + 
                 artwork.getDateArt() + "&description=" + artwork.getDescription() +
                 "&idRoom=" + artwork.getIdRoom();
        req.setUrl(url);
        

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req); // Execution of the request
    return resultOk;
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

 public ArrayList<String> getArtworkImages(int idArt) {
        String url = Statics.BASE_URL + "/mobile/" + idArt + "/images";
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


}
