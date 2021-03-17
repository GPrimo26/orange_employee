package com.maksat.uni.fragments.program;

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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.DaysRVAdapter;
import com.maksat.uni.adapters.ProgramRVAdapter;
import com.maksat.uni.customUI.CenterLayoutManager;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsEventProgramFilterBody;
import com.maksat.uni.models.ParticipantsFilterBody;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramFragment extends BaseFragment {

    public ProgramFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }


    private MainActivity mainActivity;
    private MaterialButton info_btn;
    private RecyclerView days_rv, program_rv;
    private DaysRVAdapter dayAdapter;
    private ProgramRVAdapter programAdapter;
    private ProgressBar progressBar;
    private ProgramFragment programFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_program, container, false);
        programFragment=this;
        findIDs(view);
        setInfo();
        return view;
    }

    private void findIDs(View view) {
        info_btn=view.findViewById(R.id.program_info_btn);
        days_rv=view.findViewById(R.id.days_rv);
        program_rv=view.findViewById(R.id.program_rv);
        progressBar=view.findViewById(R.id.progressBar7);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            setInfo();
        }
    }

    private void setInfo() {
        CenterLayoutManager centerLayoutManager = new CenterLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        dayAdapter = new DaysRVAdapter(mainActivity, Variables.programByDays);
        days_rv.setLayoutManager(centerLayoutManager);
        days_rv.setAdapter(dayAdapter);
        dayAdapter.setOnItemClickListener(position -> {
            days_rv.smoothScrollToPosition(position);
            if (Variables.programByDays.size() != 0)
                if (programAdapter == null) {
                    programAdapter = new ProgramRVAdapter(mainActivity, Variables.programByDays.get(position).getEventProgram(), 0);
                    program_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    program_rv.setAdapter(programAdapter);
                    programAdapter.setOnItemClickListener(program -> {
                        if (mainActivity.fragmentManager.findFragmentByTag("programInfo") != null) {
                            mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programInfo"))).commit();
                        } else {
                            mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramInfoFragment(mainActivity, program, 0), "programInfo").commit();
                        }
                        mainActivity.bottomAppBar.setVisibility(View.GONE);
                    });
                    programAdapter.setOnParticipantsClickListener(nameRus -> {
                        progressBar.setVisibility(View.VISIBLE);
                        Event apiProgram = Server.GetServerWithToken(Event.class, Variables.token);
                        ParticipantsEventProgramFilterBody participantsEventProgramFilterBody = new ParticipantsEventProgramFilterBody(null, null, new ParticipantsFilterBody.sorting("lastNameRus", true),
                                "", "", null, "", "", "", "", null);
                        Call<ParticipantsModel> call = apiProgram.getParticipantsWithEventProgram(Variables.currentEvent.getId(), 1, 1000000, participantsEventProgramFilterBody);
                        call.enqueue(new Callback<ParticipantsModel>() {
                            @Override
                            public void onResponse(@NonNull Call<ParticipantsModel> call, @NonNull Response<ParticipantsModel> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getItems().size() == 0) {
                                            Toast.makeText(getContext(), "Список пуст", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mainActivity.bottomAppBar.setVisibility(View.GONE);
                                            if (mainActivity.fragmentManager.findFragmentByTag("programParticipants") != null) {
                                                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programParticipants"))).commit();
                                            } else {
                                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramParticipantsFragment(mainActivity, response.body(), nameRus, programFragment, null), "programParticipants").commit();
                                            }
                                            mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("four"))).commit();
                                        }
                                    }
                                } else {
                                    try {
                                        if (response.errorBody() != null) {
                                            Gson gson = new Gson();
                                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                                            Log.d("NAME_RESP_ERROR", ""+errorBody.Ru);
                                        }else {
                                            Log.d("NAME_RESP_ERROR", "ErrorBody is null.");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(@NonNull Call<ParticipantsModel> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                                Log.d("PARTICS_SERV_ERROR", "" + t.getMessage());
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    });
                } else {
                    programAdapter.eventProgram = Variables.programByDays.get(position).getEventProgram();
                    programAdapter.notifyDataSetChanged();
                }
            dayAdapter.notifyDataSetChanged();
        });

        dayAdapter.performClick(0);
        info_btn.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mainActivity);
            bottomSheetDialog.setContentView(R.layout.lo_status_info);
            bottomSheetDialog.show();
        });

        mainActivity.search_btn.setOnClickListener(v -> {
            if (mainActivity.fragmentManager.findFragmentByTag("programSearch") != null) {
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programSearch"))).commit();
            } else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramSearch(mainActivity, programFragment), "programSearch").commit();
            }
            mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("four"))).commit();
            mainActivity.bottomAppBar.setVisibility(View.GONE);
        });
    }
}
