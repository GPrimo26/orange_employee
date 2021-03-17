package com.maksat.uni.fragments.participants.infotabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.DaysRVAdapter;
import com.maksat.uni.adapters.ProgramRVAdapter;
import com.maksat.uni.customUI.CenterLayoutManager;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.participants.ParticipantInfo;
import com.maksat.uni.fragments.program.ProgramInfoFragment;
import com.maksat.uni.interfaces.Profile;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.EventProgramByDays;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Schedule extends BaseFragment {
    public Schedule(MainActivity mainActivity, ParticipantsModel.item item, ParticipantInfo participantInfo) {
        this.mainActivity=mainActivity;
        this.item=item;
        this.participantInfo=participantInfo;
    }



    private MainActivity mainActivity;
    private ParticipantsModel.item item;
    private RecyclerView days_rv, program_rv;
    private List<EventProgram> eventPrograms;
    private DaysRVAdapter dayAdapter;
    private ProgramRVAdapter programAdapter;
    private ParticipantInfo participantInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule, container, false);
        findIDs(view);
        doCalls();
        return view;
    }

    private void findIDs(View view) {
        days_rv=view.findViewById(R.id.days_rv);
        program_rv=view.findViewById(R.id.program_rv);

    }


    private void doCalls(){
        Profile apiProgram= Server.GetServerWithToken(Profile.class, Variables.token);
        Call<List<EventProgram>> call=apiProgram.getProgram(Variables.currentEvent.getId(), item.getId(), null, null, null, null, null);
        call.enqueue(new Callback<List<EventProgram>>() {
            @Override
            public void onResponse(Call<List<EventProgram>> call, Response<List<EventProgram>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        eventPrograms=response.body();
                        if (eventPrograms.size() != 0) {
                            List<Date> dates = new ArrayList<>();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
                            for (int i = 0; i < eventPrograms.size(); i++) {
                                String dateStr = eventPrograms.get(i).getDateTimeStart();
                                try {
                                    Date date = format.parse(dateStr);
                                    dates.add(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            Collections.sort(dates);
                            List<String> datesList = new ArrayList<>();
                            format.applyPattern("yyyy-MM-dd");
                            for (int i = 0; i < eventPrograms.size(); i++) {
                                String dateStr;
                                if (dates.get(i) != null) {
                                    dateStr = format.format(dates.get(i));
                                    int flag = 0;
                                    for (int j = 0; j < datesList.size(); j++) {
                                        if (dateStr.equals(datesList.get(j))) {
                                            flag = 1;
                                            break;
                                        }
                                    }
                                    if (flag == 0) {
                                        datesList.add(dateStr);
                                    }
                                }
                            }
                            List<EventProgramByDays> programByDays = new ArrayList<>();
                            for (int i = 0; i < datesList.size(); i++) {
                                List<EventProgram> tempEvents = new ArrayList<>();
                                for (int j = 0; j < eventPrograms.size(); j++) {
                                    String dateStr = eventPrograms.get(j).getDateTimeStart();
                                    format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
                                    try {
                                        Date date = format.parse(dateStr);
                                        format.applyPattern("yyyy-MM-dd");
                                        if (date != null) {
                                            dateStr = format.format(date);
                                            if (dateStr.equals(datesList.get(i))) {
                                                tempEvents.add(eventPrograms.get(j));
                                            }
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                programByDays.add(new EventProgramByDays(datesList.get(i), tempEvents));
                            }
                            CenterLayoutManager centerLayoutManager = new CenterLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                            dayAdapter = new DaysRVAdapter(mainActivity, programByDays);
                            days_rv.setLayoutManager(centerLayoutManager);
                            days_rv.setAdapter(dayAdapter);
                            dayAdapter.setOnItemClickListener(position -> {
                                days_rv.smoothScrollToPosition(position);
                                if (programByDays.size() != 0)
                                    if (programAdapter == null) {
                                        programAdapter = new ProgramRVAdapter(mainActivity, programByDays.get(position).getEventProgram(), 1);
                                        program_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                        program_rv.setAdapter(programAdapter);
                                        programAdapter.setOnItemClickListener(program -> {
                                            if (mainActivity.fragmentManager.findFragmentByTag("programInfo") != null) {
                                                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programInfo"))).commit();
                                            } else {
                                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramInfoFragment(mainActivity, program, 1), "programInfo").commit();
                                            }
                                            //mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("four"))).commit();
                                            mainActivity.bottomAppBar.setVisibility(View.GONE);
                                        });
                                    }else {
                                        programAdapter.eventProgram = programByDays.get(position).getEventProgram();
                                        programAdapter.notifyDataSetChanged();
                                    }
                                dayAdapter.notifyDataSetChanged();
                            });
                            dayAdapter.performClick(0);
                        }
                    }
                }else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            Log.d("PPROGRAM_RESP_ERROR", ""+response.errorBody().string());
                        }else {
                            Log.d("PPROGRAM_RESP_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EventProgram>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("PPROGRAM_SERV_ERROR", ""+t.getMessage());
            }
        });
    }
}
