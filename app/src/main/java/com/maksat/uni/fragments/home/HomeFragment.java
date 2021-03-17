package com.maksat.uni.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.HomeTodayTasksRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.interfaces.Dictionary;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.BadgeStatus;
import com.maksat.uni.models.Category;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.EventProgramByDays;
import com.maksat.uni.models.Events;
import com.maksat.uni.models.Sport;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    public HomeFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    private final MainActivity mainActivity;
    private RecyclerView recyclerView;
    private LinearLayout contentLayout;
    private ImageView avatar_iv;
    public Events events;
    private ProgressBar avatar_prb;
    private TextView title_tv, date_tv;
    private List<EventProgram> eventPrograms;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeTodayTasksRVAdapter adapter;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        findIDs(view);


        ViewTreeObserver vto = avatar_iv.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                avatar_iv.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = avatar_iv.getMeasuredHeight();
                int finalWidth = avatar_iv.getMeasuredWidth();
                CoordinatorLayout.LayoutParams layoutParams=(CoordinatorLayout.LayoutParams) contentLayout.getLayoutParams();
                layoutParams.topMargin=finalHeight-36;
                contentLayout.setLayoutParams(layoutParams);
                return true;
            }
        });
        /*sheetBehavior = BottomSheetBehavior.from(contentLayout);
        sheetBehavior.setFitToContents(false);
        sheetBehavior.setHideable(false);//prevents the boottom sheet from completely hiding off the screen
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //sheetBehavior.setPeekHeight(0);


        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });*/

        swipeRefreshLayout.setRefreshing(true);
        doCalls();

        homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getEvent().observe(getViewLifecycleOwner(), event -> {
            Dictionary dictionaryApi=Server.GetServer(Dictionary.class);
            Variables.currentEvent=event;
            doSomeWork(dictionaryApi);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void findIDs(View view) {
        recyclerView = view.findViewById(R.id.tasks_rv);
        contentLayout = view.findViewById(R.id.backdrop);
        avatar_iv=view.findViewById(R.id.avatar_iv);
        avatar_prb=view.findViewById(R.id.avatar_prb);
        title_tv=view.findViewById(R.id.title_tv);
        date_tv=view.findViewById(R.id.date_tv);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);
    }

    private void doCalls() {

        Dictionary dictionaryApi=Server.GetServer(Dictionary.class);
        Call<List<Sport>> call1=dictionaryApi.getSports();
        call1.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sport>> call, @NonNull Response<List<Sport>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Variables.sports=response.body();
                    }
                }else {
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
            }

            @Override
            public void onFailure(@NonNull Call<List<Sport>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("SPORTS_SERV_ERROR", ""+t.getMessage());
            }
        });
        getEvents();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (events!=null){
                swipeRefreshLayout.setRefreshing(true);
                getEvents();
            }else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getEvents() {
        Event eventApi=Server.GetServerWithToken(Event.class, Variables.token);
        Call<Events> eventsCall=eventApi.getEvents();
        eventsCall.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NonNull Call<Events> call, @NonNull Response<Events> response) {
                if (response.isSuccessful()){
                    events=response.body();
                    if (events != null) {
                        Variables.events=events.getEvents();
                        for(Events.events event:events.getEvents()){
                            if (event.getId().equals(events.getCurrEventId())){
                                Variables.currentEvent=new Events.events(event);
                            }
                        }
                        Variables.currentEvent.setId(events.getCurrEventId());
                        com.maksat.uni.interfaces.Dictionary dictionaryApi= Server.GetServer(com.maksat.uni.interfaces.Dictionary.class);
                        Call<List<AcrStatus>> call1=dictionaryApi.getAcrStatuses();
                        Call<List<BadgeStatus>> call3=dictionaryApi.getBadgeStatuses();
                        call1.enqueue(new Callback<List<AcrStatus>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<AcrStatus>> call, @NonNull Response<List<AcrStatus>> response) {
                                if (response.isSuccessful()){
                                    if (response.body()!=null) {
                                        Variables.acrStatuses = response.body();
                                    }
                                }else {
                                    try {
                                        if (response.errorBody() != null) {
                                            Gson gson = new Gson();
                                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                                            Log.d("ACRS_RESP_ERROR", ""+errorBody.Ru);
                                        }else {
                                            Log.d("ACRS_RESP_ERROR", "ErrorBody is null.");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<AcrStatus>> call, @NonNull Throwable t) {
                                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                                Log.d("ACRS_SERV_ERROR", ""+t.getMessage());
                            }
                        });
                        call3.enqueue(new Callback<List<BadgeStatus>>() {
                            @Override
                            public void onResponse(Call<List<BadgeStatus>> call, Response<List<BadgeStatus>> response) {
                                if (response.isSuccessful()){
                                    if (response.body()!=null){
                                        Variables.badgeStatuses=response.body();
                                    }
                                }else {
                                    try {
                                        if (response.errorBody() != null) {
                                            Gson gson = new Gson();
                                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                                            Log.d("BADGES_RESP_ERROR", ""+errorBody.Ru);
                                        }else {
                                            Log.d("BADGES_RESP_ERROR", "ErrorBody is null.");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BadgeStatus>> call, Throwable t) {
                                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                                Log.d("BADGES_SERV_ERROR", ""+t.getMessage());
                            }
                        });
                        doSomeWork(dictionaryApi);
                    }
                }else {
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
            }

            @Override
            public void onFailure(@NonNull Call<Events> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("EVENTS_SERVER_ERROR", ""+t.getMessage());
            }
        });
    }

    private void doSomeWork(Dictionary dictionaryApi) {
        Call<List<Category>> call2=dictionaryApi.getCategories(Variables.currentEvent.getId());
        call2.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Variables.allCategories=response.body();
                    }
                }else {
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
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("CATEGS_SERV_ERROR", ""+t.getMessage());
            }
        });
        getEventProgram();
        for (Events.events event : events.getEvents()) {
            if (Variables.currentEvent.getId().equals(event.getId())) {
                title_tv.setText(event.getNameFullRus());
                String dateStr = event.getDateStart();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
                try {
                    Date date = format.parse(dateStr);
                    format = new SimpleDateFormat("dd MMMM yyyy", new Locale("ru"));
                    if (date != null) {
                        dateStr = format.format(date);
                        date_tv.setText(dateStr);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getEventProgram() {
        Event eventApi=Server.GetServerWithToken(Event.class, Variables.token);
        Call<List<EventProgram>> eventProgramCall=eventApi.getEventProgram(Variables.currentEvent.getId());
        eventProgramCall.enqueue(new Callback<List<EventProgram>>() {
            @SuppressLint({"SimpleDateFormat", "ClickableViewAccessibility"})
            @Override
            public void onResponse(@NonNull Call<List<EventProgram>> call, @NonNull Response<List<EventProgram>> response) {
                if (response.isSuccessful()){
                    eventPrograms=response.body();
                    if (eventPrograms != null && eventPrograms.size() != 0) {
                        List<Date> dates=new ArrayList<>();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
                        for (int i=0; i<eventPrograms.size(); i++){
                            String dateStr = eventPrograms.get(i).getDateTimeStart();
                            try {
                                Date date=format.parse(dateStr);
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
                        Variables.datesList = datesList;
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
                        Variables.programByDays = programByDays;
                        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                        for (EventProgramByDays eventProgramByDays : programByDays) {
                            if (eventProgramByDays.getDate().equals(todayDate)) {
                                if (adapter == null) {
                                    adapter = new HomeTodayTasksRVAdapter(mainActivity, eventProgramByDays);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                recyclerView.setAdapter(adapter);

                                recyclerView.setOnTouchListener((v, event) -> {
                                    int action = event.getAction();
                                    switch (action) {
                                        case MotionEvent.ACTION_DOWN:
                                            // Disallow NestedScrollView to intercept touch events.
                                            v.getParent().requestDisallowInterceptTouchEvent(true);
                                            break;

                                        case MotionEvent.ACTION_UP:
                                            // Allow NestedScrollView to intercept touch events.
                                            v.getParent().requestDisallowInterceptTouchEvent(false);
                                            break;
                                    }

                                    // Handle RecyclerView touch events.
                                    v.onTouchEvent(event);
                                    return true;
                                });
                                break;
                            }
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }else {
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
            }

            @Override
            public void onFailure(@NonNull Call<List<EventProgram>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("EPROGRAM_SERVER_ERROR", ""+t.getMessage());
            }
        });
    }
}
