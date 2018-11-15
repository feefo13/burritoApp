package com.example.fernando.myfirstapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    String email ;
    String password ;
    String name ;
    String address ;
    String phone ;
    Integer memberID ;
    Integer rewardPoints ;

    Boolean areCardsStored ;
    Boolean areFavoriteOrdersStored ;
    Boolean areFavoriteItemsStored ;
    Boolean areOrdersPlaced ; // populate recent orders

    Integer[] order_num_list;
    List<Item> favoriteItems;
    List<Order> favoriteOrders;
    List<CC> storedCards;



    // new users
    public User (String email ,String password ,String name ,String address ,String phone, Integer memberID, Integer rewardPoints) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.memberID = memberID;
        this.rewardPoints = rewardPoints;

        // set to true after
        this.areCardsStored = false;
        this.areFavoriteItemsStored= false;
        this.areFavoriteOrdersStored= false;
        this.areOrdersPlaced=false;


        this.favoriteOrders = new ArrayList<Order>();
        this.favoriteItems = new ArrayList<Item>();
        this.storedCards = new ArrayList<CC>();
    }

    public void addToSetOfOrderItems(Item item){
        this.favoriteItems.add(item);
    }

    public void addToSetOfOrders(Order order){
        this.favoriteOrders.add(order);
    }

    public void addToSetOfCCs(CC cc){
        this.storedCards.add(cc);
    }



    public void setAreOrdersPlaced(Boolean areOrdersPlaced) {
        this.areOrdersPlaced = areOrdersPlaced;
    }

    public void setAreFavoriteOrdersStored(Boolean areFavoriteOrdersStored) {
        this.areFavoriteOrdersStored = areFavoriteOrdersStored;
    }

    public void setAreFavoriteItemsStored(Boolean areFavoriteItemsStored) {
        this.areFavoriteItemsStored = areFavoriteItemsStored;
    }

    public void setAreCardsStored(Boolean areCardsStored) {
        this.areCardsStored = areCardsStored;
    }

    public List<CC> getStoredCards() {
        return storedCards;
    }

    public List<Order> getFavoriteOrders() {
        return favoriteOrders;
    }

    public List<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public Integer[] getOrder_num_list() {
        return order_num_list;
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

    public Integer getMemberID() {
        return memberID;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public Boolean getAreCardsStored() {
        return areCardsStored;
    }

    public Boolean getAreOrdersPlaced() {
        return areOrdersPlaced;
    }

    public Boolean getAreFavoriteItemsStored() {
        return areFavoriteItemsStored;
    }

    public Boolean getAreFavoriteOrdersStored() {
        return areFavoriteOrdersStored;
    }
}
