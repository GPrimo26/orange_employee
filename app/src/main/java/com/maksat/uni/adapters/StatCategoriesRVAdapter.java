package com.maksat.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.models.Dashboard;

import java.util.List;

public class StatCategoriesRVAdapter extends RecyclerView.Adapter<StatCategoriesRVAdapter.ViewHolder> {
    public StatCategoriesRVAdapter(MainActivity mainActivity, List<Dashboard.categories> categories) {
        this.mainActivity=mainActivity;
        this.categories=categories;
    }



    private MainActivity mainActivity;
    private List<Dashboard.categories> categories;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.cv_category_count, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.number_tv.setText(String.valueOf(categories.get(position).getParticipantCount()));
        holder.title_tv.setText(categories.get(position).getItemNameRus());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView number_tv, title_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number_tv=itemView.findViewById(R.id.number_tv);
            title_tv=itemView.findViewById(R.id.title_tv);
        }
    }
}
