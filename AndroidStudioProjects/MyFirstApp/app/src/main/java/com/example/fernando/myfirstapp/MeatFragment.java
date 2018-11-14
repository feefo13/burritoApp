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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeatFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";
    Integer portionChange;


    public MeatFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_ingred_meat, container, false);

        TextView chickenTextView = (TextView) v.findViewById(R.id.meat_item1);
        chickenTextView.setOnClickListener(this);

        TextView steakTextView = (TextView) v.findViewById(R.id.meat_item2);
        steakTextView.setOnClickListener(this);

        TextView hamTextView = (TextView) v.findViewById(R.id.meat_item3);
        hamTextView.setOnClickListener(this);

        TextView turkeyTextView = (TextView) v.findViewById(R.id.meat_item4);
        turkeyTextView.setOnClickListener(this);

        portionChange =0;

        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.meat_checkBox1);
        final CheckBox checkBox2 = (CheckBox) v.findViewById(R.id.meat_checkBox2);
        final CheckBox checkBox3 = (CheckBox) v.findViewById(R.id.meat_checkBox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    portionChange =1;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox3.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    portionChange =2;
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();

        switch(v.getId()){
            case R.id.meat_item1:
                //Chicken

                editor.putInt("meat", 1);
                editor.commit();
                break;

            case R.id.meat_item2:
                //Steak

                editor.putInt("meat", 2);
                editor.commit();
                break;

            case R.id.meat_item3:
                //Ham

                editor.putInt("meat", 3);
                editor.commit();
                break;

            case R.id.meat_item4:
                //Ham

                editor.putInt("meat", 4);
                editor.commit();
                break;

            case R.id.meat_item5:
                //None

                editor.putInt("meat", 0);
                editor.commit();
                break;
        }
        editor.putInt("meatp", portionChange);
        editor.commit();
        selectedFragment = new TortillaFragment();
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
