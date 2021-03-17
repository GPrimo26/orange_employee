package com.maksat.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.models.EventProgramByDays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class HomeTodayTasksRVAdapter extends RecyclerView.Adapter<HomeTodayTasksRVAdapter.ViewHolder> {


    public HomeTodayTasksRVAdapter(MainActivity mainActivity, EventProgramByDays eventProgramByDays) {
        this.mainActivity= mainActivity;
        this.eventProgramByDays=eventProgramByDays;
    }

    private final MainActivity mainActivity;
    private final EventProgramByDays eventProgramByDays;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
        View view = layoutInflater.inflate(R.layout.cv_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position==0){
            holder.circle_iv.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_yellow_circle));
        }else {
            holder.circle_iv.setImageDrawable(ContextCompat.getDrawable(mainActivity, R.drawable.ic_gray_circle));
        }
        holder.title_tv.setText(eventProgramByDays.getEventProgram().get(position).getNameRus());
        holder.place_tv.setText(eventProgramByDays.getEventProgram().get(position).getPlaceRus());
        try {
            String time=new SimpleDateFormat("HH:mm", new Locale("ru")).format(Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("ru")).parse(eventProgramByDays.getEventProgram().get(position).getDateTimeStart())));
            holder.time_tv.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventProgramByDays.getEventProgram().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView circle_iv;
        private final TextView title_tv;
        private final TextView place_tv;
        private final TextView time_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle_iv=itemView.findViewById(R.id.circle_iv);
            title_tv=itemView.findViewById(R.id.title_tv);
            place_tv=itemView.findViewById(R.id.descr_tv);
            time_tv=itemView.findViewById(R.id.time_tv);
        }
    }
}
