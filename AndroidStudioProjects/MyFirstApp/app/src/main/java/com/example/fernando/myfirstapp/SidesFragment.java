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


public class SidesFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences prefShared;
    private static final String FILENAME = "users.json";

    public SidesFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_sides, container, false);

        TextView chipsTextView = (TextView) v.findViewById(R.id.side_item1);
        chipsTextView.setOnClickListener(this);

        TextView salsaTextView = (TextView) v.findViewById(R.id.side_item2);
        salsaTextView.setOnClickListener(this);

        TextView guacTextView = (TextView) v.findViewById(R.id.side_item3);
        guacTextView.setOnClickListener(this);

        TextView quesoTextView = (TextView) v.findViewById(R.id.side_item4);
        quesoTextView.setOnClickListener(this);

        return v;
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

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        List<User> userList;

        Item item = null;

        double price;
        String itemType;

        switch(v.getId()){
            case R.id.side_item1:
                //chips
                itemType = "Chips";

                price= getPrice(itemType);
                item = new Item( itemType, price);

                break;

            case R.id.side_item2:
                //salsa
                itemType = "Chips & Salsa";
                price= getPrice(itemType);
                item = new Item(itemType, price);
                break;

            case R.id.side_item3:
                //guac
                itemType = "Chips & Guac";
                price= getPrice(itemType);
                item = new Item(itemType, price);
                break;

            case R.id.side_item4:
                //queso
                itemType = "Chips & Queso";
                price= getPrice(itemType);
                item = new Item(itemType, price);
                break;

        }




        userList = new ArrayList<User>();
        userList = readFromFile(userList);

        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        String email_str = prefShared.getString("email", "N/A");

        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)) {

                boolean isCartEmpty = false;
                oldUser.setIsCartEmpty(isCartEmpty);

                    Gson gson = new Gson();
                    oldUser.addToCart(item); // add to item to cart of user in list

                    prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefShared.edit();
                    editor.putBoolean("cartEmpty", false);
                    editor.commit();

                    Type type = new TypeToken<List<User>>() {
                    }.getType();
                    String json = gson.toJson(userList, type);
                    // save all users and their to file
                    writeToFile(json);
            }
        }



        selectedFragment = new MenuFragment();
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
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


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
