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
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements View.OnClickListener {

    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences prefShared;
    private static final String FILENAME = "users.json";

    TextView deliveryMethod;
    TextView item1;
    TextView Total;
    TextView Subtotal;
    TextView Tax;

    double subtotal;

    public CartFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cart, container, false);
        deliveryMethod = (TextView) v.findViewById(R.id.deliveryMethod);
        Total = (TextView) v.findViewById(R.id.total);
        Tax = (TextView) v.findViewById(R.id.tax);
        Subtotal = (TextView) v.findViewById(R.id.subtotal);

        prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        String deliveryMethod_str = prefShared.getString("deliveryMethod", "N/A");
        deliveryMethod.setText(deliveryMethod_str);

        Button checkout = (Button) v.findViewById(R.id.checkout);
        checkout.setOnClickListener(this);

        Button fave = (Button) v.findViewById(R.id.fave_order);
        fave.setOnClickListener(this);

        List<User> userList = new ArrayList<User>();
        String email_str = prefShared.getString("email", "N/A");
        Log.d("tag", email_str);

        for (User oldUser : userList) {
            Log.d("tag", "here2");
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)){
                List<Item> currentCart = oldUser.getCartItems();
                Log.d("tag", "here1");
                for (Item item : currentCart){
                    // set a text view
                    Log.d(getTag(), "here");
                    String item1_name = item.getName();
                    double item1_price = item.getPrice();
                    subtotal = subtotal + item1_price;
                    String item1_price_str = String.valueOf(item1_price);
                    String item1_str = item1_name + " - " + item1_price_str;

                    item1 = (TextView) v.findViewById(R.id.item1);

                    item1.setText(item1_str);

                }


                double tax = subtotal * .08;
                double total = subtotal + tax;

                String Total_str = String.valueOf(total);
                Total.setText(Total_str);

                String Subtotal_str = String.valueOf(subtotal);
                Subtotal.setText(Subtotal_str);

                String Tax_str = String.valueOf(tax);
                Tax.setText(Tax_str);





            }
        }



        return v;
    }


    @Override
    public void onClick(View v) {

        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);  // comment out when database is 0
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        String email_str = prefShared.getString("email", "N/A");


        switch(v.getId()) {
            case R.id.checkout:
                // check for list of cards, add rows.  add a card

                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){
                            // stored cc logic
                        }

                    }



                break;

            case R.id.fave_order:

                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){
                        //
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
            Gson gson = new Gson();
            Log.d("tag", gson.toJson(userList, type));

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
