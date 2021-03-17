package com.maksat.uni.customUI;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class ETClearButtonClickListener implements View.OnClickListener {

    public ETClearButtonClickListener(EditText editText, ExtendedFloatingActionButton apply_efab, List<EditText> editTexts, List<TextView> textViews) {
        this.editText = editText;
        this.apply_efab=apply_efab;
        this.editTexts=editTexts;
        this.textViews=textViews;
    }

    private final EditText editText;
    private final ExtendedFloatingActionButton apply_efab;
    private final List<EditText> editTexts;
    private final List<TextView> textViews;
    @Override
    public void onClick(View v) {
        if (editText!=null) {
            editText.setText("");
        }
            apply_efab.setVisibility(View.VISIBLE);
    }
}
