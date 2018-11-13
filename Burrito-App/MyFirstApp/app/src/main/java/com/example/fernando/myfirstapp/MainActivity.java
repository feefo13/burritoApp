package com.example.fernando.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity {
    SharedPreferences prefShared;
    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences.Editor editor;

    public Boolean isLoggedIn(){
        prefShared = getApplicationContext().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        boolean boolLoginSetting = prefShared.getBoolean("userLoggedIn", false);
        if (boolLoginSetting == true) {
            /*
            already signed in so continue to rewards/account info
             */
            return true;

        }
        else{
            /*
            sign up or sign in
             */
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.menu_bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        SharedPreferences prefShared = this.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();
        editor.putBoolean("userLoggedIn", false);
        editor.commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch(menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_rewards:
                    if (isLoggedIn()){
                        selectedFragment = new RewardsFragment();
                        }
                    else{
                        selectedFragment = new LoginFragment();

                        prefShared = getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                        editor = prefShared.edit();
                        editor.putBoolean("viewBeforeLogin", true);
                        editor.commit();
                    }

                    break;
                case R.id.nav_account:
                    if (isLoggedIn()){
                        selectedFragment = new AccountInfoFragment();
                    }
                    else{
                        selectedFragment = new LoginFragment();

                        prefShared = getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
                        editor = prefShared.edit();
                        editor.putBoolean("viewBeforeLogin", false);
                        editor.commit();
                    }

                    break;
                case R.id.nav_cart:
                    selectedFragment = new CartFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        };
    };


}
