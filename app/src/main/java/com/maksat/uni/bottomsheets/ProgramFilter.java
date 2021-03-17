package com.maksat.uni.bottomsheets;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.customUI.ETChangeListener;
import com.maksat.uni.customUI.ETClearButtonClickListener;
import com.maksat.uni.models.ProgramFilterBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgramFilter extends DialogFragment {

    public ProgramFilter(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }


    private static OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onClick(ProgramFilterBody programFilterBody);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private MainActivity mainActivity;
    private LinearLayout category_ll, status_ll, date_ll, time_ll, rg_ll, main_ll, sport_ll;
    private TextView category_tv, status_tv, date_tv, time_tv, title_tv, sportType_tv;
    private EditText gender_et, placement_et;
    private RadioGroup radioGroup;
    private ExtendedFloatingActionButton apply_efab;
    private MaterialButton close_btn, clear_btn, clearSport_btn, clearGender_btn, clearPlacement_btn, clearCategory_btn, clearStatus_btn, clearDate_btn, clearTime_btn;
    private final View.OnClickListener onClickListener1= v -> dismiss();
    private final View.OnClickListener onClickListener2=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            title_tv.setText("Фильтр");
            clear_btn.setVisibility(View.VISIBLE);
            close_btn.setOnClickListener(onClickListener1);
            main_ll.setVisibility(View.VISIBLE);
            rg_ll.setVisibility(View.GONE);
            radioGroup.removeAllViews();
        }
    },
            checkEfabListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (flag){
                        case 1:
                            clearCategory_btn.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            clearStatus_btn.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            clearSport_btn.setVisibility(View.VISIBLE);
                            break;
                    }
                    title_tv.setText("Фильтр");
                    clear_btn.setVisibility(View.VISIBLE);
                    close_btn.setOnClickListener(onClickListener1);
                    main_ll.setVisibility(View.VISIBLE);
                    rg_ll.setVisibility(View.GONE);
                    radioGroup.removeAllViews();
                    apply_efab.setExtended(true);
                    apply_efab.setOnClickListener(applyEfabListener);
                }
            },
            applyEfabListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> strings=new ArrayList<>();
                    strings.add(finalDateStart);
                    strings.add(finalDateEnd);
                    strings.add(finalTimeStart);
                    strings.add(finalTimeEnd);
                    boolean flag = false;
                    for (int i=0; i<strings.size(); i++){
                        if (strings.get(i).equals("")){
                            if (i<2){
                                date_tv.setError("Укажите дату");
                            }else {
                                time_tv.setError("Укажите время");
                            }
                            flag=true;
                        }
                    }
                    if (flag){
                        return;
                    }
                    finalDateStart+="T"+finalTimeStart;
                    finalDateEnd+="T"+finalTimeEnd;
                    ProgramFilterBody programFilterBody=new ProgramFilterBody(categoryId, statusId, finalDateStart, finalDateEnd, sportId, genderId, placement_et.getText().toString());
                    if (mListener!=null){
                        mListener.onClick(programFilterBody);
                    }
                }
            };
    private Integer sportId=null, categoryId=null, statusId=null, flag, genderId=null;
    private String finalDateStart="", finalDateEnd="", finalTimeStart="", finalTimeEnd="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_program_filter, container, false);

        findIDs(view);
        setActions();
        return view;
    }

    private void findIDs(@NonNull View view) {
        category_ll=view.findViewById(R.id.category_ll);
        status_ll=view.findViewById(R.id.status_ll);
        date_ll=view.findViewById(R.id.date_ll);
        time_ll=view.findViewById(R.id.time_ll);
        rg_ll=view.findViewById(R.id.rv_ll);
        category_tv=view.findViewById(R.id.filter_category_tv);
        status_tv=view.findViewById(R.id.filter_status_tv);
        date_tv=view.findViewById(R.id.filter_date_tv);
        time_tv=view.findViewById(R.id.filter_time_tv);
        sportType_tv=view.findViewById(R.id.filter_sport_tv);
        gender_et=view.findViewById(R.id.gender_et);
        placement_et=view.findViewById(R.id.place_et);
        radioGroup=view.findViewById(R.id.filter_rg);
        main_ll=view.findViewById(R.id.main_ll);
        title_tv=view.findViewById(R.id.main_title_tv);
        clear_btn=view.findViewById(R.id.clear_all_btn);
        apply_efab=view.findViewById(R.id.apply_efab);
        close_btn=view.findViewById(R.id.filter_close_btn);
        clearSport_btn=view.findViewById(R.id.clear_sportid_btn);
        clearGender_btn=view.findViewById(R.id.clear_gender_btn);
        clearPlacement_btn=view.findViewById(R.id.clear_placement_btn);
        clearCategory_btn=view.findViewById(R.id.clear_category_btn);
        clearStatus_btn=view.findViewById(R.id.clear_status_btn);
        clearDate_btn =view.findViewById(R.id.clear_date_btn);
        clearTime_btn=view.findViewById(R.id.clear_time_btn);
        sport_ll=view.findViewById(R.id.sport_ll);
    }

    private void setActions(){
        final Date[] date = new Date[2];
        date_ll.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setTitleText("Выберите интервал");
            MaterialDatePicker<Pair<Long, Long>> materialDatePicker=builder.build();
            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                if (selection.first!=null && selection.second!=null) {
                    date[0] = new Date(selection.first);
                    date[1] = new Date(selection.second);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("ru"));
                    String text = format.format(date[0]) + " - " + format.format(date[1]);
                    date_tv.setText(text);
                    format.applyPattern("yyyy-MM-dd");
                    finalDateStart = format.format(date[0]);
                    finalDateEnd=format.format(date[1]);
                    clearDate_btn.setVisibility(View.VISIBLE);
                    date_tv.setError(null);
                    if (apply_efab.getVisibility()==View.GONE){
                        apply_efab.setVisibility(View.VISIBLE);
                    }
                }
            });
            materialDatePicker.show(getChildFragmentManager(), materialDatePicker.toString());
        });
        time_ll.setOnClickListener(v -> {
            final MaterialTimePicker.Builder[] builder = {new MaterialTimePicker.Builder()};
            builder[0].setTitleText("Время начала");
            builder[0].setTimeFormat(TimeFormat.CLOCK_24H);
            final MaterialTimePicker[] materialTimePicker = {builder[0].build()};
            materialTimePicker[0].addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sHour;
                    if(materialTimePicker[0].getHour() < 10){
                        sHour = "0"+materialTimePicker[0].getHour();
                    } else {
                        sHour = String.valueOf(materialTimePicker[0].getHour());
                    }
                    String sMinute;
                    if(materialTimePicker[0].getMinute() < 10){
                        sMinute = "0"+materialTimePicker[0].getMinute();
                    } else {
                        sMinute = String.valueOf(materialTimePicker[0].getMinute());
                    }
                    String timeStart= sHour+":"+sMinute;
                    MaterialTimePicker.Builder builder1=new MaterialTimePicker.Builder();
                    builder1.setTitleText("Время окончания");
                    builder1.setTimeFormat(TimeFormat.CLOCK_24H);
                    MaterialTimePicker materialTimePicker1=builder1.build();
                    materialTimePicker1.addOnPositiveButtonClickListener(v1 -> {
                        if (apply_efab.getVisibility()==View.GONE){
                            apply_efab.setVisibility(View.VISIBLE);
                        }
                        String sHour1;
                        if(materialTimePicker1.getHour() < 10){
                            sHour1 = "0"+materialTimePicker1.getHour();
                        } else {
                            sHour1 = String.valueOf(materialTimePicker1.getHour());
                        }
                        String sMinute1;
                        if(materialTimePicker1.getMinute() < 10){
                            sMinute1 = "0"+materialTimePicker1.getMinute();
                        } else {
                            sMinute1 = String.valueOf(materialTimePicker1.getMinute());
                        }
                        String timeEnd= sHour1 +":"+ sMinute1;
                        String text=timeStart+" - "+timeEnd;
                        time_tv.setText(text);
                        finalTimeStart=timeStart;
                        finalTimeEnd=timeEnd;
                        clearTime_btn.setVisibility(View.VISIBLE);
                        time_tv.setError(null);
                    });
                    if (getFragmentManager() != null) {
                        materialTimePicker1.show(getFragmentManager(), materialTimePicker1.toString());
                    }
                }
            });
            if (getFragmentManager() != null) {
                materialTimePicker[0].show(getFragmentManager(), materialTimePicker[0].toString());
            }
        });
        apply_efab.setOnClickListener(applyEfabListener);
        sport_ll.setOnClickListener(v -> {
            if (Variables.sports.size()!=0){
            flag=3;
            main_ll.setVisibility(View.GONE);
            rg_ll.setVisibility(View.VISIBLE);
            title_tv.setText("Вид спорта");
            clear_btn.setVisibility(View.GONE);
            close_btn.setOnClickListener(onClickListener2);
            for (int i = 0; i< Variables.sports.size(); i++){
                RadioButton radioButton=new RadioButton(mainActivity);
                radioButton.setId(i);
                radioButton.setTextColor(Color.parseColor("#DE000000"));
                radioButton.setTypeface(ResourcesCompat.getFont(mainActivity, R.font.roboto));
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                radioButton.setText(Variables.sports.get(i).sportNameRus);
                radioButton.setPadding(Math.round(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics())), radioButton.getPaddingTop(),
                        radioButton.getPaddingRight(),
                        radioButton.getPaddingBottom());
                radioGroup.addView(radioButton);
                LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams) radioButton.getLayoutParams();
                layoutParams.height=Math.round(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 48,getResources().getDisplayMetrics()));
                layoutParams.setMarginStart(Math.round(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 16,getResources().getDisplayMetrics())));
                radioButton.setLayoutParams(layoutParams);
            }
            apply_efab.setExtended(false);
            apply_efab.setOnClickListener(checkEfabListener);
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                ((RadioButton)radioGroup.getChildAt(checkedId)).setChecked(true);
                sportType_tv.setText(Variables.sports.get(checkedId).sportNameRus);
                apply_efab.setVisibility(View.VISIBLE);
                sportId=Variables.sports.get(checkedId).sportId;
            });
            if (sportId!=null){
                for (int i=0; i<Variables.sports.size(); i++){
                    if (sportId.equals(Variables.sports.get(i).sportId)){
                        ((RadioButton)radioGroup.getChildAt(i)).setChecked(true);
                        break;
                    }
                }
            }
        }else {
                Toast.makeText(getContext(), "Список пуст...", Toast.LENGTH_SHORT).show();
            }
        });
        List<EditText> editTexts=new ArrayList<>();
        editTexts.add(placement_et);
        editTexts.add(gender_et);
        List<TextView> textViews=new ArrayList<>();
        textViews.add(category_tv);
        textViews.add(status_tv);
        textViews.add(time_tv);
        textViews.add(date_tv);
        textViews.add(sportType_tv);

        List<MaterialButton> clearButtons = new ArrayList<>();
        clearButtons.add(clearCategory_btn);
        clearButtons.add(clearStatus_btn);
        clearButtons.add(clearDate_btn);
        clearButtons.add(clearTime_btn);
        clearButtons.add(clearSport_btn);
        clearButtons.add(clearGender_btn);
        clearButtons.add(clearPlacement_btn);

        clearGender_btn.setOnClickListener(new ETClearButtonClickListener(gender_et, apply_efab, editTexts, textViews));
        clearPlacement_btn.setOnClickListener(new ETClearButtonClickListener(placement_et, apply_efab, editTexts, textViews));
        clearSport_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                sportType_tv.setText("");
                sportId=null;
                clearSport_btn.setVisibility(View.GONE);
                int flag=0;
                for (EditText editText: editTexts){
                    if (!editText.getText().toString().equals("")){
                        flag=1;
                    }
                }
                for (TextView textView: textViews){
                    if (!textView.getText().toString().equals("")){
                        flag=1;
                    }
                }
                if (flag!=1)
                {
                    apply_efab.setVisibility(View.GONE);
                }else {
                    apply_efab.setVisibility(View.VISIBLE);
                }
            }
        });
        clearCategory_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                category_tv.setText("");
                categoryId=null;
                clearCategory_btn.setVisibility(View.GONE);
                int flag=0;
                for (EditText editText: editTexts){
                    if (!editText.getText().toString().equals("")){
                        flag=1;
                    }
                }
                for (TextView textView: textViews){
                    if (!textView.getText().toString().equals("")){
                        flag=1;
                    }
                }
                if (flag!=1)
                {
                    apply_efab.setVisibility(View.GONE);
                }else {
                    apply_efab.setVisibility(View.VISIBLE);
                }
            }
        });
        clearStatus_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                status_tv.setText("");
                statusId=null;
                clearStatus_btn.setVisibility(View.GONE);
                int flag=0;
                for (EditText editText: editTexts){
                    if (!editText.getText().toString().equals("")){
                        flag=1;
                    }
                }
                for (TextView textView: textViews){
                    if (!textView.getText().toString().equals("")){
                        flag=1;
                    }
                }
                if (flag!=1)
                {
                    apply_efab.setVisibility(View.GONE);
                }else {
                    apply_efab.setVisibility(View.VISIBLE);
                }
            }
        });
        clearDate_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                date_tv.setText("");
                finalDateStart="";
                finalDateEnd="";
                clearDate_btn.setVisibility(View.GONE);
                int flag=0;
                for (EditText editText: editTexts){
                    if (!editText.getText().toString().equals("")){
                        flag=1;
                    }
                }
                for (TextView textView: textViews){
                    if (!textView.getText().toString().equals("")){
                        flag=1;
                    }
                }
                if (flag!=1)
                {
                    apply_efab.setVisibility(View.GONE);
                }else {
                    apply_efab.setVisibility(View.VISIBLE);
                }
            }
        });
        clearTime_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                time_tv.setText("");
                finalTimeStart="";
                finalTimeEnd="";
                clearTime_btn.setVisibility(View.GONE);
                int flag=0;
                for (EditText editText: editTexts){
                    if (!editText.getText().toString().equals("")){
                        flag=1;
                    }
                }
                for (TextView textView: textViews){
                    if (!textView.getText().toString().equals("")){
                        flag=1;
                    }
                }
                if (flag!=1)
                {
                    apply_efab.setVisibility(View.GONE);
                }else {
                    apply_efab.setVisibility(View.VISIBLE);
                }
            }
        });
        clear_btn.setOnClickListener(v -> {
            category_tv.setText("");
            categoryId=null;
            status_tv.setText("");
            statusId=null;
            date_tv.setText("");
            finalDateStart="";
            finalDateEnd="";
            time_tv.setText("");
            finalTimeStart="";
            finalTimeEnd="";
            sportType_tv.setText("");
            sportId=null;
            gender_et.setText("");
            placement_et.setText("");
            for (MaterialButton materialButton: clearButtons){
                materialButton.setVisibility(View.GONE);
            }
            apply_efab.setVisibility(View.GONE);
        });

        gender_et.addTextChangedListener(new ETChangeListener(clearGender_btn, apply_efab, editTexts, textViews));
        placement_et.addTextChangedListener(new ETChangeListener(clearPlacement_btn, apply_efab, editTexts, textViews));
        close_btn.setOnClickListener(onClickListener1);
    }

}
