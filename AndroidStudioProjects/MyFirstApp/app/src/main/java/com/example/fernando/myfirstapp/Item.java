package com.example.fernando.myfirstapp;

public class Item {
    String name;
    String mealCode;
    Double price;


    // build your own
    public Item (String name, String mealCode, Double  price){
        this.name = name;
        this.mealCode = mealCode;
        this.price = price;
    }

    // simple items
    public Item (String name, Double  price){
        this.name = name;
        this.price = price;
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
}
