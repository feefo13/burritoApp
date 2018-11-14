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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment implements View.OnClickListener {
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";


    View rootView;
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        Button button = (Button)rootView.findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(this);

        Button button2 = (Button)rootView.findViewById(R.id.email_sign_up_button);
        button2.setOnClickListener(this);

        return rootView;
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

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        switch(v.getId()){
            case R.id.email_sign_in_button:
                /*
                verify credentials and proceed to log in
                verifyCredentials();
                public boolean verifyCredentials();
                 */



                SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefShared.edit();
                editor.putBoolean("userLoggedIn", true);
                editor.commit();

                if (isAccountViewBeforeLogIn()){
                    selectedFragment = new RewardsFragment();
                }
                else{
                    selectedFragment = new AccountInfoFragment();
                }

                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

            case R.id.email_sign_up_button:
                selectedFragment = new AccountFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
