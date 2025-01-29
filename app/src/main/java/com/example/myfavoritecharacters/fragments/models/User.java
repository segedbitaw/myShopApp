package com.example.myfavoritecharacters.fragments.models;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;

    public User(String phone, String name,String email, String password) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public User(){
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.phone = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
