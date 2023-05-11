/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;





/**
 *
 * @author venom-1
 */
public class users {
    String firstname,lastname,email,pwd,address,gender;
   String role;
   int id,cin,phone_number;
   String birth_date;
   String status,image,secretcode;
   
 public users() {
       
    }

    public users(String firstname, String lastname, String email, String address, String gender, int id, int cin, int phone_number, String birth_date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.id = id;
        this.cin = cin;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
    }

    public users(String firstname, String lastname, String email, String pwd, String address, String gender, int cin, int phone_number, String birth_date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.gender = gender;
        this.cin = cin;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
    }

    public users(String firstname, String lastname, String email, String pwd, String address, String gender, int cin, int phone_number) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.gender = gender;
        this.cin = cin;
        this.phone_number = phone_number;

    }
    public users(int id,int cin,String firstname, String lastname, String email, String pwd, String address,int phone_number,  String birth_date,String gender, String role, String status) {
        this.id = id;
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;
        this.status = status;
   
    }
      public users(int id,int cin,String firstname, String lastname, String email, String pwd, String address,int phone_number,  String birth_date,String gender, String role) {
        this.id = id;
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;
   
    }
     
      public users(int id,int cin,String firstname, String lastname, String email, String pwd, String address,int phone_number,  String birth_date,String gender, String role, String status, String image, String secretcode) {
        this.id = id;
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.image=image;
        this.secretcode=secretcode;
   
    }
     
     
    public users(int cin,String firstname, String lastname, String email, String pwd, String address,int phone_number,  String birth_date,String gender, String role) {
        
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;

    }
   
    public users(int cin,String firstname, String lastname, String pwd, String address,int phone_number,  String birth_date,String gender, String role, String status) {
        
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;
        this.status = status;
        
    }
    public users(int cin,String firstname, String lastname, String email, String pwd, String address,int phone_number, String birth_date,String gender, String role, String status) {
        
        this.cin = cin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
        this.role = role;
        this.status = status;
        
        
    }

    
    
     
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "users{" + "firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", pwd=" + pwd + ", address=" + address + ", gender=" + gender + ", role=" + role + ", id=" + id + ", cin=" + cin + ", phone_number=" + phone_number + ", birth_date=" + birth_date + ", status=" + status + ", image=" + image + ", secretcode=" + secretcode + '}';
    }
   
    
   

    public String getSecretcode() {
        return secretcode;
    }

    public void setSecretcode(String secretcode) {
        this.secretcode = secretcode;
    }
}
