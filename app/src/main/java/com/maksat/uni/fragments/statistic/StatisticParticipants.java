package com.maksat.uni.fragments.statistic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.AlphabetRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.participants.ParticipantInfo;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Dashboard;
import com.maksat.uni.models.Employee;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsByAlphabet;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.Zone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticParticipants extends BaseFragment {

    public StatisticParticipants(String from, MainActivity mainActivity, List<Dashboard.categories> finalCategories1, ParticipantsModel body, int whole, StatisticFragment statisticFragment, int position) {
        this.from=from;
        this.mainActivity=mainActivity;
        this.categories=finalCategories1;
        this.participantsModel=body;
        this.whole=whole;
        this.statisticFragment=statisticFragment;
        this.position=position;
    }
    private StatisticFragment statisticFragment;
    private String from;
    private int whole, position;
    private MainActivity mainActivity;
    private List<Dashboard.categories> categories;
    private ParticipantsModel participantsModel;
    private List<ParticipantsModel.item> items;
    private List<ParticipantsByAlphabet> alphabets;
    private TextView title_tv;
    private MaterialButton back_btn;
    private RecyclerView alphabet_rv;
    private StatisticParticipants statisticParticipants;
    public ProgressBar progressBar;
    private FloatingActionButton filter_fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants_fromstatistic, container, false);
        statisticParticipants=this;
        findIDs(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInfo();
    }

    private void findIDs(@NonNull View view) {
        title_tv=view.findViewById(R.id.title_tv);
        back_btn=view.findViewById(R.id.back_btn);
        alphabet_rv=view.findViewById(R.id.alphabet_rv);
        progressBar=view.findViewById(R.id.progressBar4);
        filter_fab=view.findViewById(R.id.search_fab);
    }

    private void setInfo() {
        String text=from+" "+whole;
        title_tv.setText(text);
        back_btn.setOnClickListener(v -> {
            Fragment fragment=mainActivity.fragmentManager.findFragmentByTag("statisticParticipants");
            if (fragment != null) {
                mainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
            }
            mainActivity.ChangeScreen("two");

        });
        items=new ArrayList<>();
        items.addAll(participantsModel.getItems());
        alphabets=new ArrayList<>();
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
        AlphabetRVAdapter adapter=new AlphabetRVAdapter(mainActivity, alphabets, null, statisticParticipants, null, participantsModel.getItems(), 0);
        alphabet_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        alphabet_rv.setAdapter(adapter);
        adapter.setOnParticipantClickListener(new AlphabetRVAdapter.OnItemClickListener() {
            @Override
            public void onParticipantClick(ParticipantsModel.item item) {
                /*switch (Variables.fragment){
                    case "two":
                        mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticParticipants"))).commit();
                        break;
                    case "three":
                        mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).commit();
                        break;
                }*/
               /* progressBar.setVisibility(View.VISIBLE);
                getZones(item);*/
            }

            @Override
            public void onContactClick(Employee employee) {

            }
        });
        filter_fab.setOnClickListener(v -> {
            if (mainActivity.fragmentManager.findFragmentByTag("statisticParticipants")!=null){
                mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticParticipants"))).commit();
            }
            if (mainActivity.fragmentManager.findFragmentByTag("statisticSearch")!=null){
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticSearch"))).commit();
            }else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticSearch(mainActivity, participantsModel, alphabets, statisticParticipants, position, 0), "statisticSearch").commit();
            }
        });
    }

    private void getZones(ParticipantsModel.item item) {
        Event zonesApi= Server.GetServerWithToken(Event.class, Variables.token);
        Call<List<Zone>> call=zonesApi.getZonesForParticipant(Variables.currentEvent.getId(), item.getId());
        call.enqueue(new Callback<List<Zone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Zone>> call, @NonNull Response<List<Zone>> response) {
                mainActivity.bottomAppBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, response.body()), "participantInfo").commit();
                    }
                }else {
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, new ArrayList<>()), "participantInfo").commit();
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            Log.d("ZONE_RESP_ERROR", ""+errorBody.Ru);
                        }else {
                            Log.d("ZONE_RESP_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Zone>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("ZONE_SERV_ERROR", ""+t.getMessage());
            }
        });
    }
}
