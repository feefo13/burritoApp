package com.example.fernando.myfirstapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;

    public MenuFragment() {
        // Required empty public constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_menu, container, false);

        TextView burritoTextView = (TextView) v.findViewById(R.id.menu_item1);
        burritoTextView.setOnClickListener(this);

        TextView tacoTextView = (TextView) v.findViewById(R.id.menu_item2);
        tacoTextView.setOnClickListener(this);

        TextView kidsTextView = (TextView) v.findViewById(R.id.menu_item3);
        kidsTextView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        switch(v.getId()){

            case R.id.menu_item1:
                //Burrito
                break;

            case R.id.menu_item2:
                //Taco
                break;

            case R.id.menu_item3:
                //Kids

                break;
        }
        selectedFragment = new MeatFragment();
        fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}
