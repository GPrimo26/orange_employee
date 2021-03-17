package com.maksat.uni.adapters;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.fragments.statistic.StatisticFragment;
import com.maksat.uni.fragments.statistic.StatisticParticipants;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Dashboard;
import com.maksat.uni.models.ParticipantsFilterBody;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticRVAdapter extends RecyclerView.Adapter<StatisticRVAdapter.ViewHolder> {
    public StatisticRVAdapter(MainActivity mainActivity, List<Integer> info, Dashboard dashboard, StatisticFragment statisticFragment) {
        this.mainActivity = mainActivity;
        this.info = info;
        this.dashboard = dashboard;
        this.statisticFragment=statisticFragment;
    }

    private MainActivity mainActivity;
    private List<Integer> info;
    private Dashboard dashboard;
    private StatisticFragment statisticFragment;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cv_statistic_expandable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Dashboard.categories> categories = new ArrayList<>();

        holder.categories_rv.setLayoutManager(new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false));
        Event participantsApi = Server.GetServerWithToken(Event.class, Variables.token);
        ParticipantsFilterBody participantsFilterBody = new ParticipantsFilterBody(0, null, null, null, new ParticipantsFilterBody.sorting("lastNameRus", true),
                "", "", null, "", "", "", "", null);
        Call<ParticipantsModel> call = participantsApi.getParicipantsWFilter(Variables.currentEvent.getId(), 1, 1000000, participantsFilterBody);
        if (position==0){
            final ParticipantsModel[] participantsModel = new ParticipantsModel[1];
            holder.title_tv.setText("Общее количество участников");
            categories = dashboard.getCategories();
            StatCategoriesRVAdapter adapter = new StatCategoriesRVAdapter(mainActivity, categories);
            holder.categories_rv.setAdapter(adapter);
            holder.progressBar.setVisibility(View.GONE);
            List<Dashboard.categories> finalCategories1 = categories;
            holder.button.setOnClickListener(v -> {
                statisticFragment.progressBar.setVisibility(View.VISIBLE);
                if (!call.isExecuted()) {
                    call.enqueue(new Callback<ParticipantsModel>() {
                        @Override
                        public void onResponse(Call<ParticipantsModel> call1, Response<ParticipantsModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    participantsModel[0] =response.body();
                                    int whole = dashboard.getArrive() + dashboard.getDeparture() + dashboard.getDraft() + dashboard.getApproved();
                                    StatisticFragment fragment = (StatisticFragment) mainActivity.fragmentManager.findFragmentByTag("two");
                                    if (fragment != null) {
                                        statisticFragment.progressBar.setVisibility(View.GONE);
                                        mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                                    }
                                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticParticipants("Общее количество ", mainActivity, finalCategories1, participantsModel[0], whole, statisticFragment, position), "statisticParticipants").commit();
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(mainActivity, "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                                try {
                                    if (response.errorBody() != null) {
                                        Log.d("PSTAT_RESPONSE_ERROR", "" + response.errorBody().string());
                                    } else {
                                        Log.d("PSTAT_RESPONSE_ERROR", "ErrorBody is null.");

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ParticipantsModel> call1, Throwable t) {
                            Toast.makeText(mainActivity, "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                            Log.d("SERVER_ERROR", "" + t.getMessage());
                        }
                    });
                }else {
                    int whole = dashboard.getArrive() + dashboard.getDeparture() + dashboard.getDraft() + dashboard.getApproved();
                    StatisticFragment fragment = (StatisticFragment) mainActivity.fragmentManager.findFragmentByTag("two");
                    if (fragment != null) {
                        statisticFragment.progressBar.setVisibility(View.GONE);
                        mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                    }
                    holder.progressBar.setVisibility(View.GONE);
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticParticipants("Общее количество ", mainActivity, finalCategories1, participantsModel[0], whole, statisticFragment, position), "statisticParticipants").commit();
                }
            });
        }else {
            List<Dashboard.categories> finalCategories = categories;

            StatCategoriesRVAdapter adapter = new StatCategoriesRVAdapter(mainActivity, finalCategories);

            Callback<ParticipantsModel> callback = new Callback<ParticipantsModel>()
            {
                @Override
                public void onResponse(Call<ParticipantsModel> call, @NonNull Response<ParticipantsModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<String> types = new ArrayList<>();
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                int flag = 0;
                                for (int j = 0; j < types.size(); j++) {
                                    if (response.body().getItems().get(i).getCategory().getNameRus().equals(types.get(j))) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 0) {
                                    types.add(response.body().getItems().get(i).getCategory().getNameRus());
                                }
                            }
                            for (int i = 0; i < types.size(); i++) {
                                int count = 0;
                                for (int j = 0; j < response.body().getItems().size(); j++) {
                                    if (response.body().getItems().get(j).getCategory().getNameRus().equals(types.get(i))) {
                                        count++;
                                    }
                                }
                                finalCategories.add(new Dashboard.categories(types.get(i), "", count));
                            }
                            holder.progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            holder.button.setOnClickListener(v -> {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                int whole=response.body().getItems().size();
                                StatisticFragment fragment=(StatisticFragment) mainActivity.fragmentManager.findFragmentByTag("two");
                                String title="";
                                switch (position){
                                    case 1:
                                        title="Рассматривается";
                                        break;
                                    case 2:
                                        title="Одобрено";
                                        break;
                                    case 3:
                                        title="Приехали";
                                        break;
                                    case 4:
                                        title="Уехали";
                                        break;
                                    case 5:
                                        title="Заехали";
                                        break;
                                    case 6:
                                        title="Выехали";
                                        break;
                                }
                                holder.progressBar.setVisibility(View.GONE);
                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new StatisticParticipants(title, mainActivity, finalCategories, response.body(), whole, statisticFragment, position), "statisticParticipants").commit();
                                if (fragment != null) {
                                    statisticFragment.progressBar.setVisibility(View.GONE);
                                    mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(mainActivity, "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                        try {
                            if (response.errorBody() != null) {
                                Log.d("PSTAT_RESPONSE_ERROR", "" + response.errorBody().string());
                            } else {
                                Log.d("PSTAT_RESPONSE_ERROR", "ErrorBody is null.");

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ParticipantsModel> call, Throwable t) {
                    Toast.makeText(mainActivity, "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                    Log.d("SERVER_ERROR", "" + t.getMessage());
                }
            };
            switch (position){
                case 1:
                    holder.title_tv.setText("На рассмотрении");
                    if (categories.size() == 0) {
                        participantsFilterBody.acrStatusStepOneId = 1;
                    }
                    break;
                case 2:
                    holder.title_tv.setText("Одобрено");
                    if (categories.size() == 0) {
                        participantsFilterBody.acrStatusStepOneId = 2;
                    }
                    break;
                case 3:
                    holder.title_tv.setText("Приехали");
                    break;
                case 4:
                    holder.title_tv.setText("Уехали");
                    break;
                case 5:
                    holder.title_tv.setText("Заехали");
                    break;
                case 6:
                    holder.title_tv.setText("Выехали");
                    break;
            }
            call.enqueue(callback);
            holder.categories_rv.setAdapter(adapter);
        }
        holder.number_tv.setText(String.valueOf(info.get(position)));
        holder.main_lo.setOnClickListener(v -> {
            if (holder.expandable_lo.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                holder.expandable_lo.setVisibility(View.VISIBLE);
                //TransitionManager.endTransitions(holder.cardView);
                RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(400);
                rotateAnimation.setFillAfter(true);
                holder.arrow_iv.startAnimation(rotateAnimation);
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(400);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.expandable_lo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                holder.expandable_lo.startAnimation(alphaAnimation);
                TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
//TransitionManager.endTransitions(holder.cardView);
                RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setRepeatCount(0);
                rotateAnimation.setDuration(400);
                rotateAnimation.setFillAfter(true);
                holder.arrow_iv.startAnimation(rotateAnimation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout main_lo;
        private ConstraintLayout expandable_lo;
        private CardView cardView;
        private TextView title_tv, number_tv;
        private ImageView arrow_iv;
        private RecyclerView categories_rv;
        private ProgressBar progressBar;
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_lo = itemView.findViewById(R.id.main_lo);
            title_tv = itemView.findViewById(R.id.title_tv);
            number_tv = itemView.findViewById(R.id.number_tv);
            arrow_iv = itemView.findViewById(R.id.arrow_iv);
            expandable_lo = itemView.findViewById(R.id.expandable_lo);
            cardView = itemView.findViewById(R.id.cardView);
            categories_rv = itemView.findViewById(R.id.categories_rv);
            progressBar = itemView.findViewById(R.id.progressBar3);
            button=itemView.findViewById(R.id.button);
        }
    }
}
