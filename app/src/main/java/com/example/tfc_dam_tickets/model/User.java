package com.example.tfc_dam_tickets.model;

public class User {

    String email, password, name, lastName, phoneNum, type;
    Long comId;

    public User(String email, String password, String name, String lastName, String phone_num, String type, Long comId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNum = phone_num;
        this.type = type;
        this.comId = comId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getComId() {return comId;}

    public void setComId(Long comId) {this.comId = comId;}
}
