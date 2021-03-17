package com.maksat.uni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.maksat.uni.interfaces.Profile;
import com.maksat.uni.interfaces.Server;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;
    private SharedPreferences preferences;
    private OnboardingActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences =this.getSharedPreferences("event", Context.MODE_PRIVATE);
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        activity=this;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = findViewById(R.id.img_logo);

        ViewCompat.animate(logoImageView)
                .translationY(-150).alpha(1)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

            TextView v = findViewById(R.id.tt_tv);
            ViewPropertyAnimatorCompat viewAnimator;
            viewAnimator = ViewCompat.animate(v)
                        .translationY(-50).alpha(1)
                        .setStartDelay(ITEM_DELAY  + 500)
                        .setDuration(600);
            viewAnimator.setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            });
            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        new Handler().postDelayed(() -> {
            if (!preferences.getString("token", "").equals("")){
                Variables.token=preferences.getString("token", "");
                getProfile();
            }else {
                Variables.token="none";
                changeActivity();
            }
        }, 4000);
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
                        changeActivity();
                    }
                }else {
                    try {
                        if (response.errorBody() != null) {
                            Log.d("PROFILE_RESPONSE_ERROR", ""+response.errorBody().string());
                        }else {
                            Log.d("PROFILE_RESPONSE_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Variables.token="none";
                    changeActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.maksat.uni.models.Profile> call, @NonNull Throwable t) {
                Toast.makeText(OnboardingActivity.this, "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("PROFILE_RESPONSE_ERROR", ""+t.getMessage());
                Variables.token="none";
                changeActivity();
            }
        });
    }
    private void changeActivity(){
        Intent intent=new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(OnboardingActivity.this);
    }
}
