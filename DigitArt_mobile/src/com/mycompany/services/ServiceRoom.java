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
import com.codename1.ui.events.ActionListener;
import com.mycomany.entities.Room;
import com.mycomany.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author mohamed
 */
public class ServiceRoom {
     // Singleton instance
    public static ServiceRoom instance = null;
    
    public static boolean resultOk = true;

    // Initialization of the connection request
    private ConnectionRequest req;
    
    
    public static ServiceRoom getInstance() {
        if(instance == null)
            instance = new ServiceRoom();
        return instance;
    }
    
    
    
    public ServiceRoom() {
        req = new ConnectionRequest();
        
    }
    
    
    // Adding a Room
    public void addRoom(Room room) {
        
        String url = Statics.BASE_URL + "/room/addRoomJSON/new?nameRoom="+room.getNameRoom()+"&area="+room.getArea()+"&state="+room.getState()+"&description="+room.getDescription();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
    // Displaying Rooms
    public ArrayList<Room> displayRooms() {
        ArrayList<Room> result = new ArrayList<>();
        
        String url = Statics.BASE_URL + "/room/AllJson";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try {
                    Map<String, Object> mapRooms = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String, Object>> listOfMaps =  (List<Map<String, Object>>) mapRooms.get("root");
                    
                    for (Map<String, Object> obj : listOfMaps) {
                        Room room = new Room();
                        
                        
                        int idRoom = Math.round(Float.parseFloat(obj.get("idRoom").toString()));
                        
                        String nameRoom = obj.get("nameRoom").toString();
                        
                        int area = Math.round(Float.parseFloat(obj.get("area").toString()));
                        String State = obj.get("state").toString();
                        
                        String description = obj.get("description").toString();
                        
                        room.setIdRoom(idRoom);
                        room.setNameRoom(nameRoom);
                        room.setArea(area);
                        room.setState(State);
                        room.setDescription(description);
                        
                        result.add(room);
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return result;
        
        
    }
    
    
    
   // Get Room details
public Room getRoomDetails(int id, Room room) {

    String url = Statics.BASE_URL + "/room/RoomJson/" + id;
    req.setUrl(url);
    String str  = new String(req.getResponseData());
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            try {
                String str = new String(req.getResponseData());
                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(str.toCharArray()));

                int idRoom = Integer.parseInt(obj.get("idRoom").toString());
                String nameRoom = obj.get("nameRoom").toString();
                int area = Integer.parseInt(obj.get("area").toString());
                String description = obj.get("description").toString();

                room.setIdRoom(idRoom);
                room.setNameRoom(nameRoom);
                room.setArea(area);
                room.setDescription(description);

            } catch (IOException ex) {
                System.out.println("Error related to SQL: " + ex.getMessage());
            }

            System.out.println("Data === " + str);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return room;
}

// Delete Room
public boolean deleteRoom(int id) {
    String url = Statics.BASE_URL + "/room/deleteRoomJSON/"+id;

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

// Update Room
public boolean updateRoom(int id,Room room) {
    String url = Statics.BASE_URL + "/room/updateRoomJSON/"+id+"?nameRoom="+room.getNameRoom()+"&area="+room.getArea()+"&state="+room.getState()+"&description="+room.getDescription();
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