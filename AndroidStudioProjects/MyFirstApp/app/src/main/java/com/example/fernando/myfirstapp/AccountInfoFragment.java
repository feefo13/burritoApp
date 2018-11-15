package com.example.fernando.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AccountInfoFragment extends Fragment {
    View v;
    TextView email;
    TextView name;
    TextView phone;
    TextView address;
    TextView address2;

    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
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
