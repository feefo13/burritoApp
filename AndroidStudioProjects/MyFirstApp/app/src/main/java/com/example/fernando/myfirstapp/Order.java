package com.example.fernando.myfirstapp;

import java.util.Date;
import java.util.List;

public class Order {
    Date timePlaced;
    List<Item> orderItems;
    String deliveryMethod;


    public Order (String deliveryMethod, List<Item> orderItems){
        this.deliveryMethod = deliveryMethod;
        this.orderItems = orderItems;
        Date time = new Date();
        this.timePlaced = time;
    }

    public void addToSetOfOrderItems(Item item){
        this.orderItems.add(item);
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }
}
