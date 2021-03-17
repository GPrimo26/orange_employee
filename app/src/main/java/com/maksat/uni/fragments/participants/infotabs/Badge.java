package com.maksat.uni.fragments.participants.infotabs;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.participants.ParticipantInfo;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.BadgeStatus;
import com.maksat.uni.models.BadgeType;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.Zone;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Badge extends BaseFragment {
    public Badge(MainActivity mainActivity, ParticipantsModel.item item, List<Zone> zones, ParticipantInfo participantInfo) {
        this.mainActivity=mainActivity;
        this.item=item;
        this.zones=zones;
        this.participantInfo=participantInfo;
    }

    private final MainActivity mainActivity;
    private final ParticipantsModel.item item;
    private ProgressBar progressBar;
    private TextView status_tv, statusBadge_tv, zones_tv, objects_tv;
    private Chip badgeCategory_chip;
    private MaterialButton info1_btn, info2_btn;
    private List<BadgeType> badgeTypes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final List<Zone> zones;
    private ParticipantInfo participantInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge, container, false);
        findIDs(view);
        doCalls();
        setInfo();
        return view;
    }

    private void findIDs(View view) {
        progressBar=view.findViewById(R.id.progressBar5);
        status_tv=view.findViewById(R.id.status_tv);
        statusBadge_tv=view.findViewById(R.id.statusbadge_tv);
        zones_tv=view.findViewById(R.id.zones_tv);
        objects_tv=view.findViewById(R.id.objects_tv);
        badgeCategory_chip=view.findViewById(R.id.badgecategory_chip);
        info1_btn=view.findViewById(R.id.info1_btn);
        info2_btn=view.findViewById(R.id.info2_btn);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);
    }

    private void doCalls() {
        com.maksat.uni.interfaces.Badge badgeApi= Server.GetServerWithToken(com.maksat.uni.interfaces.Badge.class, Variables.token);
        Call<List<BadgeType>> call=badgeApi.getBadgeTypes(Variables.currentEvent.getId());
        call.enqueue(new Callback<List<BadgeType>>() {
            @Override
            public void onResponse(Call<List<BadgeType>> call, Response<List<BadgeType>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null) {
                        badgeTypes = response.body();
                        progressBar.setVisibility(View.GONE);
                        for (BadgeType badgeType: badgeTypes){
                            int flag=0;
                            for (BadgeType.Category category: badgeType.getCategories()){
                                if (item.getCategory().getNameRus().equals(category.getNameRus())){
                                    badgeCategory_chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(badgeType.getColor())));
                                    flag=1;
                                    break;
                                }
                            }
                            if (flag==1){
                                break;
                            }
                        }
                    }
                }else {
                    Toast.makeText(getContext(), "При загрузке мероприятий произошла ошибка.", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.d("BTYPES_RESPONSE_ERROR", ""+response.errorBody().string());
                        }else {
                            Log.d("BTYPES_RESPONSE_ERROR", "ErrorBody is null.");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BadgeType>> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("BTYPES_SERVER_ERROR", ""+t.getMessage());
            }
        });
    }

    private void setInfo(){
        /*if (item.getAcrStatusStepOneId()!=null) {
            switch (item.getAcrStatusStepOneId()) {
                case 2:
                    status_tv.setText("Одобрено");
                    break;
                case 3:
                    status_tv.setText("Отказано");
                    break;
                case 1:
                    status_tv.setText("На рассмотрении");
                    break;
                default:
                    status_tv.setText("Бейдж не создан");
                    break;
            }
        }else {
            status_tv.setText("Информация отсутствует");
        }*/
       /* if (item.getBadgeStatusId()!=null) {
            switch (item.getBadgeStatusId()) {
                case 2:
                    statusBadge_tv.setText("Скомпрометирован");
                    break;
                case 3:
                    statusBadge_tv.setText("Распечатан");
                    break;
                default:
                    statusBadge_tv.setText("Не печатан");
            }
        }else {
            statusBadge_tv.setText("Информация отсутствует");
        }*/
        if (item.getBadgeStatusId() != null) {
            for (BadgeStatus status : Variables.badgeStatuses) {
                if (item.getBadgeStatusId().equals(status.id)) {
                    statusBadge_tv.setText(status.nameRus);
                    break;
                }
            }
        } else {
            statusBadge_tv.setText("Бейдж не создан");
        }
        if (item.getCategory().getNameRus()!=null) {
            if (item.getCategory().getNameRus().equals("")){
                badgeCategory_chip.setVisibility(View.GONE);
            }else {
                badgeCategory_chip.setText(item.getCategory().getNameRus());
            }
        }else {
            badgeCategory_chip.setVisibility(View.GONE);
        }
        StringBuilder text = new StringBuilder();
        if (zones!=null) {
            if (zones.size()!=0) {
                for (int i = 0; i < zones.size(); i++) {
                    if (i != zones.size() - 1) {
                        text.append(zones.get(i).code).append(", ");
                    } else {
                        text.append(zones.get(i).code);
                    }
                }
                zones_tv.setText(text.toString());
                info1_btn.setOnClickListener(v -> {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mainActivity);
                    bottomSheetDialog.setContentView(R.layout.dialog_info);
                    LinearLayout main_ll = bottomSheetDialog.findViewById(R.id.main_ll);
                    if (main_ll != null) {
                        main_ll.setOrientation(LinearLayout.VERTICAL);
                        for (Zone zone : zones) {
                            LinearLayout ll = new LinearLayout(mainActivity);
                            ll.setOrientation(LinearLayout.VERTICAL);
                            main_ll.addView(ll);
                            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ll.getLayoutParams());
                            llParams.bottomMargin = Math.round(TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                            ll.setLayoutParams(llParams);
                            TextView code = new TextView(mainActivity);
                            code.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                            code.setTextColor(Color.parseColor("#DE000000"));
                            code.setTypeface(ResourcesCompat.getFont(mainActivity, R.font.roboto));
                            code.setText(zone.code);
                            ll.addView(code);

                            TextView name = new TextView(mainActivity);
                            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            name.setTextColor(Color.parseColor("#99000000"));
                            name.setTypeface(ResourcesCompat.getFont(mainActivity, R.font.roboto));
                            name.setText(zone.name);
                            ll.addView(name);
                        }
                        bottomSheetDialog.show();
                    }
                });
            } else {
                ifZonesEmpty();
            }
        } else {
            ifZonesEmpty();
        }
    }

    private void ifZonesEmpty() {
        zones_tv.setText("Инофрмация отсутствует");
        info1_btn.setOnClickListener(v -> Toast.makeText(getContext(), "Список пуст", Toast.LENGTH_SHORT).show());

    }
}
