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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    Integer item1;
    Integer item2;
    Integer item3;
    Integer item4;
    Integer item5;
    Integer item6;
    Integer item7;
    //TextView mealCodev = (TextView)v.findViewById(R.id.mealCode);
    String mealCodeStr = "";
    public OtherFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_ingred_other, container, false);

        Button button = (Button)v.findViewById(R.id.other_add_to_cart);
        button.setOnClickListener(this);

        item1 = 0;
        item2 = 0;
        item3 = 0;
        item4 = 0;
        item5 = 0;
        item6 = 0;
        item7 = 0;

        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.other_checkBox1);
        final CheckBox checkBox2 = (CheckBox) v.findViewById(R.id.other_checkBox2);
        final CheckBox checkBox3 = (CheckBox) v.findViewById(R.id.other_checkBox3);
        final CheckBox checkBox4 = (CheckBox) v.findViewById(R.id.other_checkBox4);
        final CheckBox checkBox5 = (CheckBox) v.findViewById(R.id.other_checkBox5);
        final CheckBox checkBox6 = (CheckBox) v.findViewById(R.id.other_checkBox6);
        final CheckBox checkBox7 = (CheckBox) v.findViewById(R.id.other_checkBox7);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    // add peppers to item
                    item1 = 1;
                }
                else{
                    item1 = 0;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    // add onions to item
                    item2 = 1;
                }
                else{
                    item2 = 0;
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    // add ingredient to item
                    item3 = 1;
                }
                else{
                    item3 = 0;
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox4.isChecked()) {
                    // add ingredient to item
                    item4 = 1;
                }
                else{
                    item4 = 0;
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox5.isChecked()) {
                    // add ingredient to item
                    item5 = 1;
                }
                else{
                    item5 = 0;
                }
            }
        });

        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox6.isChecked()) {
                    // add ingredient to item
                    item6 = 1;
                }
                else{
                    item6 = 0;
                }
            }
        });

        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox7.isChecked()) {
                    // add ingredient to item
                    item7 = 1;
                }
                else{
                    item7 = 0;
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        pushOtherToSharedPref();
        switch(v.getId()){
            case R.id.other_add_to_cart:
                // Add item to cart

                selectedFragment = new CartFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;

            case R.id.other_add_to_faves:
                // Add item to favorites

                break;

        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public void pushOtherToSharedPref(){
        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();
        editor.putInt("item1", item1);
        editor.commit();
        editor.putInt("item2", item2);
        editor.commit();
        editor.putInt("item3", item3);
        editor.commit();
        editor.putInt("item4", item4);
        editor.commit();
        editor.putInt("item5", item5);
        editor.commit();
        editor.putInt("item6", item6);
        editor.commit();
        editor.putInt("item7", item7);
        editor.commit();
    }
}
