package com.maksat.uni.interfaces;

import com.maksat.uni.models.BadgeType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Badge {

    @GET("/api/v2/employee/events/{eventId}/badge-types")
    Call<List<BadgeType>> getBadgeTypes(@Path("eventId") Integer eventId);
}
