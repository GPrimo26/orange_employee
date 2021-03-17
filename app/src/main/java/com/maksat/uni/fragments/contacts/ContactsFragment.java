package com.maksat.uni.fragments.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.AlphabetRVAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.interfaces.Contacts;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.Employee;
import com.maksat.uni.models.ParticipantsByAlphabet;
import com.maksat.uni.models.ParticipantsModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsFragment extends BaseFragment {
    public ContactsFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    private final MainActivity mainActivity;
    private RecyclerView alphabet_rv;
    private Toolbar toolbar;
    public ProgressBar progressBar;
    private List<Employee> employees;
    private List<ParticipantsByAlphabet> alphabets;
    private ContactsFragment contactsFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_participants, container, false);
        findIDs(view);
        toolbar.setTitle(getResources().getString(R.string.contacts));
        contactsFragment=this;
        doCalls();
        return view;
    }

    private void findIDs(@NonNull View view) {
        alphabet_rv=view.findViewById(R.id.alphabet_rv);
        toolbar=view.findViewById(R.id.toolbar);
        progressBar=view.findViewById(R.id.progressBar2);
    }

    private void doCalls() {
        Contacts contactsApi= Server.GetServerWithToken(Contacts.class, Variables.token);
        Call<List<Employee>> call=contactsApi.getEmployees(null);
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        employees=response.body();
                        alphabets=new ArrayList<>();
                        for(char letter = 'А'; letter<='Я'; letter++){
                            List<Employee> tempItems=new ArrayList<>();
                            for (int i=0; i<employees.size(); i++){
                                if (employees.get(i).getLastName().substring(0, 1).equals(String.valueOf(letter))){
                                    tempItems.add(employees.get(i));
                                }
                            }
                            if (tempItems.size()!=0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, null, tempItems));
                            }
                        }
                        for(char letter = 'а'; letter<='я'; letter++){
                            List<Employee> tempItems=new ArrayList<>();
                            for (int i=0; i<employees.size(); i++){
                                if (employees.get(i).getLastName().charAt(0)==letter){
                                    tempItems.add(employees.get(i));
                                }
                            }
                            if (tempItems.size()!=0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, null, tempItems));
                            }                        }
                        for(char letter = 'A'; letter<='Z'; letter++){
                            List<Employee> tempItems=new ArrayList<>();
                            for (int i=0; i<employees.size(); i++){
                                if (employees.get(i).getLastName().charAt(0)==letter){
                                    tempItems.add(employees.get(i));
                                }
                            }
                            if (tempItems.size()!=0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, null, tempItems));
                            }                        }
                        for(char letter = 'a'; letter<='z'; letter++){
                            List<Employee> tempItems=new ArrayList<>();
                            for (int i=0; i<employees.size(); i++){
                                if (employees.get(i).getLastName().charAt(0)==letter){
                                    tempItems.add(employees.get(i));
                                }
                            }
                            if (tempItems.size()!=0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, null, employees));
                            }                        }
                        for(char letter = '0'; letter<='9'; letter++){
                            List<Employee> tempItems=new ArrayList<>();
                            for (int i=0; i<employees.size(); i++){
                                if (employees.get(i).getLastName().charAt(0)==letter){
                                    tempItems.add(employees.get(i));
                                }
                            }
                            if (tempItems.size()!=0) {
                                alphabets.add(new ParticipantsByAlphabet(letter, null, tempItems));
                            }
                        }
                        AlphabetRVAdapter adapter=new AlphabetRVAdapter(mainActivity, alphabets, null, null, contactsFragment, new ArrayList<>(), 0);
                        alphabet_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        alphabet_rv.setAdapter(adapter);
                        adapter.setOnContactClickListener(new AlphabetRVAdapter.OnItemClickListener() {
                            @Override
                            public void onParticipantClick(ParticipantsModel.item item) {

                            }

                            @Override
                            public void onContactClick(Employee employee) {
                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ContactInfo(mainActivity, employee), "contactInfo").commit();
                            }
                        });
                        mainActivity.search_btn.setOnClickListener(v -> {
                            if (mainActivity.fragmentManager.findFragmentByTag("contactsSearch")!=null){
                                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("contactsSearch"))).commit();
                            }else {
                                mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ContactsSearch(mainActivity, alphabets)).commit();
                            }
                        });
                    }
                }else {
                    Toast.makeText(getContext(), "При загрузке данных произошла ошибка.", Toast.LENGTH_SHORT).show();
                    try {
                        if (response.errorBody() != null) {
                            Log.d("CONT_SERV_ERROR", ""+response.errorBody().string());
                        }else {
                            Log.d("CONT_SERV_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("CONT_SERV_ERROR", ""+t.getMessage());
            }
        });
    }
}
