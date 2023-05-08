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
    
    String url = Statics.BASE_URL + "/artwork/AllJson";
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
                    
                    
                    int idArtwork = Math.round(Float.parseFloat(obj.get("idArtwork").toString()));
                    
                    String nameArtwork = obj.get("nameArtwork").toString();
                    
                    int idArtist = Math.round(Float.parseFloat(obj.get("idArtist").toString()));
                    
                    String artistName = obj.get("artistName").toString();
                    
                    // Parse the date from the string representation
                    String dateString = obj.get("dateArt").toString();
                    
                    
                    String description = obj.get("description").toString();
                    
                    int idRoom = Math.round(Float.parseFloat(obj.get("idRoom").toString()));
                    
                    artwork.setIdArt(idArtwork);
                    artwork.setArtworkName(nameArtwork);
                    artwork.setIdArtist(idArtist);
                    artwork.setArtistName(artistName);
                    artwork.setDateArt(dateString);
                    artwork.setDescription(description);
                    artwork.setIdRoom(idRoom);
                    
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
    String url = Statics.BASE_URL + "/artwork/deleteArtworkJSON/"+id;

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


}
