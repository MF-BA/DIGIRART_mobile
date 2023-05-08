/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomany.entities;


import java.util.Date;

/**
 *
 * @author fedi1
 */
public class Auction {

    int id_auction, starting_price, increment, id_artwork;
    Date date;
    String description;

    public Auction() {
    }

//constructors
    public Auction(int id_auction, int starting_price, int increment, int id_artwork, Date date, String description) {
        this.id_auction = id_auction;
        this.starting_price = starting_price;
        this.increment = increment;
        this.id_artwork = id_artwork;
        this.date = date;
        this.description = description;
    }

    public Auction(int starting_price, int increment, int id_artwork, Date date, String description) {
        this.starting_price = starting_price;
        this.increment = increment;
        this.id_artwork = id_artwork;
        this.date = date;
        this.description = description;
    }

    public Auction(int starting_price, int id_artwork, Date date, String description) {
        this.starting_price = starting_price;
        this.id_artwork = id_artwork;
        this.date = date;
        this.description = description;
    }
    
//getters
    public int getId_auction() {
        return id_auction;
    }

    public int getStarting_price() {
        return starting_price;
    }

    public int getIncrement() {
        return increment;
    }

    public int getId_artwork() {
        return id_artwork;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

//setters
    public void setStarting_price(int starting_price) {
        this.starting_price = starting_price;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void setId_artwork(int id_artwork) {
        this.id_artwork = id_artwork;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[id_auction=" + id_auction + ", starting_price=" + starting_price + ", increment=" + increment
                + ", id_artwork=" + id_artwork + ", date=" + date + ", description=" + description + "]\n";
    }

}
