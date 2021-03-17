package com.maksat.uni.fragments.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.models.ParticipantsByAlphabet;

import java.util.List;

public class ContactsSearch extends BaseFragment {

    public ContactsSearch(MainActivity mainActivity, List<ParticipantsByAlphabet> alphabets) {
        this.mainActivity=mainActivity;
        this.alphabets=alphabets;
    }
    private MainActivity mainActivity;
    private List<ParticipantsByAlphabet> alphabets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_statistic_search, container,false);

        findIDs(view);

        return view;
    }

    private void findIDs(@NonNull View view) {

    }
}
