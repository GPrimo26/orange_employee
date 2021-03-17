package com.maksat.uni.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.customUI.CircleImageView;
import com.maksat.uni.models.ParticipantsModel;

import java.util.List;

public class JudgesRVAdapter extends RecyclerView.Adapter<JudgesRVAdapter.ViewHolder> {
    public JudgesRVAdapter(MainActivity mainActivity, List<ParticipantsModel.item> items) {
        this.mainActivity=mainActivity;
        this.items=items;
    }

    public MainActivity mainActivity;
    private List<ParticipantsModel.item> items;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_judge, parent, false);
        return new ViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text=items.get(position).getLastNameRus().substring(0, 1)+items.get(position).getFirstNameRus().substring(0, 1);
        holder.circleImageView.setCircleBackgroundColor(Color.parseColor("#C4C4C4"));
        holder.circleImageView.setText(text, 1, "#61000000");
        text=items.get(position).getLastNameRus()+" "+items.get(position).getFirstNameRus()+" "+items.get(position).getPatronymic();
        holder.fio_tv.setText(text);
        holder.position_tv.setText(items.get(position).getCategory().getNameRus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fio_tv;
        private final TextView position_tv;
        private final CircleImageView circleImageView;
        public ViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super(itemView);
            fio_tv=itemView.findViewById(R.id.programinfo_fio_tv);
            position_tv=itemView.findViewById(R.id.programinfo_position_tv);
            circleImageView=itemView.findViewById(R.id.programinfo_fio_iv);
            circleImageView.context=mainActivity;
        }
    }
}
