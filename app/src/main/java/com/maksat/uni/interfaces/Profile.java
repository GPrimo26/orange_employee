package com.maksat.uni.interfaces;

import com.maksat.uni.models.AuthBody;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.ForgotPassword;
import com.maksat.uni.models.PersonalData;
import com.maksat.uni.models.RecoverPassword;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Profile {

    @POST("/api/token")
    Call<AuthBody> getToken(@Body AuthBody authBody);

    @GET("/api/profile")
    Call<com.maksat.uni.models.Profile> getProfile();

    @GET("/api/events/{eventId}/participant/{participantId}/event-program")
    Call<List<EventProgram>> getProgram(@Path("eventId") Integer eventId,
                                        @Path("participantId") Integer participantId,
                                        @Query("Name") String Name,
                                        @Query("DateTimeStart") String DateTimeStart,
                                        @Query("DateTimeFinish") String DateTimeFinish,
                                        @Query("SportId") Integer SportId,
                                        @Query("Place") String Place/*,
                                        @Query("StatusId") Integer StatusId*/);
    @POST("/api/forgot-password")
    Call<ResponseBody> sendCode(@Body ForgotPassword forgotPassword);

    @POST("/api/recover-password")
    Call<ResponseBody> recoverPassword(@Body RecoverPassword recoverPassword);

    @PUT("/api/v2/events/{eventId}/participants/{participantId}/personal-data")
    Call<ResponseBody> updatePersonalData(@Path("eventId") Integer eventId,
                                          @Path("participantId") Integer participantId,
                                          @Body PersonalData personalData);
}
