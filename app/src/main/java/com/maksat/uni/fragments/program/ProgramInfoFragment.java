package com.maksat.uni.fragments.program;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.JudgesRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.ParticipantsEventProgramFilterBody;
import com.maksat.uni.models.ParticipantsFilterBody;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramInfoFragment extends BaseFragment {

    public ProgramInfoFragment(MainActivity mainActivity, EventProgram program, Integer flag) {
    this.mainActivity=mainActivity;
    this.program=program;
    this.flag=flag;
    }

    private MainActivity mainActivity;
    private EventProgram program;
    private MaterialButton back_btn, participants_btn;
    private TextView title_tv, name_tv, sport_tv, description_tv;
    private RecyclerView judges_rv;
    private JudgesRVAdapter adapter;
    private ProgramInfoFragment programInfoFragment;
    private final Integer flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_program_info, container, false);
        programInfoFragment=this;
        findIDs(view);
        setInfo();
        return view;
    }

    private void findIDs(View view) {
        back_btn=view.findViewById(R.id.program_back_btn);
        participants_btn=view.findViewById(R.id.programinfo_participants_btn);
        title_tv=view.findViewById(R.id.programinfo_title_tv);
        name_tv=view.findViewById(R.id.programinfo_name_tv);
        sport_tv=view.findViewById(R.id.programinfo_sport_tv);
        description_tv=view.findViewById(R.id.programinfo_description_tv);
        judges_rv=view.findViewById(R.id.programinfo_judges_rv);
    }

    private void setInfo(){
        String text ="", start=program.getDateTimeStart(), end=program.getDateTimeFinish();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
        try {
            Date date = format.parse(start);
            format.applyPattern("HH:mm");
            if (date != null) {
                text = format.format(date);

            }
            format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
            date=format.parse(end);
            format.applyPattern("HH:mm");
            if (date != null) {
                text+=" - "+format.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (program.getPlaceRus()!=null) {
            if (!program.getPlaceRus().equals("")) {
                text += " " + mainActivity.getResources().getString(R.string.dot) + " " + program.getPlaceRus();
            }
        }
        title_tv.setText(text);
        name_tv.setText(program.getNameRus());
        if (program.getSportNameRus()!=null){
            if (!program.getSportNameRus().equals("")){
                text=program.getSportNameRus();
                //тут нужно добавить в text точку, если пол указан.
            }else {
                text="";
            }
        }else {
            text="";
        }
        sport_tv.setText(text);
        if (program.getDescriptionRus()!=null){
            if (!program.getDescriptionRus().equals("")){
                description_tv.setText(program.getDescriptionRus());
            }else {
                description_tv.setText("Описание отсутствует");
            }
        }else {
            description_tv.setText("Описание отсутствует");
        }
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
                                participants_btn.setOnClickListener(v -> Toast.makeText(getContext(), "Список пуст", Toast.LENGTH_SHORT).show());
                            } else {
                                List<ParticipantsModel.item> items = new ArrayList<>();
                                for (ParticipantsModel.item item : response.body().getItems()) {
                                    if (item.getCategory().getNameRus().equals("Судья") || item.getCategory().getNameRus().equals("Главный судья")) {
                                        items.add(item);
                                    }
                                }
                                if (adapter == null) {
                                    adapter = new JudgesRVAdapter(mainActivity, items);
                                    judges_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                                    judges_rv.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                                if (flag==0) {
                                    participants_btn.setOnClickListener(v -> {
                                        if (mainActivity.fragmentManager.findFragmentByTag("programParticipants") != null) {
                                            mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programParticipants"))).commit();
                                        } else {
                                            mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ProgramParticipantsFragment(mainActivity, response.body(), program.getNameRus(), null, programInfoFragment), "programParticipants").commit();
                                        }
                                        mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("programInfo"))).commit();
                                    });
                                }else {
                                    participants_btn.setClickable(false);
                                    if (program.getStatusId()!=null)
                                        switch (program.getStatusId()){
                                            case 2:
                                                participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                                                break;
                                            case 3:
                                                participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_deny));
                                                break;
                                            case 1:
                                            default:
                                                participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                                        }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                        try {
                            if (response.errorBody() != null) {
                                Log.d("JUDGES_RESP_ERROR", "" + response.errorBody().string());
                            } else {
                                Log.d("JUDGES_RESP_ERROR", "ErrorBody is null.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ParticipantsModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("JUDGES_SERV_ERROR", "" + t.getMessage());
                }
            });

        back_btn.setOnClickListener(v -> {
            Fragment fragment = mainActivity.fragmentManager.findFragmentByTag("programInfo");
            if (fragment != null) {
                mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                mainActivity.fragmentManager.beginTransaction().remove(fragment).commit();
            }
            if (flag==0) {
                mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
            }

        });
    }

}
