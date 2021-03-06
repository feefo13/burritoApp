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
public class CheeseFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;

    public static final String PREF_FILE_NAME = "userdetails";
    Integer portionChange;

    public CheeseFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_ingred_cheese, container, false);

        TextView pintoTextView = (TextView) v.findViewById(R.id.cheese_item1);
        pintoTextView.setOnClickListener(this);

        TextView blackTextView = (TextView) v.findViewById(R.id.cheese_item2);
        blackTextView.setOnClickListener(this);

        portionChange = 0;

        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.cheese_checkBox1);
        final CheckBox checkBox2 = (CheckBox) v.findViewById(R.id.cheese_checkBox2);
        final CheckBox checkBox3 = (CheckBox) v.findViewById(R.id.cheese_checkBox3);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    portionChange = 1;
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
                    portionChange = 2;
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

            case R.id.cheese_item1:
                //mozzarella

                editor.putInt("cheese", 1);
                editor.commit();
                break;

            case R.id.cheese_item2:
                //cheddar

                editor.putInt("cheese", 2);
                editor.commit();
                break;

            case R.id.cheese_item5:
                //None

                editor.putInt("cheese", 0);
                editor.commit();
                break;
        }
        editor.putInt("cheesep", portionChange);
        editor.commit();
        selectedFragment = new SauceFragment();
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
