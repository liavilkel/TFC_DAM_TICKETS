package com.example.tfc_dam_tickets.model;

public class User {

    String email, password, name, lastName, phone_num, type, comId;

    public User(String email, String password, String name, String lastName, String phone_num, String type, String comId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phone_num = phone_num;
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

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }
}
