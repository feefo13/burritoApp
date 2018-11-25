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


public class RedeemFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences prefShared;
    private static final String FILENAME = "users.json";

    public RedeemFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_redeem, container, false);


        Button chipsButton = (Button) v.findViewById(R.id.redeem_item1);
        chipsButton.setOnClickListener(this);

        Button salsaButton = (Button) v.findViewById(R.id.redeem_item2);
        salsaButton.setOnClickListener(this);

        Button guacButton = (Button) v.findViewById(R.id.redeem_item3);
        guacButton.setOnClickListener(this);

        Button quesoButton = (Button) v.findViewById(R.id.redeem_item4);
        quesoButton.setOnClickListener(this);



        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        List<User> userList;

        double price = 0;
        Item item = null;
        String itemType;

        switch(v.getId()){
            case R.id.redeem_item1:
                //chips
                itemType = "Chips";

                item = new Item( itemType, price);

                break;

            case R.id.redeem_item2:
                //salsa
                itemType = "Chips & Salsa";
                item = new Item(itemType, price);
                break;

            case R.id.redeem_item3:
                //guac
                itemType = "Chips & Guac";
                item = new Item(itemType, price);
                break;

            case R.id.redeem_item4:
                //queso
                itemType = "Chips & Queso";
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
