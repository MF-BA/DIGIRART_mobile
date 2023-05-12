/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author User
 */
public class Ticket {
    private float ticket_id;
    private String  ticket_date;
    private String  ticket_edate;
    private int price;
    private String ticket_type;

     public Ticket() {
    }
     
    public Ticket(float ticket_id, String  ticket_date, String  ticket_edate, int price, String ticket_type) {
        this.ticket_id = ticket_id;
        this.ticket_date = ticket_date;
        this.ticket_edate = ticket_edate;
        this.price = price;
        this.ticket_type = ticket_type;
    }
    
    public Ticket(String  date, String  edate, int price, String type) {
    this.ticket_date = date;
    this.ticket_edate = edate;
    this.price = price;
    this.ticket_type = type;
}
   
    public float getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(float ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String  getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(String  ticket_date) {
        this.ticket_date = ticket_date;
    }

    public String  getTicket_edate() {
        return ticket_edate;
    }

    public void setTicket_edate(String  ticket_edate) {
        this.ticket_edate = ticket_edate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", ticket_date=" + ticket_date +
                ", ticket_edate=" + ticket_edate +
                ", price=" + price +
                ", ticket_type='" + ticket_type + '\'' +
                '}';
    }   
}
