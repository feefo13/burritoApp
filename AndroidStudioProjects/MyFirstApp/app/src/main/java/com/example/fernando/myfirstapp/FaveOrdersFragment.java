package com.example.fernando.myfirstapp;


import android.annotation.SuppressLint;
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
import android.widget.EditText;
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
import java.util.Date;
import java.util.List;

public class FaveOrdersFragment extends Fragment implements View.OnClickListener  {
    private static final String FILENAME = "users.json";
    View v;
    private FragmentActivity myContext;
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";


    public FaveOrdersFragment() {
        // Required empty public constructor

    }


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_faveorders, container, false);

        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);  

        prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        String email_str = prefShared.getString("email", "N/A");

        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)) {

                if (oldUser.getAreFavoriteOrdersStored()){

                    List<Order> faveOrders = oldUser.getFavoriteOrders();
                    Integer counter = 0;
                    TableLayout tl = (TableLayout) v.findViewById(R.id.faveorderstablelayout);
                    DecimalFormat df = new DecimalFormat("#.00");

                    for (Order order: faveOrders) {

                        TableRow tr = new TableRow(myContext);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        tr.setLayoutParams(lp);

                        // set a text view
                        TextView tv = new TextView(myContext);

                        List<Item> faveItemsList = order.getOrderItems();
                        Double OrderTotal = 0.0;
                        for (Item item: faveItemsList){
                            double price = item.getPrice();
                            OrderTotal = OrderTotal + price;
                        }
                        OrderTotal = OrderTotal * 1.08;
                        Date time = order.getTimePlaced();
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mma");
                        String orderLabel = format.format(time) + " - $" + df.format(OrderTotal);

                        format = new SimpleDateFormat("wwuHHmmss");  // week ##, day of week, hour, min, sec = index
                        String index_str = format.format(time);
                        Integer index = Integer.valueOf(index_str);

                        tv.setText(orderLabel);
                        tv.setTextSize(24);
                        tv.setId(index);
                        tr.addView(tv);
                        tv.setOnClickListener(this);

                        tl.addView(tr, counter);
                        counter = counter + 1;

                    }
                }

            }
        }


        return v;
    }

    @Override
    public void onClick(View v) {
        // read users from json file, populate list

        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();
        editor.putInt("selectedOrderIndex", v.getId()); // used to match up order to push to cart... next frag will ask for this index
        editor.commit();

        selectedFragment = new SelectedFaveOrderFragment();       // display order contents, add to cart button
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();



    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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
