package com.maksat.uni;

import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.BadgeStatus;
import com.maksat.uni.models.Category;
import com.maksat.uni.models.EventProgramByDays;
import com.maksat.uni.models.Events;
import com.maksat.uni.models.Profile;
import com.maksat.uni.models.Sport;

import java.util.ArrayList;
import java.util.List;

public class Variables {
    public static String fragment="one";
    public static String ip="https://cab.rusportevents.ru";
    public static String token;
    public static Profile profile;
    public static Events.events currentEvent;
    public static List<Category> allCategories=new ArrayList<>();
    public static List<AcrStatus> acrStatuses=new ArrayList<>();
    public static List<EventProgramByDays> programByDays=new ArrayList<>();
    public static List<String> datesList=new ArrayList<>();
    public static List<Sport> sports=new ArrayList<>();
    public static List<BadgeStatus> badgeStatuses=new ArrayList<>();
    public static List<Events.events> events;


    /*
     call.enqueue(new Callback<List<Employee>>() {
        @Override
        public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
            if (response.isSuccessful()){
                  if (response.body()!=null){

                                    }
            }else {
                try {
                    if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                        Log.d("NAME_RESP_ERROR", ""+errorBody.Ru);
                    }else {
                        Log.d("NAME_RESP_ERROR", "ErrorBody is null.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Employee>> call, Throwable t) {
            Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
            Log.d("NAME_SERV_ERROR", ""+t.getMessage());
        }
    });
    */ //шаблон для метода enqueue
}
