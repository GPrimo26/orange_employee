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
import com.maksat.uni.models.Employee;

import java.util.List;

public class AContatsRVAdapter extends RecyclerView.Adapter<AContatsRVAdapter.ViewHolder> {
    public AContatsRVAdapter(MainActivity mainActivity, List<Employee> employees) {
        this.mainActivity=mainActivity;
        this.employees=employees;
    }

    private MainActivity mainActivity;
    private List<Employee> employees;

    private static OnItemClickListener mListener;
    public interface OnItemClickListener {

        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cv_contact, parent, false);
        return new ViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StringBuilder text= new StringBuilder(employees.get(position).getLastName().substring(0, 1) + employees.get(position).getLastName().substring(0, 1));
        holder.imageView.setCircleBackgroundColor(Color.parseColor("#C4C4C4"));
        holder.imageView.setText(text.toString(), 1, "#61000000");
        for (int i=0; i<employees.get(position).getRoles().size(); i++){
            if (i!=(employees.get(position).getRoles().size()-1)){
                text.append(employees.get(position).getRoles().get(i)).append(", ");
            }else {
                text.append(employees.get(position).getRoles().get(i));
            }
        }
        holder.title_tv.setText(text);
        String t=employees.get(position).getLastName();
        if (t==null){
            t="";
        }
        if (employees.get(position).getFistName()==null){
            t+="";
        }else {
            t+=" "+employees.get(position).getFistName();
        }
        holder.fio_tv.setText(t);
        holder.position_tv.setText(employees.get(position).getPositionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(employees.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView title_tv, fio_tv, position_tv;
        public ViewHolder(@NonNull View itemView, MainActivity mainActivity) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fio_iv);
            imageView.context=mainActivity;
            title_tv=itemView.findViewById(R.id.title_tv);
            fio_tv=itemView.findViewById(R.id.fio_tv);
            position_tv=itemView.findViewById(R.id.position_tv);
        }
    }
}
