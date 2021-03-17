package com.maksat.uni.bottomsheets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;

import java.util.Objects;

public class SettingsFragment extends DialogFragment  {
    public SettingsFragment(MainActivity mainClass) {
        this.mainClass=mainClass;
    }
    private MainActivity mainClass;
    private TextInputEditText fio_et, place_et, phone_et, mail_et;
    public static final String TAG = "example_dialog";
    public static SettingsFragment display(FragmentManager fragmentManager) {
        MainActivity mainClass=new MainActivity();
        SettingsFragment exampleDialog = new SettingsFragment(mainClass.mainActivity);
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }

    private static OnLogoutClickListener mListener;
    public interface OnLogoutClickListener {
        void onClick();
    }

    public void setOnLogoutClickListener(OnLogoutClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.lo_settings, container, false);
        findIDs(view);
        setInfo();
        return view;
    }

    private void findIDs(View view) {
        fio_et=view.findViewById(R.id.fio_et);
        place_et=view.findViewById(R.id.place_et);
        phone_et=view.findViewById(R.id.phone_et);
        mail_et=view.findViewById(R.id.mail_et);
    }

    private void setInfo() {
        if (Variables.profile!=null) {
            String name = Variables.profile.getFirstName() + " " + Variables.profile.getLastName();
            fio_et.setText(name);
            phone_et.setText(Variables.profile.getPhoneNumber());
            mail_et.setText(Variables.profile.getEmail());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button close_btn=view.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        MaterialButton logout=view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(v->{
            mListener.onClick();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }
}
