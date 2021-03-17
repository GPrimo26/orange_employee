package com.maksat.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;

public class CategoriesStatusesRVAdapter extends RecyclerView.Adapter<CategoriesStatusesRVAdapter.ViewHolder> {
    public CategoriesStatusesRVAdapter(MainActivity mainActivity, int i) {
        this.mainActivity=mainActivity;
        this.flag=i;
    }

    private MainActivity mainActivity;
    private Integer flag;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cv_filter_checkboxes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (flag){
            case 1:
                holder.checkBox.setText(Variables.allCategories.get(position).getNameRus());
                break;
            case 2:
                holder.checkBox.setText(Variables.acrStatuses.get(position).getNameRus());
                break;
        }
    }

    @Override
    public int getItemCount() {
       switch (flag){
           case 1:
               return Variables.allCategories.size();
           case 2:
               return Variables.acrStatuses.size();
           default:
               return 0;
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
