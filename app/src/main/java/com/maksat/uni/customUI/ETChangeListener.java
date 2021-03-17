package com.maksat.uni.customUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class ETChangeListener implements TextWatcher {

    public ETChangeListener(MaterialButton button, ExtendedFloatingActionButton apply_efab, List<EditText> editTexts, List<TextView> textViews) {
        this.button = button;
        this.apply_efab = apply_efab;
        this.editTexts = editTexts;
        this.textViews=textViews;
    }

    private final MaterialButton button;
    private final ExtendedFloatingActionButton apply_efab;
    private final List<EditText> editTexts;
    private final List<TextView> textViews;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals("")) {
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        int flag = 0;
        for (EditText editText : editTexts) {
            if (!editText.getText().toString().equals("")) {
                flag = 1;
            }
        }
        for (TextView textView : textViews) {
            if (!textView.getText().toString().equals("")) {
                flag = 1;
            }
        }
        if (flag != 1) {
            apply_efab.setVisibility(View.GONE);
        } else {
            apply_efab.setVisibility(View.VISIBLE);
        }
    }
}