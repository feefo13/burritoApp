package com.example.fernando.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RewardsFragment extends Fragment {
    View v;

    TextView memberID;
    TextView rewardPTS;

    private FragmentActivity myContext;
    public static final String PREF_FILE_NAME = "userdetails";

    public RewardsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rewards, container, false);

        memberID = (TextView)  v.findViewById(R.id.userID);
        rewardPTS = (TextView)  v.findViewById(R.id.userPoints);

        SharedPreferences prefShared = getActivity().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        Integer rewardPTS_int = prefShared.getInt("rewardPoints", 0);
        Integer memberID_int = prefShared.getInt("memberID", 0);

        String rewardPTS_str = rewardPTS_int.toString();
        String memberID_str = memberID_int.toString();

        memberID.setText(memberID_str);
        rewardPTS.setText(rewardPTS_str);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
