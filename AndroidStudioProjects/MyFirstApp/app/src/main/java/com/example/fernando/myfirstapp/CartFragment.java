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
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
        userList = readFromFile(userList);
        String email_str = prefShared.getString("email", "N/A");

        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)){
                List<Item> currentCart = oldUser.getCartItems();
                Integer counter = 4;
                TableLayout tl =  (TableLayout) v.findViewById(R.id.carttablelayout);
                DecimalFormat df = new DecimalFormat("#.00");
                for (Item item : currentCart){

                    TableRow tr = new TableRow(myContext);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(lp);

                        // set a text view
                    TextView tv = new TextView(myContext);
                    tv.setText("1x:");

                    TextView tv2 = new TextView(myContext);
                        String item1_name = item.getName();
                        double item1_price = item.getPrice();
                        subtotal = subtotal + item1_price;
                        String item1_price_str = df.format(item1_price);
                        String item1_str = item1_name + " - $" + item1_price_str;

                        tv2.setText(item1_str);

                        tr.addView(tv);
                    tr.addView(tv2);
                    tl.addView(tr,counter);
                    counter = counter + 1;

                }



                double tax = subtotal * .08;
                double total = subtotal + tax;

                String Subtotal_str = df.format(subtotal);
                String Total_str = df.format(total);
                String Tax_str = df.format(tax);
                Subtotal_str = "$"+ Subtotal_str;
                Total_str = "$"+ Total_str;
                Tax_str = "$"+ Tax_str;

                Total.setText(Total_str);
                Subtotal.setText(Subtotal_str);
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
        String deliveryMethod_str = prefShared.getString("deliveryMethod", "N/A");

        switch(v.getId()) {
            case R.id.checkout:

                if (subtotal != 0){
                    // add a payment method
                    for (User oldUser : userList) {

                        String old_email = oldUser.getEmail();
                        if (email_str.equals(old_email)){
                            if (oldUser.getAreCardsStored()){
                                selectedFragment = new SelectCardFragment();
                                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            }
                            else{
                                selectedFragment = new AddCardFragment();
                                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            }
                        }
                    }
                }
                else{
                    selectedFragment = new CheckoutFragment();
                    fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }

                break;

            case R.id.fave_order:

                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){
                        //
                        List<Item> newOrder = oldUser.getCartItems();
                        Integer memberID = oldUser.getMemberID();   // add logic to find next available order# by searching through users
                        Order order = new Order(memberID,deliveryMethod_str,newOrder);
                        oldUser.addToSetOfFaveOrders(order);
                        oldUser.setAreFavoriteOrdersStored(true);

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
