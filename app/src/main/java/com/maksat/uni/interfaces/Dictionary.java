package com.maksat.uni.interfaces;

import com.maksat.uni.models.AcrStatus;
import com.maksat.uni.models.BadgeStatus;
import com.maksat.uni.models.Category;
import com.maksat.uni.models.Sport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Dictionary {

    @GET("/api/statuses/acr")
    Call<List<AcrStatus>> getAcrStatuses();

    @GET("/api/events/{eventId}/categories")
    Call<List<Category>> getCategories(@Path("eventId") Integer id);

    @GET("/api/dictionary/sports")
    Call<List<Sport>> getSports();

    @GET("/api/statuses/badge")
    Call<List<BadgeStatus>> getBadgeStatuses();
}
