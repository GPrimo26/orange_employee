package com.maksat.uni.adapters;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.models.EventProgram;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgramRVAdapter extends RecyclerView.Adapter<ProgramRVAdapter.ViewHolder> {

    public ProgramRVAdapter(MainActivity mainActivity, List<EventProgram> eventProgram, int flag) {
        this.mainActivity = mainActivity;
        this.eventProgram = eventProgram;
        this.flag=flag;
    }


    private MainActivity mainActivity;
    public List<EventProgram> eventProgram;
    private int position, flag;


    private static OnItemClickListener mListener;
    private static OnParticipantClickListener mListener2;
    public interface OnItemClickListener {
        void onItemClick(EventProgram program);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnParticipantClickListener {
        void onItemClick(String nameRus);
    }

    public void setOnParticipantsClickListener(OnParticipantClickListener listener) {
        mListener2 = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cv_competition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text ="", start=eventProgram.get(position).getDateTimeStart(), end=eventProgram.get(position).getDateTimeFinish();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru"));
        try {
            Date date = format.parse(start);
            format.applyPattern("HH:mm");
            if (date != null) {
                text = format.format(date);

            }
            format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
            date=format.parse(end);
            format.applyPattern("HH:mm");
            if (date != null) {
                text+=" - "+format.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (eventProgram.get(position).getPlaceRus()!=null) {
            if (!eventProgram.get(position).getPlaceRus().equals("")) {
                text += " " + mainActivity.getResources().getString(R.string.dot) + " " + eventProgram.get(position).getPlaceRus();
            }
        }
        holder.time_tv.setText(text);
        if (eventProgram.get(position).getSportNameRus()!=null){
            if (!eventProgram.get(position).getSportNameRus().equals("")){
                text=eventProgram.get(position).getSportNameRus();
                //тут нужно добавить в text точку, если пол указан.
            }else {
                text="";
            }
        }else {
            text="";
        }
        holder.name_tv.setText(eventProgram.get(position).getNameRus());
        holder.sport_tv.setText(text);
        if (flag==0) {
            holder.participants_btn.setOnClickListener(v -> mListener2.onItemClick(eventProgram.get(position).getNameRus()));
        }else {
            if (eventProgram.get(position).getStatusId()!=null)
                switch (eventProgram.get(position).getStatusId()){
                    case 2:
                        holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                        break;
                    case 3:
                        holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_deny));
                        break;
                    case 1:
                    default:
                        holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                }
            holder.participants_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.participants_btn, "rotationY", 0.0f, 360.0f);
                    objectAnimator.setDuration(300);
                    objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    objectAnimator.start();
                    if (eventProgram.get(position).getStatusId()!=null) {
                        switch (eventProgram.get(position).getStatusId()) {
                            case 2:
                                eventProgram.get(position).setStatusId(3);
                                holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_wait));
                                break;
                            case 3:
                                eventProgram.get(position).setStatusId(1);
                                holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                                break;
                            case 1:
                            default:
                                eventProgram.get(position).setStatusId(2);
                                holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_deny));
                        }
                    }else {
                        eventProgram.get(position).setStatusId(1);
                        holder.participants_btn.setIcon(ContextCompat.getDrawable(mainActivity, R.drawable.ic_baseline_check_24));
                    }
                }
            });
        }
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(eventProgram.get(position)));
    }

    @Override
    public int getItemCount() {
        return eventProgram.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time_tv, name_tv, sport_tv;
        private MaterialButton participants_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_tv=itemView.findViewById(R.id.program_time_tv);
            name_tv=itemView.findViewById(R.id.program_name_tv);
            sport_tv=itemView.findViewById(R.id.program_sport_tv);
            participants_btn=itemView.findViewById(R.id.program_participants_btn);
        }
    }
}
