package com.maksat.uni.fragments.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.interfaces.Profile;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ForgotPassword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }


    private MainActivity mainActivity;
    private TextView login_btn;
    private EditText email_et, login_et;
    private MaterialButton sendCode_btn;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        findIDs(view);
        setActions();
        return view;
    }

    private void findIDs(View view){
        login_btn=view.findViewById(R.id.login_btn);
        email_et=view.findViewById(R.id.email_et);
        login_et=view.findViewById(R.id.login_et);
        sendCode_btn=view.findViewById(R.id.reset_btn);
        progressBar=view.findViewById(R.id.resetpass_pb);
    }

    private void setActions(){
        login_btn.setOnClickListener(v -> {
            if(mainActivity.fragmentManager.findFragmentByTag("auth")!=null){
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("auth"))).commit();
            }else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ResetPasswordFragment(mainActivity), "auth").commit();
            }
            if(mainActivity.fragmentManager.findFragmentByTag("reset")!=null){
                mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("reset"))).commit();
                mainActivity.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("reset"))).commit();
            }
        });
        sendCode_btn.setOnClickListener(v->{
            List<EditText> editTexts = new ArrayList<>();
            editTexts.add(login_et);
            editTexts.add(email_et);
            boolean flag = false;
            for (EditText editText: editTexts) {
                if (editText.getText().toString().equals("")) {
                    editText.setError("Заполните данное поле");
                    flag =true;
                }
            }
            if (flag){
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            doCall();
        });
    }

    private void doCall() {
        Profile recoverPassApi = Server.GetServer(Profile.class);
        ForgotPassword fpBody = new ForgotPassword(email_et.getText().toString(), login_et.getText().toString(), 1);
        Call<ResponseBody> call = recoverPassApi.sendCode(fpBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Мы выслали вам письмо на электронную почту.", Toast.LENGTH_LONG).show();
                        login_btn.performClick();
                    }
                }else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            Log.d("FPASS_RESP_ERROR", ""+errorBody.Ru);
                        }else {
                            Log.d("FPASS_RESP_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("FPASS_SERV_ERROR", ""+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}