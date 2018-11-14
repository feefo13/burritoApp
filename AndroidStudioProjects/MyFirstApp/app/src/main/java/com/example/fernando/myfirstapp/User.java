package com.example.fernando.myfirstapp;

public class User {
    String email ;
    String password ;
    String name ;
    String address ;
    String phone ;

    public User (String email ,String password ,String name ,String address ,String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public String getEmail(){
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
