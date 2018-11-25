package com.example.fernando.myfirstapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    String email ;
    String password ;
    String name ;
    String address ;
    String phone ;
    Integer memberID ;
    Integer rewardPoints ;
    Date date;

    Boolean areCardsStored ;
    Boolean areFavoriteOrdersStored ;
    Boolean areFavoriteItemsStored ;
    Boolean areOrdersPlaced ; // populate recent orders
    Boolean isCartEmpty;

    List<Order> order_num_list; // prior order numbers
    List<Item> favoriteItems;
    List<Order> favoriteOrders;
    List<CC> storedCards;
    List<Item> cartItems;



    // new users
    public User (String email ,String password ,String name ,String address ,String phone, Integer memberID, Integer rewardPoints, Date date) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.memberID = memberID;
        this.rewardPoints = rewardPoints;
        this.isCartEmpty = true;
        this.date = date;

        // set to true after
        this.areCardsStored = false;
        this.areFavoriteItemsStored= false;
        this.areFavoriteOrdersStored= false;
        this.areOrdersPlaced=false;

        this.favoriteOrders = new ArrayList<Order>();
        this.favoriteItems = new ArrayList<Item>();
        this.storedCards = new ArrayList<CC>();
        this.cartItems = new ArrayList<Item>();
        this.order_num_list = new ArrayList<Order>();

    }

    public Date getDate() {
        return date;
    }

    public Boolean getCartEmpty() {
        return isCartEmpty;
    }
    public void setIsCartEmpty(boolean value){
        this.isCartEmpty = value;
    }

    public List<Order> getOrder_num_list() {
        return this.order_num_list;
    }

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void addToOrderHistory(Order order){
        this.order_num_list.add(order);
    }

    public void addToSetOfFaveOrders(Order order){
        this.favoriteOrders.add(order);
    }

    public void addToFavoriteItems(Item item){
        this.favoriteItems.add(item);
    }

    public void addToCart(Item item){
        this.cartItems.add(item);
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


    public void setCartItems(List<Item> cartItems) {
        this.cartItems = new ArrayList<Item>(cartItems);
    }
}
