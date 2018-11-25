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
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener {
    public static final String PREF_FILE_NAME = "userdetails";
    private static final String FILENAME = "users.json";
    EditText email;
    EditText password;

    View rootView;
    private FragmentActivity myContext;

    public LoginFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        Button button = (Button)rootView.findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(this);

        Button button2 = (Button)rootView.findViewById(R.id.email_sign_up_button);
        button2.setOnClickListener(this);

        email = (EditText) rootView.findViewById(R.id.text_input_email_signIn);
        password = (EditText) rootView.findViewById(R.id.text_input_password_signIn);

        return rootView;
    }

    public boolean isAccountViewBeforeLogIn(){
        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
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


    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        boolean switchFlag = true;
        switch(v.getId()){
            case R.id.email_sign_in_button: {

                switchFlag = false;
                boolean switchFlag2 = true;

                List<User> userList = new ArrayList<User>();
                userList = readFromFile(userList);
                String email_str = email.getText().toString();
                String password_str = password.getText().toString();

                for (User oldUser : userList) {

                    String old_email = oldUser.getEmail();
                    String old_password = oldUser.getPassword();
                    if (email_str.equals(old_email)) {
                        if (password_str.equals(old_password)) {
                            // load into shared prefs
                            SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefShared.edit();

                            switchFlag2 = false;
                            editor.putString("email", email_str);
                            editor.putString("name", oldUser.getName());
                            editor.putString("phone", oldUser.getPhone());
                            editor.putString("address", oldUser.getAddress());
                            editor.putInt("rewardPoints", oldUser.getRewardPoints());
                            editor.putInt("memberID", oldUser.getMemberID());
                            Date time = oldUser.getDate();
                            SimpleDateFormat format = new SimpleDateFormat("MM/dd");
                            String date_str = format.format(time);
                            editor.putString("date", date_str);
                            editor.putBoolean("userLoggedIn", true);
                            editor.commit();

                            if (isAccountViewBeforeLogIn()) {
                                selectedFragment = new RewardsFragment();
                            } else {
                                selectedFragment = new AccountInfoFragment();
                            }

                            fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            break;
                        }
                        else{
                            if (switchFlag2) {
                                selectedFragment = new InvalidInputFragment();
                                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                                break;
                            }
                        }

                    }
                    if (switchFlag2) {
                        selectedFragment = new InvalidInputFragment();
                        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        break;
                    }
                }
            }


            case R.id.email_sign_up_button:{
                if (switchFlag) {
                    selectedFragment = new AccountFragment();
                    fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                break;
            }

        }
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
