package com.maksat.uni.fragments.program;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.DaysRVAdapter;
import com.maksat.uni.adapters.ProgramRVAdapter;
import com.maksat.uni.bottomsheets.ProgramFilter;
import com.maksat.uni.customUI.CenterLayoutManager;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Category;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.EventProgramByDays;
import com.maksat.uni.models.ProgramFilterBody;
import com.maksat.uni.models.Sport;

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

public class ProgramSearch extends BaseFragment {
    public ProgramSearch(MainActivity mainActivity, ProgramFragment programFragment) {
        this.mainActivity = mainActivity;
    }

    private final MainActivity mainActivity;
    private SearchView searchView;
    private MaterialButton back_btn, filter_btn;
    private RecyclerView days_rv, program_rv;
    private DaysRVAdapter daySearchAdapter;
    private ProgramRVAdapter programSearchAdapter;
    private ProgressBar progressBar;
    private Window window;
    private final List<EventProgramByDays> programByDays = new ArrayList<>(Variables.programByDays);
    private ProgramFilterBody programFilterBody;
    private ChipGroup chipGroup;
    private Call<List<EventProgram>> eventProgramCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program_search, container, false);

        window = mainActivity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(mainActivity, R.color.filterStatusBarColor));
        mainActivity.bottomAppBar.setVisibility(View.GONE);

        programFilterBody = new ProgramFilterBody(null, null, "", "", null, null, "");

        findIDs(view);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setHintTextColor(ContextCompat.getColor(mainActivity, R.color.filterTextColor));
        textView.setTextColor(Color.WHITE);

        setInfo();
        return view;
    }

    private void findIDs(@NonNull View view) {
        searchView = view.findViewById(R.id.searchView);
        back_btn = view.findViewById(R.id.search_back_btn);
        filter_btn = view.findViewById(R.id.program_filter_btn);
        days_rv = view.findViewById(R.id.search_days_rv);
        program_rv = view.findViewById(R.id.search_program_rv);
        progressBar = view.findViewById(R.id.search_progressBar);
        chipGroup = view.findViewById(R.id.chips);
    }

    private void setInfo() {
        CenterLayoutManager centerLayoutManager = new CenterLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        daySearchAdapter = new DaysRVAdapter(mainActivity, programByDays);
        days_rv.setLayoutManager(centerLayoutManager);
        days_rv.setAdapter(daySearchAdapter);
        daySearchAdapter.setOnItemClickListener(position -> {
            days_rv.smoothScrollToPosition(position);
            if (programSearchAdapter == null) {
                programSearchAdapter = new ProgramRVAdapter(mainActivity, Variables.programByDays.get(position).getEventProgram(), 0);
                program_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                program_rv.setAdapter(programSearchAdapter);
                programSearchAdapter.setOnItemClickListener(program -> {
                });
                programSearchAdapter.setOnParticipantsClickListener(nameRus -> {
                });
            } else {
                programSearchAdapter.eventProgram = Variables.programByDays.get(position).getEventProgram();
                programSearchAdapter.notifyDataSetChanged();
            }
            daySearchAdapter.notifyDataSetChanged();
        });
        daySearchAdapter.performClick(0);

        back_btn.setOnClickListener(v -> {
            Fragment fragment = mainActivity.fragmentManager.findFragmentByTag("four");
            if (fragment != null) {
                window.setStatusBarColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark));
                if (mainActivity.fragmentManager.findFragmentByTag("programSearch") != null) {
                    mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programSearch"))).commit();
                    mainActivity.fragmentManager.beginTransaction().show(fragment).commit();
                    mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programSearch"))).commit();
                } else {
                    mainActivity.fragmentManager.beginTransaction().show(fragment).commit();
                }
                mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
            } else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramFragment(mainActivity), "four").commit();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                getEventProgram(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        filter_btn.setOnClickListener(v -> {
            ProgramFilter programFilter = new ProgramFilter(mainActivity);
            if (getFragmentManager() != null) {
                programFilter.setOnClickListener(body -> {
                    programFilterBody = body;
                    programFilter.dismiss();
                    createChips();
                    getEventProgram("");
                });
                if (getFragmentManager() != null) {
                    programFilter.show(getFragmentManager(), "programFilter");
                }
            }
        });
    }

    private void createChips() {
        if (programFilterBody.sportId != null) {
            for (Sport sport : Variables.sports) {
                if (sport.sportId.equals(programFilterBody.sportId)) {
                    Chip sportChip = new Chip(mainActivity);
                    sportChip.setCloseIconVisible(true);
                    sportChip.setOnCloseIconClickListener(v -> {
                        chipGroup.removeView(sportChip);
                        programFilterBody.sportId = null;
                        eventProgramCall.cancel();
                        getEventProgram("");
                    });
                    sportChip.setText(sport.sportNameRus);
                    chipGroup.addView(sportChip);
                }
            }
        }
        if (!programFilterBody.dateStart.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", new Locale("ru"));
            try {
                Date date = format.parse(programFilterBody.dateStart);
                format.applyPattern("dd/MM/yyyy HH:mm");
                String dateStart = "";
                if (date != null) {
                    dateStart = format.format(date);
                }
                Chip dateStartChip = new Chip(mainActivity);
                dateStartChip.setCloseIconVisible(true);
                dateStartChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(dateStartChip);
                    programFilterBody.dateStart = "";
                    eventProgramCall.cancel();
                    getEventProgram("");
                });
                dateStartChip.setText(dateStart);
                chipGroup.addView(dateStartChip);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!programFilterBody.dateFinish.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", new Locale("ru"));
            try {
                Date date = format.parse(programFilterBody.dateFinish);
                format.applyPattern("dd/MM/yyyy HH:mm");
                String dateEnd = "";
                if (date != null) {
                    dateEnd = format.format(date);
                }
                Chip dateEndChip = new Chip(mainActivity);
                dateEndChip.setCloseIconVisible(true);
                dateEndChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(dateEndChip);
                    programFilterBody.dateFinish = "";
                    eventProgramCall.cancel();
                    getEventProgram("");
                });
                dateEndChip.setText(dateEnd);
                chipGroup.addView(dateEndChip);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(programFilterBody.categoryId!=null){
            for (Category category : Variables.allCategories) {
                if (category.getId().equals(programFilterBody.categoryId)) {
                    Chip categoryChip = new Chip(mainActivity);
                    categoryChip.setCloseIconVisible(true);
                    categoryChip.setOnCloseIconClickListener(v -> {
                        chipGroup.removeView(categoryChip);
                        programFilterBody.categoryId = null;
                        eventProgramCall.cancel();
                        getEventProgram("");
                    });
                    categoryChip.setText(category.getNameRus());
                    chipGroup.addView(categoryChip);
                }
            }
        }
        /*if (programFilterBody.statusId!=null){

        }*/
        if (!programFilterBody.placement.equals("")){
            Chip placeChip = new Chip(mainActivity);
            placeChip.setCloseIconVisible(true);
            placeChip.setOnCloseIconClickListener(v -> {
                chipGroup.removeView(placeChip);
                programFilterBody.placement = "";
                eventProgramCall.cancel();
                getEventProgram("");
            });
            placeChip.setText(programFilterBody.placement);
            chipGroup.addView(placeChip);
        }
    }


    private void getEventProgram(String query) {
        Event eventApi = Server.GetServerWithToken(Event.class, Variables.token);
        eventProgramCall = eventApi.getEventProgramWithFilter(Variables.currentEvent.getId(), query, programFilterBody.dateStart, programFilterBody.dateFinish, programFilterBody.sportId, programFilterBody.placement, true);
        eventProgramCall.enqueue(new Callback<List<EventProgram>>() {
            @SuppressLint({"SimpleDateFormat", "ClickableViewAccessibility"})
            @Override
            public void onResponse(@NonNull Call<List<EventProgram>> call, @NonNull Response<List<EventProgram>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() != 0) {
                        List<Date> dates = new ArrayList<>();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
                        for (int i = 0; i < response.body().size(); i++) {
                            String dateStr = response.body().get(i).getDateTimeStart();
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
                        for (int i = 0; i < response.body().size(); i++) {
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
                        programByDays.clear();
                        for (int i = 0; i < datesList.size(); i++) {
                            List<EventProgram> tempEvents = new ArrayList<>();
                            for (int j = 0; j < response.body().size(); j++) {
                                String dateStr = response.body().get(j).getDateTimeStart();
                                format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
                                try {
                                    Date date = format.parse(dateStr);
                                    format.applyPattern("yyyy-MM-dd");
                                    if (date != null) {
                                        dateStr = format.format(date);
                                        if (dateStr.equals(datesList.get(i))) {
                                            tempEvents.add(response.body().get(j));
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            programByDays.add(new EventProgramByDays(datesList.get(i), tempEvents));
                            daySearchAdapter.notifyDataSetChanged();
                            if (programByDays.size() != 0) {
                                daySearchAdapter.performClick(0);
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            Log.d("EPROGRAM_RESPONSE_ERROR", "" + response.errorBody().string());
                        } else {
                            Log.d("EPROGRAM_RESPONSE_ERROR", "ErrorBody is null.");
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (IOException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "При загрузке программы мероприятий произошла ошибка.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EventProgram>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                    Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("EPROGRAM_SERVER_ERROR", "" + t.getMessage());
                }
            }
        });
    }

}
