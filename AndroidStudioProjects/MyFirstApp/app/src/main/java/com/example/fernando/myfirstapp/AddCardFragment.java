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
import android.widget.EditText;
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

public class AddCardFragment extends Fragment implements View.OnClickListener  {
    private static final String FILENAME = "users.json";

    View v;
    private FragmentActivity myContext;

    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";

    EditText cardnum;
    EditText ccv;
    EditText expiredate;
    EditText name;
    EditText zip;

    public AddCardFragment() {
        // Required empty public constructor

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addcard, container, false);

        Button button = (Button) v.findViewById(R.id.create_cc_button);
        button.setOnClickListener(this);

        name = (EditText) v.findViewById(R.id.text_input_cardholders_name);
        cardnum = (EditText) v.findViewById(R.id.text_input_card_number);
        expiredate = (EditText) v.findViewById(R.id.text_input_expire_date);
        ccv = (EditText) v.findViewById(R.id.text_input_security_code);
        zip = (EditText) v.findViewById(R.id.text_input_zipcode);

        return v;
    }

    @Override
    public void onClick(View v) {
        // read users from json file, populate list
        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);  // comment out when database is 0

        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        String cardnum_str = cardnum.getText().toString();
        String expiredate_str = expiredate.getText().toString();
        String ccv_str = ccv.getText().toString();
        String name_str = name.getText().toString();
        String zip_str = zip.getText().toString();

        Long cardnumm = Long.valueOf(cardnum_str);
        Integer ccvv = Integer.valueOf(ccv_str);
        Integer zipp = Integer.valueOf(zip_str);

        prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        String email_str = prefShared.getString("email", "N/A");

        switch (v.getId()) {
            case R.id.create_cc_button:
                //  add a card

                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)) {
                        CC cc = new CC(cardnumm, expiredate_str, ccvv, zipp);
                        oldUser.addToSetOfCCs(cc);
                        oldUser.setAreCardsStored(true);

                        Gson gson = new Gson();

                        Type type = new TypeToken<List<User>>() {}.getType();
                        String json = gson.toJson(userList, type);

                        writeToFile(json);

                        selectedFragment = new SelectCardFragment();
                        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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
