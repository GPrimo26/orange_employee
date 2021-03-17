package com.maksat.uni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.fragments.contacts.ContactsFragment;
import com.maksat.uni.fragments.participants.ParticipantsFragment;
import com.maksat.uni.fragments.statistic.StatisticParticipants;
import com.maksat.uni.models.Employee;
import com.maksat.uni.models.ParticipantsByAlphabet;
import com.maksat.uni.models.ParticipantsModel;

import java.util.ArrayList;
import java.util.List;

public class AlphabetRVAdapter extends RecyclerView.Adapter<AlphabetRVAdapter.ViewHolder>  {
    public AlphabetRVAdapter(MainActivity mainActivity, List<ParticipantsByAlphabet> alphabets, ParticipantsFragment participantsFragment, StatisticParticipants statisticParticipants, ContactsFragment contactsFragment, List<ParticipantsModel.item> items, Integer flag) {
        this.mainActivity=mainActivity;
        this.alphabets=alphabets;
        this.participantsFragment=participantsFragment;
        this.statisticParticipants=statisticParticipants;
        this.contactsFragment=contactsFragment;
        this.allItems=items;
        this.flag=flag;
    }

    private MainActivity mainActivity;
    private List<ParticipantsByAlphabet> alphabets;
    private ParticipantsFragment participantsFragment;
    private StatisticParticipants statisticParticipants;
    private ContactsFragment contactsFragment;
    private List<ParticipantsModel.item> allItems;
    private Integer flag;

    private static OnItemClickListener mListenerPasrticipant, mListenerContact;


    public interface OnItemClickListener {
        void onParticipantClick(ParticipantsModel.item item);
        void onContactClick(Employee employee);
    }

    public void setOnParticipantClickListener(OnItemClickListener listener) {
        mListenerPasrticipant = listener;
    }

    public void setOnContactClickListener(OnItemClickListener listener){
        mListenerContact=listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mainActivity).inflate(R.layout.cv_alphabet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (alphabets.get(position)!=null) {
            String letter = String.valueOf(alphabets.get(position).getLetter());
            holder.letter_tv.setText(letter);
            if (alphabets.get(position).getItems()!=null) {
                AParticipantsRVAdapter adapter;
                if (flag!=1) {
                    adapter = new AParticipantsRVAdapter(mainActivity, alphabets.get(position).getItems(), 0);
                }else {
                    adapter= new AParticipantsRVAdapter(mainActivity, alphabets.get(position).getItems(), 1);
                }
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false));
                holder.recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(item -> {
                    if (mListenerPasrticipant!=null)
                    mListenerPasrticipant.onParticipantClick(item);
                });
            }
            else {
                AContatsRVAdapter adapter = new AContatsRVAdapter(mainActivity, alphabets.get(position).getEmployees());
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false));
                holder.recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(employee -> {
                    if (mListenerContact!=null)
                    mListenerContact.onContactClick(employee);
                });

            }
            if (participantsFragment!=null) {
                participantsFragment.progressBar.setVisibility(View.GONE);
            }else if (statisticParticipants!=null){
                statisticParticipants.progressBar.setVisibility(View.GONE);
            }else if (contactsFragment!=null){
                contactsFragment.progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return alphabets.size();
    }

    public void setItems(List<ParticipantsByAlphabet> alph){
        alphabets=new ArrayList<>(alph);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView letter_tv;
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letter_tv=itemView.findViewById(R.id.letter_tv);
            recyclerView=itemView.findViewById(R.id.participants_rv);
        }
    }
}
