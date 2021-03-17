package com.maksat.uni.fragments.statistic;

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

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.StatisticRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.home.HomeFragment;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticFragment extends BaseFragment {
    public StatisticFragment(MainActivity mainActivity) {
       this.mainActivity=mainActivity;
    }

    private MainActivity mainActivity;
    public ProgressBar progressBar;
    private RecyclerView recyclerView;
    private HomeFragment homeFragment;
    private StatisticFragment statisticFragment;
    private Dashboard dashboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_statistic, container, false);
        homeFragment=(HomeFragment) (mainActivity.getSupportFragmentManager().findFragmentByTag("one"));
        statisticFragment=this;
        findIDs(view);
        doCalls();
        return view;
    }

    private void findIDs(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.statistic_rv);
    }


    private void doCalls() {
        if (homeFragment!=null) {
            Event dashboardApi = Server.GetServerWithToken(Event.class, Variables.token);
            Call<Dashboard> call = dashboardApi.getDashboard(homeFragment.events.getCurrEventId());
            call.enqueue(new Callback<Dashboard>() {
                @Override
                public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            dashboard=response.body();
                            List<Integer> info=new ArrayList<>();
                            int whole=dashboard.getArrive()+dashboard.getDeparture()+dashboard.getDraft()+dashboard.getApproved()/*+dashboard.getCheckIn()+dashboard.getCheckOut()*/;
                            info.add(whole);//всего
                            info.add(dashboard.getDraft());//на рассмотрении
                            info.add(dashboard.getApproved());
                            /*info.add(dashboard.getArrive());
                            info.add(dashboard.getDeparture());
                            info.add(dashboard.getCheckIn());
                            info.add(dashboard.getCheckOut());*/
                            StatisticRVAdapter adapter=new StatisticRVAdapter(mainActivity, info, dashboard, statisticFragment);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    }else {
                        Toast.makeText(getContext(), "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                        try {
                            if (response.errorBody() != null) {
                                Log.d("DASHB_RESP_ERROR", ""+response.errorBody().string());
                            }else {
                                Log.d("DASHB_RESP_ERROR", "ErrorBody is null.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Dashboard> call, Throwable t) {
                    Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("DASHB_SERV_ERROR", ""+t.getMessage());
                }
            });
        }
    }
}
