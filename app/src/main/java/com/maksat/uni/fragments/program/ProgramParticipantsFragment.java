package com.maksat.uni.fragments.program;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.adapters.AlphabetRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.statistic.StatisticSearch;
import com.maksat.uni.models.ParticipantsByAlphabet;
import com.maksat.uni.models.ParticipantsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProgramParticipantsFragment extends BaseFragment {
    public ProgramParticipantsFragment(MainActivity mainActivity, ParticipantsModel body, String nameRus, ProgramFragment programFragment, ProgramInfoFragment programInfoFragment) {
        this.mainActivity=mainActivity;
        this.body=body;
        this.nameRus=nameRus;
        this.programFragment=programFragment;
        this.programInfoFragment=programInfoFragment;
    }
    private MainActivity mainActivity;
    private ParticipantsModel body;
    private TextView title_tv;
    private MaterialButton back_btn;
    private RecyclerView alphabets_rv;
    private String nameRus;
    private List<ParticipantsModel.item> items;
    private List<ParticipantsByAlphabet> alphabets;
    private ProgramFragment programFragment;
    private ProgramInfoFragment programInfoFragment;
    private ProgressBar progressBar;
    private FloatingActionButton search_fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_participants_status, container, false);
        findIDs(view);
        search_fab.setVisibility(View.GONE);
        return view;
    }

    private void findIDs(View view) {
        title_tv=view.findViewById(R.id.title_tv2);
        back_btn=view.findViewById(R.id.program_participants_back_btn);
        alphabets_rv=view.findViewById(R.id.participants_rv);
        progressBar=view.findViewById(R.id.progressBar8);
        search_fab=view.findViewById(R.id.search_fab);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(this::setInfo, 500);
    }

    private void setInfo() {
        title_tv.setText(nameRus);
        items=new ArrayList<>();
        alphabets=new ArrayList<>();
        items.addAll(body.getItems());
        for(char letter = 'А'; letter<='Я'; letter++){
            List<ParticipantsModel.item> tempItems=new ArrayList<>();
            for (int i=0; i<items.size(); i++){
                if (items.get(i).getLastNameRus().substring(0, 1).equals(String.valueOf(letter))){
                    tempItems.add(items.get(i));
                }
            }
            if (tempItems.size()!=0) {
                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
            }
        }
        for(char letter = 'а'; letter<='я'; letter++){
            List<ParticipantsModel.item> tempItems=new ArrayList<>();
            for (int i=0; i<items.size(); i++){
                if (items.get(i).getLastNameRus().charAt(0)==letter){
                    tempItems.add(items.get(i));
                }
            }
            if (tempItems.size()!=0) {
                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
            }                        }
        for(char letter = 'A'; letter<='Z'; letter++){
            List<ParticipantsModel.item> tempItems=new ArrayList<>();
            for (int i=0; i<items.size(); i++){
                if (items.get(i).getLastNameRus().charAt(0)==letter){
                    tempItems.add(items.get(i));
                }
            }
            if (tempItems.size()!=0) {
                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
            }                        }
        for(char letter = 'a'; letter<='z'; letter++){
            List<ParticipantsModel.item> tempItems=new ArrayList<>();
            for (int i=0; i<items.size(); i++){
                if (items.get(i).getLastNameRus().charAt(0)==letter){
                    tempItems.add(items.get(i));
                }
            }
            if (tempItems.size()!=0) {
                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
            }                        }
        for(char letter = '0'; letter<='9'; letter++){
            List<ParticipantsModel.item> tempItems=new ArrayList<>();
            for (int i=0; i<items.size(); i++){
                if (items.get(i).getLastNameRus().charAt(0)==letter){
                    tempItems.add(items.get(i));
                }
            }
            if (tempItems.size()!=0) {
                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
            }
        }
        AlphabetRVAdapter adapter=new AlphabetRVAdapter(mainActivity, alphabets, null, null, null, items, 1);
        alphabets_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        alphabets_rv.setAdapter(adapter);
        back_btn.setOnClickListener(v -> {
            Fragment showFragment;
            if (programFragment==null){
                showFragment=programInfoFragment;
            }else {
                mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
                showFragment=programFragment;
            }
            Fragment fragment = mainActivity.fragmentManager.findFragmentByTag("programParticipants");
            if (fragment != null) {
                mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                mainActivity.fragmentManager.beginTransaction().show(showFragment).commit();
                mainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
            }else {
                mainActivity.fragmentManager.beginTransaction().show(showFragment).commit();
            }
        });
        progressBar.setVisibility(View.GONE);
        search_fab.setVisibility(View.VISIBLE);
        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.fragmentManager.findFragmentByTag("programParticipants")!=null){
                    mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programParticipants"))).commit();
                }
                if (mainActivity.fragmentManager.findFragmentByTag("statisticSearch")!=null){
                    mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticSearch"))).commit();
                }else {
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticSearch(mainActivity, body, alphabets, null, null, 1), "statisticSearch").commit();
                }
            }
        });
    }
}
