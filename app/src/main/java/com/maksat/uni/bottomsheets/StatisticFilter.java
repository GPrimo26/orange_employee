package com.maksat.uni.bottomsheets;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.customUI.ETChangeListener;
import com.maksat.uni.customUI.ETClearButtonClickListener;
import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.Category;
import com.maksat.uni.models.ParticipantsFilterBody;

import java.util.ArrayList;
import java.util.List;

public class StatisticFilter extends DialogFragment {
    public StatisticFilter(MainActivity mainActivity, ParticipantsFilterBody filterBody, Integer mainFlag) {
        this.mainActivity=mainActivity;
        this.participantsFilterBody=filterBody;
        this.mainFlag=mainFlag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }


    private static OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onClick(ParticipantsFilterBody participantsFilterBody);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private final MainActivity mainActivity;
    public ParticipantsFilterBody participantsFilterBody;
    private EditText id_et, fio_et, organisation_et, position_et;
    private MaterialButton clearId_btn, clearOrganisation_btn, clearFio_btn, clearPosition_btn, close_btn, clear_btn, clearCategory_btn, clearStatus_btn;
    private ExtendedFloatingActionButton apply_efab;
    private TextView title_tv, category_tv, status_tv;
    private LinearLayout main_ll, rg_ll, category_ll, status_ll, id_ll, fio_ll;
    private View id_divider, fio_divider;
    private ImageView category_iv, status_iv;
    private RadioGroup radioGroup;
    private final OnClickListener onClickListener1= v -> dismiss();
    private final OnClickListener onClickListener2=new OnClickListener() {
        @Override
        public void onClick(View v) {
            title_tv.setText("Фильтр");
            clear_btn.setVisibility(View.VISIBLE);
            close_btn.setOnClickListener(onClickListener1);
            main_ll.setVisibility(View.VISIBLE);
            rg_ll.setVisibility(View.GONE);
            radioGroup.removeAllViews();
            status_tv.setText("");
            statusId=null;
        }
    },
    checkEfabListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (flag){
                case 1:
                    for (Category category:Variables.allCategories){
                        if (tempCategoryId.equals(category.getId())){
                            category_tv.setText(category.getNameRus());
                            categoryId=tempCategoryId;
                            break;
                        }
                    }
                    clearCategory_btn.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    for (AcrStatus status:Variables.acrStatuses){
                        if (tempStatusId.equals(status.getId())){
                            status_tv.setText(status.getNameRus());
                            statusId=tempStatusId;
                            break;
                        }
                    }
                    clearStatus_btn.setVisibility(View.VISIBLE);
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
    applyEfabListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!id_et.getText().toString().equals("")) {
                try {
                    id = Integer.parseInt(id_et.getText().toString());
                } catch (Exception e) {
                    id_et.setError("Поле заполнено неверно");
                    id = null;
                }
            }
            fio=fio_et.getText().toString();
            organisaton=organisation_et.getText().toString();
            position=position_et.getText().toString();
            participantsFilterBody.id=id;
            participantsFilterBody.name=fio;
            participantsFilterBody.companyName=organisaton;
            participantsFilterBody.positionName=position;
            participantsFilterBody.categoryId=categoryId;
            participantsFilterBody.acrStatusStepOneId=statusId;
            mListener.onClick(participantsFilterBody);

        }
    };
    private Integer id=null, categoryId=null, tempCategoryId=null, statusId=null, tempStatusId=null, flag, mainFlag;
    private String fio="", organisaton="", position="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_statistic_filter, container, false);

        id=participantsFilterBody.id; categoryId=participantsFilterBody.categoryId; statusId=participantsFilterBody.acrStatusStepOneId;
         fio=participantsFilterBody.name; organisaton=participantsFilterBody.companyName; position=participantsFilterBody.positionName;

        findIDs(view);

        return view;
    }

    private void findIDs(@NonNull View view) {
        id_et=view.findViewById(R.id.id_et);
        fio_et=view.findViewById(R.id.full_name_et);
        organisation_et=view.findViewById(R.id.organisation_et);
        position_et=view.findViewById(R.id.position_et);
        clearId_btn=view.findViewById(R.id.clear_id_btn);
        clearFio_btn=view.findViewById(R.id.clear_fio_btn);
        clearOrganisation_btn=view.findViewById(R.id.clear_organisation_btn);
        clearPosition_btn=view.findViewById(R.id.clear_position_btn);
        close_btn=view.findViewById(R.id.filter_close_btn);
        clear_btn=view.findViewById(R.id.clear_all_btn);
        category_ll=view.findViewById(R.id.category_ll);
        status_ll=view.findViewById(R.id.status_ll);
        apply_efab=view.findViewById(R.id.apply_efab);
        main_ll=view.findViewById(R.id.main_ll);
        rg_ll=view.findViewById(R.id.rv_ll);
        radioGroup=view.findViewById(R.id.filter_rg);
        title_tv=view.findViewById(R.id.main_title_tv);
        category_tv =view.findViewById(R.id.filter_category_tv);
        status_tv=view.findViewById(R.id.filter_status_tv);
        clearCategory_btn=view.findViewById(R.id.clear_category_btn);
        clearStatus_btn=view.findViewById(R.id.clear_status_btn);
        id_ll=view.findViewById(R.id.id_ll);
        fio_ll=view.findViewById(R.id.fio_ll);
        id_divider=view.findViewById(R.id.divider12);
        fio_divider=view.findViewById(R.id.divider15);
        category_iv=view.findViewById(R.id.category_iv);
        status_iv=view.findViewById(R.id.status_iv);
        setInfo();
        setActions();
    }

    private void setInfo() {
        if(mainFlag==1){
            id_ll.setVisibility(View.GONE);
            status_iv.setImageResource(R.drawable.ic_tag);
            status_iv.setColorFilter(Color.parseColor("#99000000"), android.graphics.PorterDuff.Mode.SRC_IN);
            category_iv.setImageResource(R.drawable.ic_users);
            category_iv.setColorFilter(Color.parseColor("#99000000"), android.graphics.PorterDuff.Mode.SRC_IN);
            fio_ll.setVisibility(View.GONE);

        }else if (mainFlag==2){
            id_ll.setVisibility(View.GONE);
            id_divider.setVisibility(View.GONE);
            fio_ll.setVisibility(View.GONE);
            fio_divider.setVisibility(View.GONE);
            category_iv.setImageResource(R.drawable.ic_users);
            category_iv.setColorFilter(Color.parseColor("#99000000"), android.graphics.PorterDuff.Mode.SRC_IN);
            status_iv.setImageResource(R.drawable.ic_tag);
            status_iv.setColorFilter(Color.parseColor("#99000000"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (id!=null){
            id_et.setText(String.valueOf(id));
            clearId_btn.setVisibility(View.VISIBLE);
        }
        if (categoryId!=null){
            for (int i=0; i<Variables.allCategories.size(); i++){
                if (categoryId.equals(Variables.allCategories.get(i).getId())){
                    category_tv.setText(Variables.allCategories.get(i).getNameRus());
                    clearCategory_btn.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        if (statusId!=null){
            for (int i=0; i<Variables.acrStatuses.size(); i++){
                if (statusId.equals(Variables.acrStatuses.get(i).getId())){
                    status_tv.setText(Variables.acrStatuses.get(i).getNameRus());
                    clearStatus_btn.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        if (!fio.equals("")){
            fio_et.setText(participantsFilterBody.name);
            clearFio_btn.setVisibility(View.VISIBLE);
        }
        if (!organisaton.equals("")){
            organisation_et.setText(participantsFilterBody.companyName);
            clearOrganisation_btn.setVisibility(View.VISIBLE);
        }
        if (!position.equals("")){
            position_et.setText(participantsFilterBody.positionName);
            clearPosition_btn.setVisibility(View.VISIBLE);
        }
    }

    private void setActions() {
        List<EditText> editTexts=new ArrayList<>();
        editTexts.add(id_et);
        editTexts.add(fio_et);
        editTexts.add(organisation_et);
        editTexts.add(position_et);

        List<TextView> textViews=new ArrayList<>();
        textViews.add(status_tv);
        textViews.add(category_tv);

        List<MaterialButton> clearButtons=new ArrayList<>();
        clearButtons.add(clearId_btn);
        clearButtons.add(clearStatus_btn);
        clearButtons.add(clearCategory_btn);
        clearButtons.add(clearFio_btn);
        clearButtons.add(clearOrganisation_btn);
        clearButtons.add(clearPosition_btn);


        clearId_btn.setOnClickListener(new ETClearButtonClickListener(id_et, apply_efab, editTexts, textViews));
        clearFio_btn.setOnClickListener(new ETClearButtonClickListener(fio_et, apply_efab, editTexts, textViews));
        clearOrganisation_btn.setOnClickListener(new ETClearButtonClickListener(organisation_et, apply_efab, editTexts, textViews));
        clearPosition_btn.setOnClickListener(new ETClearButtonClickListener(position_et, apply_efab, editTexts, textViews));
        clearCategory_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                category_tv.setText("");
                categoryId=null;
                clearCategory_btn.setVisibility(View.GONE);
                apply_efab.setVisibility(View.VISIBLE);
            }
        });
        clearStatus_btn.setOnClickListener(new ETClearButtonClickListener(null, apply_efab, editTexts, textViews){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                status_tv.setText("");
                statusId=null;
                clearStatus_btn.setVisibility(View.GONE);
                apply_efab.setVisibility(View.VISIBLE);
            }
        });
        clear_btn.setOnClickListener(v -> {
            id_et.setText("");
            category_tv.setText("");
            categoryId=null;
            status_tv.setText("");
            statusId=null;
            fio_et.setText("");
            organisation_et.setText("");
            position_et.setText("");
            for (MaterialButton materialButton: clearButtons){
                materialButton.setVisibility(View.GONE);
            }
            apply_efab.setVisibility(View.GONE);
            participantsFilterBody=new ParticipantsFilterBody(null, null, null, null, new ParticipantsFilterBody.sorting("lastNameRus", true),
                    "", "", null, "", "", "", "", null);
            apply_efab.setVisibility(View.VISIBLE);
        });

        id_et.addTextChangedListener(new ETChangeListener(clearId_btn, apply_efab, editTexts, textViews));
        fio_et.addTextChangedListener(new ETChangeListener(clearFio_btn, apply_efab, editTexts, textViews));
        organisation_et.addTextChangedListener(new ETChangeListener(clearOrganisation_btn, apply_efab, editTexts, textViews));
        position_et.addTextChangedListener(new ETChangeListener(clearPosition_btn, apply_efab, editTexts, textViews));

        category_ll.setOnClickListener(v -> {
            flag=1;
            main_ll.setVisibility(View.GONE);
            rg_ll.setVisibility(View.VISIBLE);
            title_tv.setText(getResources().getString(R.string.category));
            clear_btn.setVisibility(View.GONE);
            close_btn.setOnClickListener(onClickListener2);
            for (int i=0; i<Variables.allCategories.size(); i++){
                RadioButton radioButton=new RadioButton(mainActivity);
                radioButton.setId(i);
                radioButton.setTextColor(Color.parseColor("#DE000000"));
                radioButton.setTypeface(ResourcesCompat.getFont(mainActivity, R.font.roboto));
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                radioButton.setText(Variables.allCategories.get(i).getNameRus());
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
                tempCategoryId=Variables.allCategories.get(checkedId).getId();
                apply_efab.setVisibility(View.VISIBLE);
            });
            if (categoryId!=null){
                for (int i=0; i<Variables.allCategories.size(); i++){
                    if (categoryId.equals(Variables.allCategories.get(i).getId())){
                        ((RadioButton)radioGroup.getChildAt(i)).setChecked(true);
                        break;
                    }
                }
            }
        });
        status_ll.setOnClickListener(v -> {

            flag=2;
            main_ll.setVisibility(View.GONE);
            rg_ll.setVisibility(View.VISIBLE);
            title_tv.setText(getResources().getString(R.string.status));
            clear_btn.setVisibility(View.GONE);
            close_btn.setOnClickListener(onClickListener2);
            for (int i=0; i<Variables.acrStatuses.size(); i++){
                RadioButton radioButton=new RadioButton(mainActivity);
                radioButton.setId(i);
                radioButton.setTextColor(Color.parseColor("#DE000000"));
                radioButton.setTypeface(ResourcesCompat.getFont(mainActivity, R.font.roboto));
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                radioButton.setText(Variables.acrStatuses.get(i).getNameRus());
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
                status_tv.setText(Variables.acrStatuses.get(checkedId).getNameRus());
                apply_efab.setVisibility(View.VISIBLE);
                tempStatusId=Variables.acrStatuses.get(checkedId).getId();
            });
            if (statusId!=null){
                for (int i=0; i<Variables.acrStatuses.size(); i++){
                    if (statusId.equals(Variables.acrStatuses.get(i).getId())){
                        ((RadioButton)radioGroup.getChildAt(i)).setChecked(true);
                        break;
                    }
                }
            }
        });

        apply_efab.setOnClickListener(applyEfabListener);

        close_btn.setOnClickListener(onClickListener1);
    }
}
