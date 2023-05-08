/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;


import java.util.Date;

/**
 *
 * @author mohamed
 */
public class Artwork {
    
    
    private int idArt;
    private String artworkName;
    private int idArtist;
    private String artistName;
    private String dateArt;
    private String description;
    private int idRoom;
    

    public Artwork() {
    }
    
    public Artwork(int idArt, String artworkName, int idArtist, String artistName, String dateArt, String description, int idRoom, String ownerType) {
        this.idArt = idArt;
        this.artworkName = artworkName;
        this.idArtist = idArtist;
        this.artistName = artistName;
        this.dateArt = dateArt;
        this.description = description;
        this.idRoom = idRoom;
      
    }

    public Artwork(String artworkName, int idArtist, String artistName, String dateArt, String description, int idRoom, String ownerType) {
        this.artworkName = artworkName;
        this.idArtist = idArtist;
        this.artistName = artistName;
        this.dateArt = dateArt;
        this.description = description;
        this.idRoom = idRoom;
       
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public String getArtworkName() {
        return artworkName;
    }

    public void setArtworkName(String artworkName) {
        this.artworkName = artworkName;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDateArt() {
        return dateArt;
    }

    public void setDateArt(String dateArt) {
        this.dateArt = dateArt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

 

   

    
    
}
