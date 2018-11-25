package com.example.fernando.myfirstapp;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class OtherFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences prefShared;
    private static final String FILENAME = "users.json";

    Integer item1;
    Integer item2;
    Integer item3;
    Integer item4;
    Integer item5;
    Integer item6;
    Integer item7;
    String mealCodeStr = "";
    public OtherFragment() {
        // Required empty public constructor

    }

    public String getMealCodeStr(String string2){

        prefShared = getActivity().getApplicationContext().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        Integer meat = prefShared.getInt("meat", 5);
        Integer tortilla = prefShared.getInt("tortilla", 4);
        Integer rice = prefShared.getInt("rice", 3);
        Integer bean = prefShared.getInt("bean", 3);
        Integer cheese = prefShared.getInt("cheese", 3);
        Integer sauce = prefShared.getInt("sauce", 3);

        Integer meatp = prefShared.getInt("meatp", 1);
        Integer tortillap = prefShared.getInt("tortillap", 1);
        Integer ricep = prefShared.getInt("ricep", 1);
        Integer beanp = prefShared.getInt("beanp", 1);
        Integer cheesep = prefShared.getInt("cheesep", 1);
        Integer saucep = prefShared.getInt("saucep", 1);

        Integer item1 = prefShared.getInt("item1", 0);
        Integer item2 = prefShared.getInt("item2", 0);
        Integer item3 = prefShared.getInt("item3", 0);
        Integer item4 = prefShared.getInt("item4", 0);
        Integer item5 = prefShared.getInt("item5", 0);
        Integer item6 = prefShared.getInt("item6", 0);
        Integer item7 = prefShared.getInt("item7", 0);

        string2 = "" + meat.toString() +","+ tortilla.toString() +","+ rice.toString() +","+ bean.toString() +","+ cheese.toString() +","+ sauce.toString();
        string2 = string2 +","+ meatp.toString() +","+ tortillap.toString() +","+ ricep.toString() +","+ beanp.toString() +","+ cheesep.toString() +","+ saucep.toString();
        string2 = string2 +","+ item1.toString() +","+ item2.toString() +","+ item3.toString() +","+ item4.toString() +","+ item5.toString() +","+ item6.toString() +","+ item7.toString();

        return string2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_ingred_other, container, false);

        Button button = (Button)v.findViewById(R.id.other_add_to_cart);
        button.setOnClickListener(this);

        Button button2 = (Button)v.findViewById(R.id.other_add_to_faves);
        button2.setOnClickListener(this);

        item1 = 0;
        item2 = 0;
        item3 = 0;
        item4 = 0;
        item5 = 0;
        item6 = 0;
        item7 = 0;

        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.other_checkBox1);
        final CheckBox checkBox2 = (CheckBox) v.findViewById(R.id.other_checkBox2);
        final CheckBox checkBox3 = (CheckBox) v.findViewById(R.id.other_checkBox3);
        final CheckBox checkBox4 = (CheckBox) v.findViewById(R.id.other_checkBox4);
        final CheckBox checkBox5 = (CheckBox) v.findViewById(R.id.other_checkBox5);
        final CheckBox checkBox6 = (CheckBox) v.findViewById(R.id.other_checkBox6);
        final CheckBox checkBox7 = (CheckBox) v.findViewById(R.id.other_checkBox7);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    // add peppers to item
                    item1 = 1;
                }
                else{
                    item1 = 0;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    // add onions to item
                    item2 = 1;
                }
                else{
                    item2 = 0;
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    // add ingredient to item
                    item3 = 1;
                }
                else{
                    item3 = 0;
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox4.isChecked()) {
                    // add ingredient to item
                    item4 = 1;
                }
                else{
                    item4 = 0;
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox5.isChecked()) {
                    // add ingredient to item
                    item5 = 1;
                }
                else{
                    item5 = 0;
                }
            }
        });

        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox6.isChecked()) {
                    // add ingredient to item
                    item6 = 1;
                }
                else{
                    item6 = 0;
                }
            }
        });

        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox7.isChecked()) {
                    // add ingredient to item
                    item7 = 1;
                }
                else{
                    item7 = 0;
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        Item item;
        String itemType;
        double price;
        List<User> userList;
        String email_str;

        prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        itemType = prefShared.getString("itemType", "N/A");

        pushOtherToSharedPref();
        mealCodeStr = getMealCodeStr(mealCodeStr);

        switch(v.getId()){
            case R.id.other_add_to_cart:
                // Add item to cart
                mealCodeStr = getMealCodeStr(mealCodeStr);

                price = getPrice(itemType);
                if (itemType.equals("Burrito") ||  itemType.equals("Taco") || itemType.equals("Kids") ){
                    item = new Item( itemType, mealCodeStr, price);
                }
                else{
                    // simple item
                    item = new Item( itemType, price);
                }

                userList = new ArrayList<User>();
                userList = readFromFile(userList);

                SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                email_str = prefShared.getString("email", "N/A");

                for (User oldUser : userList){
                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){

                        boolean isCartEmpty = false;
                        oldUser.setIsCartEmpty(isCartEmpty);
                            Gson gson = new Gson();
                            oldUser.addToCart(item); // add to item to cart of user in list

                            Type type = new TypeToken<List<User>>() {}.getType();
                            String json = gson.toJson(userList, type);
                            // save all users to file
                            writeToFile(json);
                        }
                    }



                selectedFragment = new CartFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;

            case R.id.other_add_to_faves:
                // Add item to favorites

                price = getPrice(itemType);
                if (itemType.equals("Burrito") ||  itemType.equals("Taco") || itemType.equals("Kids") ){
                    item = new Item( itemType, mealCodeStr, price);
                }
                else{
                    // simple item
                    item = new Item( itemType, price);
                }

                userList = new ArrayList<User>();
                userList = readFromFile(userList);

                prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                email_str = prefShared.getString("email", "N/A");

                for (User oldUser : userList){
                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){

                        Gson gson = new Gson();
                        oldUser.addToFavoriteItems(item); // add to item to favoriteItemsList of user in list
                        boolean value = true;
                        oldUser.setAreFavoriteItemsStored(value);

                        Type type = new TypeToken<List<User>>() {}.getType();
                        String json = gson.toJson(userList, type);
                        // save all users and their to file
                        writeToFile(json);
                    }
                }

                break;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public double getPrice(String itemType){
        double price = 0;          //0      1       2       3       4               5               6               7       8       9      10   11          12      13
        String[] priceMatrix =  {"Burrito","Taco","Kids","Chips", "Chips & Salsa","Chips & Guac", "Chips & Queso","Coke","Water","Tea","Juice","Cookie","Brownie","Reward"};

        if (itemType.equals(priceMatrix[0])){
            price = 7.00;
        }
        else {
            if(itemType.equals(priceMatrix[1]))
            {
                price = 5.50;
            }
            if(itemType.equals(priceMatrix[2]) || itemType.equals(priceMatrix[3]) || itemType.equals(priceMatrix[4]) || itemType.equals(priceMatrix[5]) || itemType.equals(priceMatrix[6])  )
            {
                price = 2.50;
            }
            if( itemType.equals(priceMatrix[7]) || itemType.equals(priceMatrix[8]) || itemType.equals(priceMatrix[9]) || itemType.equals(priceMatrix[10]) || itemType.equals(priceMatrix[11]) || itemType.equals(priceMatrix[12]))
            {
                price = 2.00;
            }

        }
        return price;

    }


    private List<User> readFromFile(List<User> userList){

        FileInputStream fis = null;
        try {
            fis = myContext.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            Type type = new TypeToken<List<User>>() {}.getType();

            userList =  new Gson().fromJson(br, type);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis !=null){
                try {
                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }

    private void writeToFile(String data) {
        FileOutputStream fos = null;

        try {
            fos = myContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
        }
        catch (IOException e) {
            System.out.println("Write to file failed");
        }
        finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pushOtherToSharedPref(){
        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();
        editor.putInt("item1", item1);
        editor.commit();
        editor.putInt("item2", item2);
        editor.commit();
        editor.putInt("item3", item3);
        editor.commit();
        editor.putInt("item4", item4);
        editor.commit();
        editor.putInt("item5", item5);
        editor.commit();
        editor.putInt("item6", item6);
        editor.commit();
        editor.putInt("item7", item7);
        editor.commit();
    }
}
