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
import java.util.ArrayList;
import java.util.List;

public class SelectCardFragment extends Fragment implements View.OnClickListener  {
    private static final String FILENAME = "users.json";
    View v;
    private FragmentActivity myContext;
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";


    public SelectCardFragment() {
        // Required empty public constructor

    }


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selectcard, container, false);

        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);  

        TextView button = (TextView) v.findViewById(R.id.addCard);
        button.setOnClickListener(this);
        prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        String email_str = prefShared.getString("email", "N/A");

        for (User oldUser : userList) {
            String old_email = oldUser.getEmail();
            if (email_str.equals(old_email)) {

                if (oldUser.getAreCardsStored()){

                    List<CC> currentCards = oldUser.getStoredCards();
                    Integer counter = 0;
                    TableLayout tl = (TableLayout) v.findViewById(R.id.selectcardtablelayout);

                    for (CC cc : currentCards) {

                        TableRow tr = new TableRow(myContext);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        tr.setLayoutParams(lp);

                        // set a text view
                        TextView tv = new TextView(myContext);

                        String cc_name = cc.getCardnum();

                        tv.setText(cc_name);
                        tv.setTextSize(24);
                        tv.setId(1); // set unique id for each to index the push from prior order to cart
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

        switch(v.getId()){

            case R.id.addCard:
                //

                selectedFragment = new AddCardFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;

            case 1:

                selectedFragment = new CheckoutFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;
        }



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
