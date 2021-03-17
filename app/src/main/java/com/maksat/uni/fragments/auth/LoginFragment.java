package com.maksat.uni.fragments.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.interfaces.Profile;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.AuthBody;
import com.maksat.uni.models.ErrorBody;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {


    private MaterialButton login_btn;
    private TextView resetPass_btn;
    private EditText login_et, password_et;
    private MainActivity mainActivity;

    public LoginFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        findIDs(view);
        setActions();
        return view;
    }

    private void findIDs(View view) {
        login_btn=view.findViewById(R.id.login_btn);
        resetPass_btn=view.findViewById(R.id.reset_btn);
        login_et=view.findViewById(R.id.login_et);
        password_et=view.findViewById(R.id.password_et);
    }

    private void setActions(){
        login_btn.setOnClickListener(v -> {
            if (login_et.getText().toString().equals("")){
                login_et.setError("Заполните данное поле");
                return;
            }
            if (password_et.getText().toString().equals("")){
                password_et.setError("Заполните данное поле");
                return;
            }
            Profile tokenApi= Server.GetServer(Profile.class);
            AuthBody authBody=new AuthBody(login_et.getText().toString(), password_et.getText().toString());
            Call<AuthBody> call=tokenApi.getToken(authBody);
            call.enqueue(new Callback<AuthBody>() {
                @Override
                public void onResponse(@NonNull Call<AuthBody> call, @NonNull Response<AuthBody> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null) {
                            Variables.token=response.body().getAccess_token();
                            mainActivity.preferences.edit().putString("token", response.body().getAccess_token()).apply();
                            getProfile();
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
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthBody> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("TOKEN_SERVER_ERROR", ""+t.getMessage());
                }
            });
        });
        resetPass_btn.setOnClickListener(v -> {
            if(mainActivity.fragmentManager.findFragmentByTag("reset")!=null){
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("reset"))).commit();
            }else {
                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ResetPasswordFragment(mainActivity), "reset").commit();
            }
            mainActivity.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("auth"))).commit();
        });
    }
    public void getProfile(){
        Profile profileApi= Server.GetServerWithToken(Profile.class, Variables.token);
        Call<com.maksat.uni.models.Profile> call=profileApi.getProfile();
        call.enqueue(new Callback<com.maksat.uni.models.Profile>() {
            @Override
            public void onResponse(@NonNull Call<com.maksat.uni.models.Profile> call, @NonNull Response<com.maksat.uni.models.Profile> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        Variables.profile=response.body();
                        mainActivity.ChangeScreen("one");
                        mainActivity.bottomAppBar.setVisibility(View.VISIBLE);
                        if(mainActivity.fragmentManager.findFragmentByTag("auth")!=null){
                            mainActivity.fragmentManager.beginTransaction().remove(mainActivity.fragmentManager.findFragmentByTag("auth")).commit();
                        }
                    }
                }else {
                    Toast.makeText(getContext(), "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.d("PROFILE_RESPONSE_ERROR", ""+response.errorBody().string());
                        }else {
                            Log.d("PROFILE_RESPONSE_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.maksat.uni.models.Profile> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("PROFILE_RESPONSE_ERROR", ""+t.getMessage());
            }
        });
    }
}