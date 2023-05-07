/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.io.Preferences;

/**
 *
 * @author venom-1
 */
public class SessionUser {
    public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id ; 
    private static int cin ; 
    
    private static String password ;
    private static String image;
    private static String firstname;
    private static String lastname;
    private static String email; 
    private static String address; 
    private static int phoneNum; 
    private static String gender; 
    private static String role; 
    private static String birthDate; 


    
   /*
    nzid les attributs lb9eya mba3ed mantasti w kol chy yemchi
    */

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionUser.pref = pref;
    }

    public static int getId() {
        return pref.get("id",id);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setId(int id) {
        pref.set("id",id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static int getCin() {
        return pref.get("cin",cin);
    }

    public static void setCin(int cin) {
         pref.set("cin",cin);
    }
     public static int getPhonenum() {
        return pref.get("phoneNum",phoneNum);
    }

    public static void setPhonenum(int phoneNum) {
         pref.set("phoneNum",phoneNum);
    }
    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
         pref.set("email",email);
    }

    public static String getPassword() {
        return pref.get("password",password);
    }

    public static void setPassword(String password) {
         pref.set("password",password);
    }

    public static String getImage() {
        return pref.get("image",image);
    }

    public static void setImage(String image) {
         pref.set("image",image);
    }
    
    public static String getFirstname() {
        return pref.get("firstname",firstname);
    }

    public static void setFirstname(String firstname) {
         pref.set("firstname",firstname);
    }
    
    public static String getLastname() {
        return pref.get("lastname",lastname);
    }

    public static void setLastname(String lastname) {
         pref.set("lastname",lastname);
    }
     public static String getAddress() {
        return pref.get("address",address);
    }

    public static void setAddress(String address) {
         pref.set("address",address);
    }
     public static String getGender() {
        return pref.get("gender",gender);
    }

    public static void setGender(String gender) {
         pref.set("gender",gender);
    }
     public static String getRole() {
        return pref.get("role",role);
    }

    public static void setRole(String role) {
         pref.set("role",role);
    }
     public static String getBirthDate() {
        return pref.get("birthDate",birthDate);
    }

    public static void setBirthDate(String birthDate) {
         pref.set("birthDate",birthDate);
    }
}
