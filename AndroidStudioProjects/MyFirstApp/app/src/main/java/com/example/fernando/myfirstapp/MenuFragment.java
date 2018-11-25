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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{
    View v;
    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";

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

        TextView sidesTextView = (TextView) v.findViewById(R.id.menu_item4);
        sidesTextView.setOnClickListener(this);

        TextView drinksTextView = (TextView) v.findViewById(R.id.menu_item5);
        drinksTextView.setOnClickListener(this);

        TextView dessertsTextView = (TextView) v.findViewById(R.id.menu_item6);
        dessertsTextView.setOnClickListener(this);

        TextView itemsTextView = (TextView) v.findViewById(R.id.menu_item7);
        itemsTextView.setOnClickListener(this);

        TextView ordersTextView = (TextView) v.findViewById(R.id.menu_item8);
        ordersTextView.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        SharedPreferences prefShared = this.getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefShared.edit();

        switch(v.getId()){

            case R.id.menu_item1:
                //Burrito

                editor.putString("itemType", "Burrito");
                editor.commit();
                selectedFragment = new MeatFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;

            case R.id.menu_item2:
                //Taco

                editor.putString("itemType", "Taco");
                editor.commit();
                selectedFragment = new MeatFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

            case R.id.menu_item3:
                //Kids

                editor.putString("itemType", "Kids");
                editor.commit();
                selectedFragment = new MeatFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

                // open frag to menu for sides/drinks/desert, set itemType from there.

            case R.id.menu_item4:
                //Sides
                selectedFragment = new SidesFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

            case R.id.menu_item5:
                //Drinks
                selectedFragment = new DrinksFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;
            case R.id.menu_item6:
                //Desserts
                selectedFragment = new DessertsFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

            case R.id.menu_item7:
                //Fave items
                selectedFragment = new FaveItemsFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                break;

            case R.id.menu_item8:
                //Fave orders
                selectedFragment = new FaveOrdersFragment();
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
