package com.example.fernando.myfirstapp;

public class Item {
        String name;
    String mealCode;
    Float price;


    // build your own
    public Item (String name, String mealCode, Float  price){
        this.name = name;
        this.mealCode = mealCode;
        this.price = price;
    }

    // simple items
    public Item (String name, Float  price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getMealCode() {
        return mealCode;
    }
}
