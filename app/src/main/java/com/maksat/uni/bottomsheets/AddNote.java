package com.maksat.uni.bottomsheets;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.maksat.uni.R;

public class AddNote extends DialogFragment {


    private EditText text_et;
    private MaterialButton close_btn, clear_btn;
    private ExtendedFloatingActionButton save_btn;
    private String text, startText;

    public AddNote(String text) {
        this.text=text;
    }

    private static OnApplyClickListener mListener;


    public interface OnApplyClickListener {
        void onClick(String text);
    }

    public void setOnApplyClickListener(OnApplyClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_note, container, false);

        findIDs(view);
        setActions();
        return view;
    }

    private void findIDs(View view) {
        text_et=view.findViewById(R.id.text_et);
        close_btn=view.findViewById(R.id.filter_close_btn);
        clear_btn=view.findViewById(R.id.clear_all_btn);
        save_btn=view.findViewById(R.id.apply_efab);

        text_et.setText(text);
    }

    private void setActions() {
        if (!text.equals("")){
            clear_btn.setVisibility(View.VISIBLE);
        }
        save_btn.setOnClickListener(v -> {
            if (mListener!=null)
            mListener.onClick(text_et.getText().toString());
        });

        text_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                startText=s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!startText.equals(s.toString())){
                    save_btn.setVisibility(View.VISIBLE);
                }else {
                    save_btn.setVisibility(View.GONE);
                }
                if(!s.toString().equals("")){
                    clear_btn.setVisibility(View.VISIBLE);
                }else {
                    clear_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        close_btn.setOnClickListener(v -> dismiss());
        clear_btn.setOnClickListener(v -> text_et.setText(""));


    }
}
