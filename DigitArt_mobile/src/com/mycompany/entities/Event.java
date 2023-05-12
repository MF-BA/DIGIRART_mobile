/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author kossay
 */
public class Event {
    
   
    private int id;

   
    private String eventName;

    
    private String startDate;

    
    private String endDate;

    
    private int nbParticipants;

   
    private String detail;

  
    private int startTime;


    private String image;

    private int idRoom;

    private Date createdAt;

 
    private Date updatedAt;

    
    private String color;

    public Event() {
    }

    public Event(int id, String eventName, String startDate, String endDate, int nbParticipants, String detail, int startTime, String image, int idRoom, Date createdAt, Date updatedAt, String color) {
        this.id = id;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbParticipants = nbParticipants;
        this.detail = detail;
        this.startTime = startTime;
        this.image = image;
        this.idRoom = idRoom;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.color = color;
    }

    public Event(String eventName, String startDate, String endDate, int nbParticipants, String detail, int startTime, String color) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbParticipants = nbParticipants;
        this.detail = detail;
        this.startTime = startTime;
        this.color = color;
    }
    
    

    public Event(String eventName, String startDate, String endDate, int nbParticipants, String detail, int startTime, String image, int idRoom, Date createdAt, Date updatedAt, String color) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbParticipants = nbParticipants;
        this.detail = detail;
        this.startTime = startTime;
        this.image = image;
        this.idRoom = idRoom;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(int nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    

    
}
