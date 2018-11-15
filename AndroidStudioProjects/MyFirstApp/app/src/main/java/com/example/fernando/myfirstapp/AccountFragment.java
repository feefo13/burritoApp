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

public class AccountFragment extends Fragment implements View.OnClickListener  {
    private static final String FILENAME = "users.json";
    private static final Integer init_memberID = 116435;
    Integer nextAvailableMemberID;

    View v;
    private FragmentActivity myContext;

    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences.Editor editor;

    EditText email;
    EditText password;
    EditText confirm_password;
    EditText name;
    EditText address;
    EditText phone;

    public AccountFragment() {
        // Required empty public constructor

    }

    public boolean isAccountViewBeforeLogIn(){
        prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        boolean boolAccountViewSetting = prefShared.getBoolean("viewBeforeLogin", false);
        if (boolAccountViewSetting == true) {
            /*
            source view prior to log in is Rewards
             */
            return true;

        }
        else{
            /*
            source view prior to log in is Account
             */
            return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        Button button = (Button) v.findViewById(R.id.create_user_button);
        button.setOnClickListener(this);
        email = (EditText) v.findViewById(R.id.text_input_email);
        password = (EditText) v.findViewById(R.id.text_input_password);
        confirm_password = (EditText) v.findViewById(R.id.text_input_confirm_password);
        name = (EditText) v.findViewById(R.id.text_input_name);
        address = (EditText) v.findViewById(R.id.text_input_address);
        phone = (EditText) v.findViewById(R.id.text_input_phone_number);

        return v;
    }

    @Override
    public void onClick(View v) {
        // read users from json file, populate list
        List<User> userList = new ArrayList<User>();
        userList = readFromFile(userList);  // comment out when database is 0

        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        String email_str = email.getText().toString();
        String password_str = password.getText().toString();
        String confirm_password_str = confirm_password.getText().toString();
        String name_str = name.getText().toString();
        String address_str = address.getText().toString();
        String phone_str = phone.getText().toString();

        if (password_str.equals(confirm_password_str)){

            // verify password, and that email is unused. pass data to json file
            boolean verifyFlag = true;

            if (userList.size() == 0){
            //
            }
            else{
                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    if (email_str.equals(old_email)){
                        verifyFlag = false;
                    }
                }
            }



            if (verifyFlag){
                Gson gson = new Gson();
                //nextAvailableMemberID = init_memberID + userList.size(); // delete later for when database is 0
                User newUser = new User( email_str,  password_str,  name_str,  address_str,  phone_str, nextAvailableMemberID, 0);
                userList.add(newUser);

                // converts object to json string, print to
                Type type = new TypeToken<List<User>>() {}.getType();
                String json = gson.toJson(userList, type);

                writeToFile(json);

                prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                editor = prefShared.edit();
                editor.putBoolean("userLoggedIn", true);
                editor.commit();

                if (isAccountViewBeforeLogIn()){
                    selectedFragment = new RewardsFragment();
                }
                else{
                    selectedFragment = new AccountInfoFragment();
                }

                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            else{
                selectedFragment = new InvalidInputFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }



        }
        else {
            selectedFragment = new InvalidInputFragment();
            fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
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
            nextAvailableMemberID = init_memberID + userList.size();
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
