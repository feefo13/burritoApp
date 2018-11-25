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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SelectedFaveOrderFragment extends Fragment implements View.OnClickListener {

    View v;
    private FragmentActivity myContext;
    private static final String FILENAME = "users.json";
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";


    TextView type;
    TextView Total;
    TextView Subtotal;
    TextView Tax;

    double subtotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_selectedfaveorder, container, false) ;

        type = (TextView) v.findViewById(R.id.deliveryMethod);
        Total = (TextView) v.findViewById(R.id.total);
        Tax = (TextView) v.findViewById(R.id.tax);
        Subtotal = (TextView) v.findViewById(R.id.subtotal);

        Button addToCart = (Button) v.findViewById(R.id.faveOrder_addToCart);
        addToCart.setOnClickListener(this);

        prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);
        String email_str = prefShared.getString("email", "N/A");
        Integer selectedOrderIndex = prefShared.getInt("selectedOrderIndex", 0);


        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)){
                List<Order> faveOrders = oldUser.getFavoriteOrders();
                Integer counter = 2;
                TableLayout tl =  (TableLayout) v.findViewById(R.id.faveordertablelayout);
                DecimalFormat df = new DecimalFormat("#.00");
                for (Order order : faveOrders){

                    // search through user's fave orders for a match in index... load the data for this order

                    Date time = order.getTimePlaced();
                    SimpleDateFormat format = new SimpleDateFormat("wwuHHmmss");  // week ##, day of week, hour, min, sec = index
                    String time_str = format.format(time);
                    Integer index = Integer.valueOf(time_str);

                    Log.d("tag", String.valueOf(index) + " " + String.valueOf(selectedOrderIndex));

                    if (index.equals(selectedOrderIndex)){       // matched found

                        List<Item> orderItems = order.getOrderItems();
                        String deliveryMethod_str = order.getDeliveryMethod();


                        for (Item item : orderItems){
                            TableRow tr = new TableRow(myContext);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tr.setLayoutParams(lp);

                            // set a text view
                            TextView tv = new TextView(myContext);
                            tv.setText("1x: ");
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

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
                        type.setText(deliveryMethod_str);

                    }


                }




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

        switch(v.getId()){

            case R.id.faveOrder_addToCart:

                List<User> userList = new ArrayList<User>();
                userList = readFromFile(userList);

                SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                String email_str = prefShared.getString("email", "N/A");
                Integer selectedOrderIndex = prefShared.getInt("selectedOrderIndex", 0);


                for (User oldUser : userList){
                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){

                        boolean isCartEmpty = false;
                        oldUser.setIsCartEmpty(isCartEmpty);
                        Gson gson = new Gson();

                        List<Order> faveOrders = oldUser.getOrder_num_list();

                        for (Order order : faveOrders) {

                            // search through user's fave orders for a match in index... load the data for this order

                            Date time = order.getTimePlaced();
                            SimpleDateFormat format = new SimpleDateFormat("wwuHHmmss");  // week ##, day of week, hour, min, sec = index
                            String time_str = format.format(time);
                            Integer index = Integer.valueOf(time_str);

                            if (index.equals(selectedOrderIndex)) { // matched found
                                List<Item> orderItems = order.getOrderItems();

                                for (Item item : orderItems){
                                    oldUser.addToCart(item); // add to item to cart of user in list
                                }

                            }
                        }


                        Type type = new TypeToken<List<User>>() {}.getType();
                        String json = gson.toJson(userList, type);
                        // save all users to file
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
