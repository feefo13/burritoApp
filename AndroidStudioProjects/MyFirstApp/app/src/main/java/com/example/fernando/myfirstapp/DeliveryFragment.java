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
public class DeliveryFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";

    public DeliveryFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_deliverymethod, container, false);

        TextView carryTextView = (TextView) v.findViewById(R.id.delivery_item1);
        carryTextView.setOnClickListener(this);

        TextView deliverTextView = (TextView) v.findViewById(R.id.delivery_item2);
        deliverTextView.setOnClickListener(this);

        TextView dineinTextView = (TextView) v.findViewById(R.id.delivery_item3);
        dineinTextView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();

        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();


        switch(v.getId()){

            case R.id.delivery_item1:
                //carry out
                editor.putString("deliveryMethod", "Carry Out");
                editor.commit();
                break;

            case R.id.delivery_item2:
                //delivery
                editor.putString("deliveryMethod", "Delivery");
                editor.commit();
                break;

            case R.id.delivery_item3:
                //dine-in
                editor.putString("deliveryMethod", "Dine-in");
                editor.commit();
                break;
        }
        selectedFragment = new MenuFragment();
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
