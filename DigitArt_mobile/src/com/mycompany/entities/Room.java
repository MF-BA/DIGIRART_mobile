/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author mohamed
 */
public class Room {

    
        private int idRoom;
        private String nameRoom;
        private int area;
        private String state;
        private String description;
        
        
    public Room() {}

   
    public Room(int idRoom, String nameRoom, int area, String state, String description) {
        this.idRoom = idRoom;
        this.nameRoom = nameRoom;
        this.area = area;
        this.state = state;
        this.description = description;
    }

    public Room(String nameRoom, int area, String state, String description) {
        this.nameRoom = nameRoom;
        this.area = area;
        this.state = state;
        this.description = description;
    }
    
     public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
}
