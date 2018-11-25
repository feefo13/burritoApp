package com.example.fernando.myfirstapp;

import java.util.Date;

public class Item {
    String name;
    String mealCode;
    Double price;
    Date timePlaced;


    // build your own
    public Item (String name, String mealCode, Double  price){
        this.name = name;
        this.mealCode = mealCode;
        this.price = price;
        Date time = new Date();
        this.timePlaced = time;
    }

    // simple items
    public Item (String name, Double  price){
        this.name = name;
        this.price = price;
        Date time = new Date();
        this.timePlaced = time;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getMealCode() {
        return mealCode;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }
}
