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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectedFaveItemFragment extends Fragment implements View.OnClickListener{
    View v;

    TextView meat;
    TextView tortilla;
    TextView rice;
    TextView beans;
    TextView cheese;
    TextView sauce;
    TextView peppers;
    TextView onions;
    TextView cilantro;
    TextView corn;
    TextView lettuce;
    TextView tomato;
    TextView sour;

    private static final String FILENAME = "users.json";
    private FragmentActivity myContext;
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_selectedfaveitem, container, false);

        Button addToCart = (Button) v.findViewById(R.id.faveItem_addToCart);
        addToCart.setOnClickListener(this);

        meat = (TextView)  v.findViewById(R.id.meat);
        tortilla = (TextView)  v.findViewById(R.id.tortilla);
        rice = (TextView)  v.findViewById(R.id.rice);
        beans = (TextView)  v.findViewById(R.id.beans);
        cheese = (TextView)  v.findViewById(R.id.cheese);
        sauce = (TextView)  v.findViewById(R.id.sauce);
        peppers = (TextView)  v.findViewById(R.id.peppers);
        onions = (TextView)  v.findViewById(R.id.onions);
        cilantro = (TextView)  v.findViewById(R.id.cilantro);
        corn = (TextView)  v.findViewById(R.id.corn);
        lettuce = (TextView)  v.findViewById(R.id.lettuce);
        tomato = (TextView)  v.findViewById(R.id.tomato);
        sour = (TextView)  v.findViewById(R.id.sour);


        // match up user, match user's favorite items to index
        prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);
        String email_str = prefShared.getString("email", "N/A");
        Integer selectedOrderIndex = prefShared.getInt("selectedItemIndex", 0);


        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)){
                List<Item> faveItems = oldUser.getFavoriteItems();

                for (Item item : faveItems) {

                    // search through user's favorite items for a match in index... load the data for this item

                    Date time = item.getTimePlaced();
                    SimpleDateFormat format = new SimpleDateFormat("wwuHHmmss");  // week ##, day of week, hour, min, sec = index
                    String time_str = format.format(time);
                    Integer index = Integer.valueOf(time_str);

                    if (index.equals(selectedOrderIndex)) { // matched found... set text in textviews

                        String mealcode = item.getMealCode();

                        String[] mealcode_list = mealcode.split(",");

                        // 1,1,1,1,1,1,0,0,0,0,0,0,1,0,1,0,1,0,1
                        // ingredient, portion,   other
                        // 6            6           7

                        Integer count = 0;

                        String meat_str = "";
                        String tortilla_str = "";
                        String rice_str = "";
                        String beans_str = "";
                        String cheese_str = "";
                        String sauce_str = "";
                        String peppers_str = "";
                        String onions_str = "";
                        String cilantro_str = "";
                        String corn_str = "";
                        String lettuce_str = "";
                        String tomato_str = "";
                        String sour_str = "";

                        while(count<6){
                            if (mealcode_list[count].equals("0")){  // set to none
                                switch(count){
                                    case 0: // meat
                                        meat_str = "none";

                                        break;

                                    case 1: // tortilla
                                        tortilla_str = "none";

                                        break;

                                    case 2: // rice
                                        rice_str = "none";

                                        break;

                                    case 3: // beans
                                        beans_str = "none";

                                        break;

                                    case 4: // cheese
                                        cheese_str = "none";

                                        break;

                                    case 5: // sauce
                                        sauce_str = "none";

                                        break;

                                }

                            }
                            else // 
                            {
                                switch(count){
                                    case 0: // meat

                                        if (mealcode_list[count].equals("1")){
                                            meat_str = "Chicken";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            meat_str = "Steak";
                                        }

                                        if (mealcode_list[count].equals("3")){
                                            meat_str = "Ham";
                                        }

                                        if (mealcode_list[count].equals("4")){
                                            meat_str = "Turkey";
                                        }

                                        break;

                                    case 1: // tortilla

                                        if (mealcode_list[count].equals("1")){
                                            tortilla_str = "Flour";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            tortilla_str = "Spinach";
                                        }

                                        if (mealcode_list[count].equals("3")){
                                            tortilla_str = "Whole Wheat";
                                        }

                                        break;

                                    case 2: // rice

                                        if (mealcode_list[count].equals("1")){
                                            rice_str = "Brown";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            rice_str = "Black";
                                        }

                                        break;

                                    case 3: // beans

                                        if (mealcode_list[count].equals("1")){
                                            beans_str = "Brown";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            beans_str = "Black";
                                        }

                                        break;

                                    case 4: // cheese

                                        if (mealcode_list[count].equals("1")){
                                            cheese_str = "Cheddar";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            cheese_str = "Mozzarella";
                                        }

                                        break;

                                    case 5: // sauce

                                        if (mealcode_list[count].equals("1")){
                                            sauce_str = "Spicy";
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            sauce_str = "Mild";
                                        }

                                        break;
                                }
                            }

                            count = count + 1;
                        }

                        while(count<12){

                            if (mealcode_list[count].equals("0")){ // set to regular
                                switch(count){
                                    case 6: // meat
                                        meat_str = "(Regular) " + meat_str;

                                        break;

                                    case 7: // tortilla
                                        tortilla_str = "(Regular) " + tortilla_str;

                                        break;

                                    case 8: // rice
                                        rice_str = "(Regular) " + rice_str;

                                        break;

                                    case 9: // beans
                                        beans_str = "(Regular) " + beans_str;

                                        break;

                                    case 10: // cheese
                                        cheese_str = "(Regular) " + cheese_str;
                                        break;

                                    case 11: // sauce
                                        sauce_str = "(Regular) " + sauce_str;

                                        break;
                                    }

                            }
                            else{

                                switch(count){

                                    case 6: // meat
                                        if (mealcode_list[count].equals("1")){
                                            meat_str = "(Half) " + meat_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            meat_str = "(Double) " + meat_str;
                                        }
                                        break;

                                    case 7: // tortilla
                                        if (mealcode_list[count].equals("1")){
                                            tortilla_str = "(Half) " + tortilla_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            tortilla_str = "(Double) " + tortilla_str;
                                        }
                                        break;

                                    case 8: // rice
                                        if (mealcode_list[count].equals("1")){
                                            rice_str = "(Half) " + rice_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            rice_str = "(Double) " + rice_str;
                                        }
                                        break;

                                    case 9: // beans
                                        if (mealcode_list[count].equals("1")){
                                            beans_str = "(Half) " + beans_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            beans_str = "(Double) " + beans_str;
                                        }
                                        break;

                                    case 10: // cheese
                                        if (mealcode_list[count].equals("1")){
                                            cheese_str = "(Half) " + cheese_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            cheese_str = "(Double) " + cheese_str;
                                        }                                        
                                        break;

                                    case 11: // sauce
                                        if (mealcode_list[count].equals("1")){
                                            sauce_str = "(Half) " + sauce_str;
                                        }

                                        if (mealcode_list[count].equals("2")){
                                            sauce_str = "(Double) " + sauce_str;
                                        }
                                        break;
                                }
                            }


                            count = count + 1;
                        }

                        while(count<19){

                            if(mealcode_list[count].equals("0")){ // set to none

                                switch(count) {
                                    case 12: // meat
                                        peppers_str = "none";

                                        break;

                                    case 13: // tortilla
                                        onions_str = "none";

                                        break;

                                    case 14: // rice
                                        cilantro_str = "none";

                                        break;

                                    case 15: // beans
                                        corn_str = "none";

                                        break;

                                    case 16: // beans
                                        lettuce_str = "none";

                                        break;

                                    case 17: // cheese
                                        tomato_str = "none";

                                        break;

                                    case 18: // sauce
                                        sour_str = "none";

                                        break;
                                }
                            }
                            else{
                                switch(count) {
                                    case 12: // meat
                                        peppers_str = "some";

                                        break;

                                    case 13: // tortilla
                                        onions_str = "some";

                                        break;

                                    case 14: // rice
                                        cilantro_str = "some";

                                        break;

                                    case 15: // beans
                                        corn_str = "some";

                                        break;

                                    case 16: // beans
                                        lettuce_str = "some";

                                        break;

                                    case 17: // cheese
                                        tomato_str = "some";

                                        break;

                                    case 18: // sauce
                                        sour_str = "some";

                                        break;
                                }
                            }

                            count = count + 1;
                        }

                        meat.setText(meat_str);
                        tortilla.setText(tortilla_str);
                        rice.setText(rice_str);
                        beans.setText(beans_str);
                        cheese.setText(cheese_str);
                        sauce.setText(sauce_str);
                        peppers.setText(peppers_str);
                        onions.setText(onions_str);
                        cilantro.setText(cilantro_str);
                        corn.setText(corn_str);
                        lettuce.setText(lettuce_str);
                        tomato.setText(tomato_str);
                        sour.setText(sour_str);

                    }
                }

                Gson gson = new Gson();

                // converts object to json string, print to
                Type type = new TypeToken<List<User>>() {}.getType();
                String json = gson.toJson(userList, type);

                writeToFile(json);

            }
        }
        
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.faveItem_addToCart:

                List<User> userList = new ArrayList<User>();
                userList = readFromFile(userList);
                String email_str = prefShared.getString("email", "N/A");
                Integer selectedOrderIndex = prefShared.getInt("selectedOrderIndex", 0);


                for (User oldUser : userList) {
                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){
                        List<Item> faveItems = oldUser.getFavoriteItems();

                        for (Item item : faveItems) {

                            // search through user's recent orders for a match in index... load the data for this order

                            Date time = item.getTimePlaced();
                            SimpleDateFormat format = new SimpleDateFormat("wwuHHmmss");  // week ##, day of week, hour, min, sec = index
                            String time_str = format.format(time);
                            Integer index = Integer.valueOf(time_str);

                            if (index.equals(selectedOrderIndex)) { // matched found

                                oldUser.addToCart(item);
                                oldUser.setIsCartEmpty(false);
                            }
                        }

                        Gson gson = new Gson();

                        // converts object to json string, print to
                        Type type = new TypeToken<List<User>>() {}.getType();
                        String json = gson.toJson(userList, type);

                        writeToFile(json);

                    }
                }


                break;

        }

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
}
