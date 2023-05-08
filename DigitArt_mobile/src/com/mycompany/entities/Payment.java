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
public class Payment {
    private int paymentId;
    
   
    private String purchaseDate;
    
   
    private int nbAdult;
    
 
    private int nbTeenager;
    
   
    private int nbStudent;
    
  
    private int totalPayment;
    
  
    private boolean paid;
    
    private int userid;
  
    //private User user;

    
       public Payment(int paymentid, int userid, String purchaseDate, int nbAdult, int nbTeenager, int nbStudent, int totalPayment) {
        this.paymentId = paymentid;
        this.userid = userid;
        this.purchaseDate = purchaseDate;
        this.nbAdult = nbAdult;
        this.nbTeenager = nbTeenager;
        this.nbStudent = nbStudent;
        this.totalPayment = totalPayment;
    }

    public Payment(int userid, String purchaseDate, int nbAdult, int nbTeenager, int nbStudent, int totalPayment) {
        this.userid = userid;
        this.purchaseDate = purchaseDate;
        this.nbAdult = nbAdult;
        this.nbTeenager = nbTeenager;
        this.nbStudent = nbStudent;
        this.totalPayment = totalPayment;
    }
    
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getNbAdult() {
        return nbAdult;
    }

    public void setNbAdult(int nbAdult) {
        this.nbAdult = nbAdult;
    }

    public int getNbTeenager() {
        return nbTeenager;
    }

    public void setNbTeenager(int nbTeenager) {
        this.nbTeenager = nbTeenager;
    }

    public int getNbStudent() {
        return nbStudent;
    }

    public void setNbStudent(int nbStudent) {
        this.nbStudent = nbStudent;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /*public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } */

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", purchaseDate=" + purchaseDate +
                ", nbAdult=" + nbAdult +
                ", nbTeenager=" + nbTeenager +
                ", nbStudent=" + nbStudent +
                ", totalPayment=" + totalPayment +
                '}';
    }    
}
