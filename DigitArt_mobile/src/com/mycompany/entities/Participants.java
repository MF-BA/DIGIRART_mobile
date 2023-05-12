/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author kossay
 */
public class Participants {
    
     private String firstName;

    private int id;

   
    private String lastName;

   
    private String adress;

    
    private String gender;

      private int idEvent;

    
    private int idUser;

    public Participants() {
    }

    public Participants(String firstName, int id, String lastName, String adress, String gender, int idEvent, int idUser) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.adress = adress;
        this.gender = gender;
        this.idEvent = idEvent;
        this.idUser = idUser;
    }

    public Participants(int idEvent, int idUser,String firstName, String lastName, String adress, String gender ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.gender = gender;
        this.idEvent = idEvent;
        this.idUser = idUser;
    }
    
                       //127.0.0.1:8000/participants/addParticipant/Json?id_event=7&id_user=4&first_name="zizou"&last_name="loukil"&address="soukra"&gender="Female"


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String $lastName) {
        this.lastName = $lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

   
    
}
