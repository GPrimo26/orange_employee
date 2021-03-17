package com.maksat.uni.fragments.contacts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.models.Employee;

import java.util.Objects;

public class ContactInfo extends BaseFragment {
    public ContactInfo(MainActivity mainActivity, Employee employee) {
        this.mainActivity=mainActivity;
        this.employee=employee;
    }

    private MainActivity mainActivity;
    private Employee employee;
    private TextView title_tv, fio_tv, position_tv, phone_tv, email_tv;
    private MaterialButton sendMessage_btn, copyPhone_btn, copyEmail_btn, back_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact_info, container, false);
        findIDs(view);
        setInfo();
        return view;
    }

    private void findIDs(View view) {
        title_tv=view.findViewById(R.id.title_tv);
        fio_tv=view.findViewById(R.id.fio_tv);
        position_tv=view.findViewById(R.id.position_tv);
        phone_tv=view.findViewById(R.id.phone_tv);
        email_tv=view.findViewById(R.id.email_tv);
        sendMessage_btn=view.findViewById(R.id.send_message_btn);
        copyPhone_btn=view.findViewById(R.id.copy_pnumber_btn);
        copyEmail_btn=view.findViewById(R.id.copy_email_btn);
        back_btn=view.findViewById(R.id.back_btn);
    }

    private void setInfo(){
        StringBuilder text =new StringBuilder();
        for (int i=0; i<employee.getRoles().size(); i++){
            if (i!=(employee.getRoles().size()-1)){
                text.append(employee.getRoles().get(i)).append(", ");
            }else {
                text.append(employee.getRoles().get(i));
            }
        }
        title_tv.setText(text);
        String t=employee.getLastName();
        if (t==null){
            t="";
        }
        if (employee.getFistName()==null){
            t+="";
        }else {
            t+=" "+employee.getFistName();
        }
        fio_tv.setText(t);
        position_tv.setText(employee.getPositionName());
        phone_tv.setText(employee.getPhoneNumber());
        email_tv.setText(employee.getEmail());
        ClipboardManager clipboard = (ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        copyPhone_btn.setOnClickListener(v -> {
            ClipData clip = ClipData.newPlainText("email", phone_tv.getText().toString());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_LONG).show();
        });
        copyEmail_btn.setOnClickListener(v -> {
            ClipData clip = ClipData.newPlainText("email", email_tv.getText().toString());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getContext(), "Скопировано", Toast.LENGTH_LONG).show();
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("contactInfo"))).commit();
                /*if (mainActivity.fragmentManager.findFragmentByTag("five")!=null){
                    mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("five"))).commit();
                }else {
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ContactsFragment(mainActivity), "five").commit();
                }*/
                mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("contactInfo"))).commit();
            }
        });
    }
}
