package com.maksat.uni.adapters;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.customUI.CircleImageView;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.BadgeStatus;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AParticipantsRVAdapter extends RecyclerView.Adapter<AParticipantsRVAdapter.ViewHolder> {
    public AParticipantsRVAdapter(MainActivity mainActivity, List<ParticipantsModel.item> items, Integer flag) {
        this.mainActivity = mainActivity;
        this.items = items;
        this.flag = flag;
    }

    private final MainActivity mainActivity;
    private final List<ParticipantsModel.item> items;
    private final Integer flag;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(ParticipantsModel.item position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cv_participant, parent, false);
        return new ViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = items.get(position).getLastNameRus().substring(0, 1) + items.get(position).getFirstNameRus().substring(0, 1);
        holder.imageView.setCircleBackgroundColor(Color.parseColor("#C4C4C4"));
        holder.imageView.setText(text, 1, "#61000000");
        text = String.valueOf(items.get(position).getId());
        holder.id_tv.setText(text);
        holder.title_tv.setText(items.get(position).getCategory().getNameRus());
        text = items.get(position).getLastNameRus() + " " + items.get(position).getFirstNameRus();
        holder.fio_tv.setText(text);
        if (items.get(position).getAcrStatusStepOneId() != null) {
            for (AcrStatus status : Variables.acrStatuses) {
                if (items.get(position).getAcrStatusStepOneId().equals(status.getId())) {
                    holder.status_tv.setText(status.getNameRus());
                    break;
                }
            }
        } else {
            holder.status_tv.setText("Информация отсутсвует");
        }
        if (items.get(position).getBadgeStatusId() != null) {
            for (BadgeStatus status : Variables.badgeStatuses) {
                if (items.get(position).getBadgeStatusId().equals(status.id)) {
                    holder.badgeStatus_tv.setText(status.nameRus);
                    break;
                }
            }
        } else {
            holder.badgeStatus_tv.setText("Бейдж не создан");
        }
        if (flag != 1) {
            holder.status_btn.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> mListener.onItemClick(items.get(position)));
        } else {
            if (items.get(position).getAcrStatusStepOneId() != null) {
                for (AcrStatus status : Variables.acrStatuses) {
                    if (items.get(position).getAcrStatusStepOneId().equals(status.getId())) {
                        switch (status.getNameRus()) {
                            case "Одобрено":
                                holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                                break;
                            case "Отклонено":
                                holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_deny));
                                break;
                            case "Рассматривается":
                                holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                            default:
                                holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_noinfobadge));

                        }
                        break;
                    }
                }
            }else {
                holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_noinfobadge));
            }

            holder.status_btn.setOnClickListener(v -> {
                holder.status_btn.setEnabled(false);
                Event acrUpdateApi = Server.GetServerWithToken(Event.class, Variables.token);
                Call<ResponseBody> call = null;
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.status_btn, "rotationY", 0.0f, 360.0f);
                objectAnimator.setDuration(300);
                objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                objectAnimator.start();
                if (items.get(position).getAcrStatusStepOneId() != null) {
                    for (AcrStatus status : Variables.acrStatuses) {
                        if (items.get(position).getAcrStatusStepOneId().equals(status.getId())) {
                            switch (status.getNameRus()) {
                                case "Одобрено":
                                    for (AcrStatus status1 : Variables.acrStatuses) {
                                        if (status1.getNameRus().equals("Отклонено")) {
                                            items.get(position).setAcrStatusStepOneId(status1.getId());
                                            call=acrUpdateApi.updateAcrStatusOne(Variables.currentEvent.getId(), items.get(position).getId(), status1.getId());
                                            break;
                                        }
                                    }
                                    holder.status_tv.setText("Отклонено");
                                    holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_deny));
                                    break;
                                case "Рассматривается":
                                    for (AcrStatus status1 : Variables.acrStatuses) {
                                        if (status1.getNameRus().equals("Одобрено")) {
                                            items.get(position).setAcrStatusStepOneId(status1.getId());
                                            call=acrUpdateApi.updateAcrStatusOne(Variables.currentEvent.getId(), items.get(position).getId(), status1.getId());
                                            break;
                                        }
                                    }
                                    holder.status_tv.setText("Одобрено");
                                    holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                                    break;
                                case "Отклонено":
                                    for (AcrStatus status1 : Variables.acrStatuses) {
                                        if (status1.getNameRus().equals("Рассматривается")) {
                                            items.get(position).setAcrStatusStepOneId(status1.getId());
                                            call=acrUpdateApi.updateAcrStatusOne(Variables.currentEvent.getId(), items.get(position).getId(), status1.getId());
                                            break;
                                        }
                                    }
                                    holder.status_tv.setText("Рассматривается");
                                    holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                                    break;
                                default:
                                    items.get(position).setAcrStatusStepOneId(null);
                                    call=acrUpdateApi.updateAcrStatusOne(Variables.currentEvent.getId(), items.get(position).getId(), null);
                                    holder.status_tv.setText("Информация отсутсвует");
                                    holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_noinfobadge));
                                    break;
                            }
                            break;
                        }
                    }
                } else {
                    for (AcrStatus status : Variables.acrStatuses) {
                        if (status.getNameRus().equals("Рассматривается")) {
                            items.get(position).setAcrStatusStepOneId(status.getId());
                            call=acrUpdateApi.updateAcrStatusOne(Variables.currentEvent.getId(), items.get(position).getId(), status.getId());
                            break;
                        }
                    }
                    holder.status_tv.setText("Рассматривается");
                    holder.status_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                }
                if (call != null) {
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (!response.isSuccessful()){
                                try {
                                    if (response.errorBody() != null) {
                                        Gson gson = new Gson();
                                        ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                                        Toast.makeText(mainActivity, errorBody.Ru, Toast.LENGTH_SHORT).show();
                                        Log.d("ACRUPD_RESP_ERROR", ""+errorBody.Ru);
                                    }else {
                                        Log.d("ACRUPD_RESP_ERROR", "ErrorBody is null.");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            holder.status_btn.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mainActivity, "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                            holder.status_btn.setEnabled(true);
                            Log.d("ACRUPD_SERV_ERROR", ""+t.getMessage());
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imageView;
        private final TextView id_tv;
        private final TextView title_tv;
        private final TextView fio_tv;
        private final TextView status_tv;
        private final TextView badgeStatus_tv;
        private final MaterialButton status_btn;

        public ViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fio_iv);
            imageView.context = mainActivity;
            id_tv = itemView.findViewById(R.id.id_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            fio_tv = itemView.findViewById(R.id.fio_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
            badgeStatus_tv = itemView.findViewById(R.id.badge_status_tv);
            status_btn = itemView.findViewById(R.id.participant_status_btn);
        }
    }
}
