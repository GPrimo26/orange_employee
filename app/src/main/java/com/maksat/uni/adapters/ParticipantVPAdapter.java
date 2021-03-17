package com.maksat.uni.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ParticipantVPAdapter extends FragmentPagerAdapter  {
    private ArrayList<Fragment> arrayFragments;
    private ArrayList<String> arrayTitles;

    public ParticipantVPAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> arrayFragments, ArrayList<String> arrayTitles) {
        super(fm);
        this.arrayFragments=arrayFragments;
        this.arrayTitles=arrayTitles;

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayFragments.get(position);
    }

    @Override
    public int getCount() {
        return arrayFragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayTitles.get(position);
    }
}
