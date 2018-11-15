package com.example.fernando.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;


public class AccountInfoFragment extends Fragment {
    private static final String FILENAME = "users.json";
    private static final Integer init_memberID = 116435;
    Integer nextAvailableMemberID;

    View v;
    private FragmentActivity myContext;

    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences.Editor editor;
    List<User> userList;

    TextView email;
    TextView name;
    TextView phone;
    TextView address;
    TextView address2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_info, container, false) ;

        email = (TextView) v.findViewById(R.id.userEmail);
        name = (TextView) v.findViewById(R.id.userName);
        phone = (TextView) v.findViewById(R.id.userPhone);
        address = (TextView) v.findViewById(R.id.userAddress);
        address2 = (TextView) v.findViewById(R.id.userAddress2);


        SharedPreferences prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        String email_str = prefShared.getString("email", "N/A");
        String name_str = prefShared.getString("name", "N/A");
        String phone_str = prefShared.getString("phone", "N/A");
        String address_str = prefShared.getString("address", "N/A");


        String[] address_list = address_str.split(",");
        address_str = address_list[0] + ",";
        String address_str2 = address_list[1] + ',' + address_list[2];

        phone.setText(phone_str);
        address.setText(address_str);
        address2.setText(address_str2);
        email.setText(email_str);
        name.setText(name_str);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
