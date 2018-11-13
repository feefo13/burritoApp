package com.example.fernando.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CartFragment extends Fragment {

    View v;
    TextView mTextView;

    public static final String PREF_FILE_NAME = "userdetails";
    SharedPreferences.Editor editor;

    String mealCodeStr;
    SharedPreferences prefShared;


    public CartFragment() {
        // Required empty public constructor

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_cart, container, false);
        mTextView = (TextView) v.findViewById(R.id.mealCode);
        mealCodeStr = getMealCodeStr(mealCodeStr);
        mTextView.setText(mealCodeStr);

        return v;
    }



    public String getMealCodeStr(String string2){

        prefShared = getActivity().getApplicationContext().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        
        Integer meat = prefShared.getInt("meat", 5);
        Integer tortilla = prefShared.getInt("tortilla", 4);
        Integer rice = prefShared.getInt("rice", 3);
        Integer bean = prefShared.getInt("bean", 3);
        Integer cheese = prefShared.getInt("cheese", 3);
        Integer sauce = prefShared.getInt("sauce", 3);

        Integer meatp = prefShared.getInt("meatp", 1);
        Integer tortillap = prefShared.getInt("tortillap", 1);
        Integer ricep = prefShared.getInt("ricep", 1);
        Integer beanp = prefShared.getInt("beanp", 1);
        Integer cheesep = prefShared.getInt("cheesep", 1);
        Integer saucep = prefShared.getInt("saucep", 1);

        Integer item1 = prefShared.getInt("item1", 0);
        Integer item2 = prefShared.getInt("item2", 0);
        Integer item3 = prefShared.getInt("item3", 0);
        Integer item4 = prefShared.getInt("item4", 0);
        Integer item5 = prefShared.getInt("item5", 0);
        Integer item6 = prefShared.getInt("item6", 0);
        Integer item7 = prefShared.getInt("item7", 0);

        string2 = "" + meat.toString() +","+ tortilla.toString() +","+ rice.toString() +","+ bean.toString() +","+ cheese.toString() +","+ sauce.toString();
        string2 = string2 +","+ meatp.toString() +","+ tortillap.toString() +","+ ricep.toString() +","+ beanp.toString() +","+ cheesep.toString() +","+ saucep.toString();
        string2 = string2 +","+ item1.toString() +","+ item2.toString() +","+ item3.toString() +","+ item4.toString() +","+ item5.toString() +","+ item6.toString() +","+ item7.toString();

        return string2;
    }

}
