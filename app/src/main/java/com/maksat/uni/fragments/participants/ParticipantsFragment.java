package com.maksat.uni.fragments.participants;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.AlphabetRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.statistic.StatisticSearch;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Employee;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsByAlphabet;
import com.maksat.uni.models.ParticipantsFilterBody;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.Zone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantsFragment extends BaseFragment {

    public ParticipantsFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    private MainActivity mainActivity;
    private RecyclerView alphabet_rv;
    public List<ParticipantsModel.item> items;
    private List<ParticipantsByAlphabet> alphabets;
    public ProgressBar progressBar;
    private ParticipantsFragment participantsFragment;
    private MaterialButton filter_btn;
    private AlphabetRVAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private Integer pageNumber=1, downloadedItems=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_participants, container, false);
        participantsFragment=this;
        findIDs(view);
        doCalls();
        return view;
    }

    private void findIDs(@NonNull View view) {
        alphabet_rv=view.findViewById(R.id.alphabet_rv);
        progressBar=view.findViewById(R.id.progressBar2);
        swipeRefreshLayout=view.findViewById(R.id.participants_srefrl);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                doCalls();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PAUSED", "onPause: Participants fragment");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            doCalls();
        }
    }

    private void doCalls() {
        if (Variables.currentEvent.getId()!=null) {
            Event participantsApi = Server.GetServerWithToken(Event.class, Variables.token);
            ParticipantsFilterBody participantsFilterBody=new ParticipantsFilterBody(0, null, null, null, new ParticipantsFilterBody.sorting("lastNameRus", true),
                    "", "", null, "", "", "", "", null);
            Call<ParticipantsModel> call = participantsApi.getParicipantsWFilter(Variables.currentEvent.getId(), 1, 1000000, participantsFilterBody);
            call.enqueue(new Callback<ParticipantsModel>() {
                @Override
                public void onResponse(Call<ParticipantsModel> call, Response<ParticipantsModel> response) {
                    if (response.body() != null) {
                        items=new ArrayList<>();
                        items.addAll(response.body().getItems());
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
                        adapter=new AlphabetRVAdapter(mainActivity, alphabets, participantsFragment, null, null, items, 0);
                        alphabet_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        alphabet_rv.setAdapter(adapter);
                        adapter.setOnParticipantClickListener(new AlphabetRVAdapter.OnItemClickListener() {
                            @Override
                            public void onParticipantClick(ParticipantsModel.item item) {
                                progressBar.setVisibility(View.VISIBLE);
                                getZones(item);
                            }

                            @Override
                            public void onContactClick(Employee employee) {

                            }
                        });
                        mainActivity.search_btn.setOnClickListener(v -> {
                            /*if (mainActivity.fragmentManager.findFragmentByTag("three")!=null){
                                mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).commit();
                            }*/
                            if (mainActivity.fragmentManager.findFragmentByTag("statisticSearch")!=null){
                                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticSearch"))).commit();
                            }else {
                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticSearch(mainActivity, response.body(), alphabets, null, null, 2), "statisticSearch").commit();
                            }
                            mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
                        });

                    }else {
                        Toast.makeText(getContext(), "При загрузке участников произошла ошибка.", Toast.LENGTH_SHORT).show();
                        try {
                            if (response.errorBody() != null) {
                                Log.d("PARTICIP_RESPONSE_ERROR", ""+response.errorBody().string());
                            }else {
                                Log.d("PARTICIP_RESPONSE_ERROR", "ErrorBody is null.");

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<ParticipantsModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("SERVER_ERROR", ""+t.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });


        }
    }

    private void getZones(ParticipantsModel.item item) {
        Event zonesApi=Server.GetServerWithToken(Event.class, Variables.token);
        Call<List<Zone>> call=zonesApi.getZonesForParticipant(Variables.currentEvent.getId(), item.getId());
        call.enqueue(new Callback<List<Zone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Zone>> call, @NonNull Response<List<Zone>> response) {
                mainActivity.bottomAppBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        if (mainActivity.fragmentManager.findFragmentByTag("participantInfo")!=null) {
                            mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("participantInfo"))).commit();
                        }
                            mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, response.body()), "participantInfo").commit();
                    }
                }else {
                    if (mainActivity.fragmentManager.findFragmentByTag("participantInfo")!=null) {
                        mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("participantInfo"))).commit();
                    }
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
                if (mainActivity.fragmentManager.findFragmentByTag("participantInfo")!=null) {
                    mainActivity.fragmentManager.beginTransaction().remove(mainActivity.fragmentManager.findFragmentByTag("participantInfo")).commit();
                }
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, new ArrayList<>()), "participantInfo").commit();
                Log.d("ZONE_SERV_ERROR", ""+t.getMessage());
            }
        });
    }
}
