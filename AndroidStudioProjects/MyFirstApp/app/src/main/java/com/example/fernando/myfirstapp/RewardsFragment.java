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
import android.widget.TextView;

public class RewardsFragment extends Fragment implements View.OnClickListener{
    View v;

    TextView memberID;
    TextView rewardPTS;
    TextView sinceDate;

    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";

    public RewardsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rewards, container, false);

        Button chipsButton = (Button) v.findViewById(R.id.button_redeem);
        chipsButton.setOnClickListener(this);

        memberID = (TextView)  v.findViewById(R.id.userID);
        rewardPTS = (TextView)  v.findViewById(R.id.userPoints);
        sinceDate = (TextView)  v.findViewById(R.id.sinceDate);

        SharedPreferences prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        Integer rewardPTS_int = prefShared.getInt("rewardPoints", 0);
        Integer memberID_int = prefShared.getInt("memberID", 0);
        String sinceDate_str = prefShared.getString("date", "N/A");

        String rewardPTS_str = rewardPTS_int.toString();
        String memberID_str = memberID_int.toString();

        memberID.setText(memberID_str);
        rewardPTS.setText(rewardPTS_str);
        sinceDate.setText(sinceDate_str);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        Fragment selectedFragment = null;
        FragmentManager fragManager = myContext.getSupportFragmentManager();


        switch (v.getId()) {

            case R.id.button_redeem:

                selectedFragment = new RedeemFragment();
                fragManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;

        }

    }
}
