package com.maksat.uni.fragments.statistic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.AlphabetRVAdapter;
import com.maksat.uni.bottomsheets.StatisticFilter;
import com.maksat.uni.fragments.home.HomeViewModel;
import com.maksat.uni.fragments.participants.ParticipantInfo;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.Category;
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

public class StatisticSearch extends Fragment {
    public StatisticSearch(MainActivity mainActivity, ParticipantsModel participantsModel, List<ParticipantsByAlphabet> alphabets, StatisticParticipants statisticParticipants, Integer position, Integer mainFlag) {
        this.mainActivity=mainActivity;
        this.participantsModel=participantsModel;
        this.alphabets=new ArrayList<>(alphabets);
        this.statisticParticipants=statisticParticipants;
        this.position=position;
        this.mainFlag=mainFlag;
    }

    private final MainActivity mainActivity;
    private final ParticipantsModel participantsModel;
    private Window window;
    private SearchView searchView;
    private RecyclerView alphabet_rv;
    private List<ParticipantsByAlphabet> alphabets, staticAlphabets;
    private final StatisticParticipants statisticParticipants;
    private AlphabetRVAdapter adapter;
    private ProgressBar progressBar;
    private Integer position;
    private final Integer mainFlag;
    private ChipGroup chipGroup;
    private ParticipantsFilterBody filterBody=new ParticipantsFilterBody(null, null, null, null, new ParticipantsFilterBody.sorting("lastNameRus", true),
            "", "", null, "", "", "", "", null);
    private Call<ParticipantsModel> call;
    private HomeViewModel homeViewModel;
    private MaterialButton back_btn;
    private final OnBackPressedCallback backPressedCallback=new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            backPress();
        }
    };
    private final OnBackPressedCallback clearPressedCallback=new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            adapter.setItems(alphabets);
            back_btn.setOnClickListener(backButtonClickListener);
        }
    };
    private final View.OnClickListener backButtonClickListener= v -> backPress();
    private final View.OnClickListener clearButtonClickListener=v->{
        adapter.setItems(alphabets);
        back_btn.setOnClickListener(backButtonClickListener);
    };

    private void backPress() {
        window.setStatusBarColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark));
        switch (mainFlag) {
            case 0:
                beginTransaction("statisticParticipants");
                mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                beginTransaction("programParticipants");
                break;
            case 2:
                beginTransaction("three");
                mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_search, container, false);
        window = mainActivity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(mainActivity, R.color.filterStatusBarColor));
        mainActivity.bottomAppBar.setVisibility(View.GONE);
        homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getPaticipantId().observe(getViewLifecycleOwner(), id -> {
            for(ParticipantsModel.item item: participantsModel.getItems()){
                if (id.equals(item.getId())){
                    List<ParticipantsByAlphabet> tempalphabets=new ArrayList<>();
                    List<ParticipantsModel.item> items=new ArrayList<>();
                    items.add(item);
                    tempalphabets.add(new ParticipantsByAlphabet(' ', items, null));
                    adapter.setItems(tempalphabets);
                    requireActivity().getOnBackPressedDispatcher().addCallback(this, clearPressedCallback);
                }
            }
        });
        List<Chip> chips = new ArrayList<>();
        if (position != null) {
            for (AcrStatus status : Variables.acrStatuses) {
                switch (position) {
                    case 1:
                        if (status.getNameRus().equals("Рассматривается")) {
                            filterBody.acrStatusStepOneId = status.getId();
                        }
                        break;
                    case 2:
                        if (status.getNameRus().equals("Одобрено")) {
                            filterBody.acrStatusStepOneId = status.getId();
                        }
                        break;
                }
            }
        }

        findIDs(view);
        int id =  searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setHintTextColor(ContextCompat.getColor(mainActivity, R.color.filterTextColor));
        textView.setTextColor(Color.WHITE);


        setInfo();
        return view;
    }

    private void findIDs(@NonNull View view) {
        back_btn = view.findViewById(R.id.back_btn);
        MaterialButton filter_btn = view.findViewById(R.id.filter_btn);
        MaterialButton qr_btn=view.findViewById(R.id.qr_btn);
        searchView=view.findViewById(R.id.searchView);
        alphabet_rv=view.findViewById(R.id.search_alphabet_rv);
        progressBar=view.findViewById(R.id.progressBar6);
        chipGroup=view.findViewById(R.id.chips);
        back_btn.setOnClickListener(backButtonClickListener);
        filter_btn.setOnClickListener(v -> {
            StatisticFilter statisticFilter=new StatisticFilter(mainActivity, filterBody, mainFlag);
            if (getFragmentManager() != null) {
                statisticFilter.setOnClickListener(participantsFilterBody -> {
                    filterBody=participantsFilterBody;
                    setChips();
                    statisticFilter.dismiss();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();

                });
                if (getFragmentManager() != null) {
                    statisticFilter.show(getFragmentManager(), "filter");
                }
            }
        });
        qr_btn.setOnClickListener(v -> {
            captureFromCamera();
        });
    }


    private void captureFromCamera() {
        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions((android.app.Activity) getContext(), PERMISSIONS, 111);
        } else {
            IntentIntegrator intentIntegrator=new IntentIntegrator(requireActivity());
            intentIntegrator.setOrientationLocked(false);
            IntentIntegrator.forSupportFragment(StatisticSearch.this).initiateScan();

            /*if(mainActivity.fragmentManager.findFragmentByTag("qrScanner")!=null){
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("qrScanner"))).commit();
            }else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new QRScannerFragment(mainActivity, homeViewModel), "qrScanner").commit();
            }*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Cancelled
            } else {
                try {
                    Integer id = Integer.parseInt(result.getContents());
                    for (ParticipantsModel.item item : participantsModel.getItems()) {
                        if (id.equals(item.getId())) {
                            /*List<ParticipantsByAlphabet> tempalphabets = new ArrayList<>();
                            List<ParticipantsModel.item> items = new ArrayList<>();
                            items.add(item);
                            tempalphabets.add(new ParticipantsByAlphabet(' ', items, null));
                            adapter.setItems(tempalphabets);
                            back_btn.setOnClickListener(clearButtonClickListener);
                            requireActivity().getOnBackPressedDispatcher().addCallback(this, clearPressedCallback);*/
                            getZones(item);
                            break;
                        }
                        if((participantsModel.getItems().size()-1)==participantsModel.getItems().indexOf(item)){
                            Toast.makeText(requireContext(), "Участник не найден", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(requireContext(), "QR-код не распознан", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getZones(@NonNull ParticipantsModel.item item) {
        Event zonesApi = Server.GetServerWithToken(Event.class, Variables.token);
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Zone>> call = zonesApi.getZonesForParticipant(Variables.currentEvent.getId(), item.getId());
        call.enqueue(new Callback<List<Zone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Zone>> call, @NonNull Response<List<Zone>> response) {
                mainActivity.bottomAppBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (mainActivity.fragmentManager.findFragmentByTag("participantInfo") != null) {
                            mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("participantInfo"))).commit();
                        }
                        mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, response.body()), "participantInfo").commit();
                    }
                } else {
                    if (mainActivity.fragmentManager.findFragmentByTag("participantInfo") != null) {
                        mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("participantInfo"))).commit();
                    }
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, new ArrayList<>()), "participantInfo").commit();
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            Log.d("ZONE_RESP_ERROR", "" + errorBody.Ru);
                        } else {
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
                if (mainActivity.fragmentManager.findFragmentByTag("participantInfo") != null) {
                    mainActivity.fragmentManager.beginTransaction().remove(mainActivity.fragmentManager.findFragmentByTag("participantInfo")).commit();
                }
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ParticipantInfo(mainActivity, item, new ArrayList<>()), "participantInfo").commit();
                Log.d("ZONE_SERV_ERROR", "" + t.getMessage());
            }
        });
    }


    private boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setChips() {
        if (filterBody.id!=null) {
            if (chipGroup.findViewWithTag("id") == null) {
                Chip idChip = new Chip(mainActivity);
                idChip.setTag("id");
                idChip.setCloseIconVisible(true);
                idChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(idChip);
                    filterBody.id = null;
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                idChip.setText(String.valueOf(filterBody.id));
                chipGroup.addView(idChip);
            }else {
                ((Chip)chipGroup.findViewWithTag("id")).setText(String.valueOf(filterBody.id));
            }
        }else if(chipGroup.findViewWithTag("id")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("id"));
        }
        if (filterBody.categoryId!=null) {
            if (chipGroup.findViewWithTag("category") == null) {
                Chip categoryChip = new Chip(mainActivity);
                categoryChip.setTag("category");
                categoryChip.setCloseIconVisible(true);
                categoryChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(categoryChip);
                    filterBody.categoryId = null;
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                for (Category category : Variables.allCategories) {
                    if (filterBody.categoryId.equals(category.getId())) {
                        categoryChip.setText(category.getNameRus());
                        break;
                    }
                }
                chipGroup.addView(categoryChip);
            }else {
                for (Category category : Variables.allCategories) {
                    if (filterBody.categoryId.equals(category.getId())) {
                        ((Chip)chipGroup.findViewWithTag("category")).setText(category.getNameRus());
                        break;
                    }
                }
            }
        }else if(chipGroup.findViewWithTag("category")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("category"));
        }
        if (filterBody.acrStatusStepOneId!=null) {
            if (chipGroup.findViewWithTag("acr") == null) {
                Chip statusChip = new Chip(mainActivity);
                statusChip.setCloseIconVisible(true);
                statusChip.setTag("acr");
                statusChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(statusChip);
                    filterBody.acrStatusStepOneId = null;
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                for (AcrStatus status : Variables.acrStatuses) {
                    if (filterBody.acrStatusStepOneId.equals(status.getId())) {
                        statusChip.setText(status.getNameRus());
                        break;
                    }
                }
                chipGroup.addView(statusChip);
            }else {
                for (AcrStatus status : Variables.acrStatuses) {
                    if (filterBody.acrStatusStepOneId.equals(status.getId())) {
                        ((Chip)chipGroup.findViewWithTag("acr")).setText(status.getNameRus());
                        break;
                    }
                }
            }
        }else if(chipGroup.findViewWithTag("acr")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("acr"));
        }
        if (!filterBody.name.equals("")) {
            if (chipGroup.findViewWithTag("name") == null) {
                Chip nameChip = new Chip(mainActivity);
                nameChip.setTag("name");
                nameChip.setCloseIconVisible(true);
                nameChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(nameChip);
                    filterBody.name = "";
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                nameChip.setText(filterBody.name);
                chipGroup.addView(nameChip);
            }else {
                ((Chip)chipGroup.findViewWithTag("name")).setText(filterBody.name);
            }
        }else if(chipGroup.findViewWithTag("name")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("name"));
        }
        if (!filterBody.companyName.equals("")) {
            if (chipGroup.findViewWithTag("company") == null) {
                Chip companyChip = new Chip(mainActivity);
                companyChip.setTag("company");
                companyChip.setCloseIconVisible(true);
                companyChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(companyChip);
                    filterBody.companyName = "";
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                companyChip.setText(filterBody.companyName);
                chipGroup.addView(companyChip);
            }else {
                ((Chip)chipGroup.findViewWithTag("company")).setText(filterBody.companyName);
            }
        }else if(chipGroup.findViewWithTag("company")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("company"));
        }
        if (!filterBody.positionName.equals("")) {
            if (chipGroup.findViewWithTag("position")==null) {
                Chip positionChip = new Chip(mainActivity);
                positionChip.setTag("position");
                positionChip.setCloseIconVisible(true);
                positionChip.setOnCloseIconClickListener(v -> {
                    chipGroup.removeView(positionChip);
                    filterBody.positionName = "";
                    call.cancel();
                    progressBar.setVisibility(View.VISIBLE);
                    doCall();
                });
                positionChip.setText(filterBody.positionName);
                chipGroup.addView(positionChip);
            }else {
                ((Chip)chipGroup.findViewWithTag("position")).setText(filterBody.positionName);
            }
        }else if(chipGroup.findViewWithTag("position")!=null){
            chipGroup.removeView(chipGroup.findViewWithTag("position"));
        }

    }

    private void beginTransaction(String query) {
        if (mainActivity.fragmentManager.findFragmentByTag("statisticSearch") != null) {
            mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticSearch"))).commit();
            if (mainActivity.fragmentManager.findFragmentByTag(query) != null) {
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag(query))).commit();
            }
            mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticSearch"))).commit();
        } else {
            if (mainActivity.fragmentManager.findFragmentByTag(query) != null) {
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag(query))).commit();
            }
        }
    }

    private void setInfo(){
        if (mainFlag!=1) {//если переход был не из программы
            adapter = new AlphabetRVAdapter(mainActivity, alphabets, null, statisticParticipants, null, participantsModel.getItems(), 0);
        }else {
            adapter = new AlphabetRVAdapter(mainActivity, alphabets, null, statisticParticipants, null, participantsModel.getItems(), 1);
        }
        alphabet_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        alphabet_rv.setAdapter(adapter);
        /*adapter.setOnParticipantClickListener(new AlphabetRVAdapter.OnItemClickListener() {
            @Override
            public void onParticipantClick(ParticipantsModel.item item) {

            }

            @Override
            public void onContactClick(Employee employee) {

            }
        });*/
        staticAlphabets=new ArrayList<>(alphabets);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("")) {
                    if (position!=null) {
                        if (position==0) {
                            position = null;
                        }
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        filterBody.id=Integer.parseInt(query);
                    }catch (Exception e){
                        filterBody.id=null;
                        filterBody.name=query;
                    }
                    doCall();
                }else {
                    alphabets=new ArrayList<>(staticAlphabets);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void doCall() {
        Event participantsApi = Server.GetServerWithToken(Event.class, Variables.token);
        call = participantsApi.getParicipantsWFilter(Variables.currentEvent.getId(), 1, 1000000, filterBody);
        call.enqueue(new Callback<ParticipantsModel>() {
            @Override
            public void onResponse(@NonNull Call<ParticipantsModel> call, @NonNull Response<ParticipantsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        alphabets.clear();
                        for (char letter = 'А'; letter <= 'Я'; letter++) {
                            List<ParticipantsModel.item> tempItems = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getLastNameRus().substring(0, 1).equals(String.valueOf(letter))) {
                                    tempItems.add(response.body().getItems().get(i));
                                }
                            }
                            if (tempItems.size() != 0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
                            }
                        }
                        for (char letter = 'а'; letter <= 'я'; letter++) {
                            List<ParticipantsModel.item> tempItems = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getLastNameRus().charAt(0) == letter) {
                                    tempItems.add(response.body().getItems().get(i));
                                }
                            }
                            if (tempItems.size() != 0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
                            }
                        }
                        for (char letter = 'A'; letter <= 'Z'; letter++) {
                            List<ParticipantsModel.item> tempItems = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getLastNameRus().charAt(0) == letter) {
                                    tempItems.add(response.body().getItems().get(i));
                                }
                            }
                            if (tempItems.size() != 0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
                            }
                        }
                        for (char letter = 'a'; letter <= 'z'; letter++) {
                            List<ParticipantsModel.item> tempItems = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getLastNameRus().charAt(0) == letter) {
                                    tempItems.add(response.body().getItems().get(i));
                                }
                            }
                            if (tempItems.size() != 0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
                            }
                        }
                        for (char letter = '0'; letter <= '9'; letter++) {
                            List<ParticipantsModel.item> tempItems = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                if (response.body().getItems().get(i).getLastNameRus().charAt(0) == letter) {
                                    tempItems.add(response.body().getItems().get(i));
                                }
                            }
                            if (tempItems.size() != 0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, tempItems, null));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(mainActivity, "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.d("PSTAT_RESPONSE_ERROR", "" + response.errorBody().string());
                        } else {
                            Log.d("PSTAT_RESPONSE_ERROR", "ErrorBody is null.");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ParticipantsModel> call, @NonNull Throwable t) {
                if (!call.isCanceled())
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mainActivity, "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("SERVER_ERROR", "" + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.GONE);
    }



}
