package com.maksat.uni.interfaces;

import com.maksat.uni.models.Dashboard;
import com.maksat.uni.models.EventProgram;
import com.maksat.uni.models.Events;
import com.maksat.uni.models.ParticipantsEventProgramFilterBody;
import com.maksat.uni.models.ParticipantsFilterBody;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.UpdateParticipantModel;
import com.maksat.uni.models.Zone;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Event {

    @GET("/api/events")
    Call<Events> getEvents();

    @GET("/api/events/{eventId}/event-program")
    Call<List<EventProgram>> getEventProgram(@Path("eventId") Integer id);

    @GET("/api/events/{eventId}/event-program")
    Call<List<EventProgram>> getEventProgramWithFilter(@Path("eventId") Integer id,
                                                       @Query("Name") String name,
                                                       @Query("DateTimeStart") String dateTimeStart,
                                                       @Query("DateTimeFinish") String dateTimeFinish,
                                                       @Query("SportId") Integer sportId,
                                                       @Query("Place") String place,
                                                       @Query("SameSport") Boolean sameSport/*
                                                       @Query("StatusId") Integer statusID*/);

    @GET("/api/events/{eventId}/dashboard")
    Call<Dashboard> getDashboard(@Path("eventId") Integer id);

    @POST("/api/v2/events/{eventId}/participants")
    Call<ParticipantsModel> getParicipantsWFilter(@Path("eventId") Integer eventId,
                                                  @Query("PageNumber") Integer pageNumber,
                                                  @Query("ItemOnPage") Integer itemOnPage,
                                                  @Body ParticipantsFilterBody filterBody);

    @PUT("/api/v2/events/{eventId}/participants/{participantId}")
    Call<ParticipantsModel.item> setPhoto(@Path("eventId") Integer eventId,
                                @Path("participantId") Integer participantId,
                                @Body UpdateParticipantModel item);

    @POST("/api/v2/events/{eventId}/participants-with-event-program")
    Call<ParticipantsModel> getParticipantsWithEventProgram(@Path("eventId") Integer eventId,
                                                                  @Query("PageNumber") Integer pageNumber,
                                                                  @Query("ItemOnPage") Integer itemOnPage,
                                                                  @Body ParticipantsEventProgramFilterBody filterBody);

    @GET("/api/events/{eventId}/participants/{participantId}/zones")
    Call<List<Zone>> getZonesForParticipant(@Path("eventId") Integer eventId,
                                            @Path("participantId") Integer participantId);

    @PUT("/api/acr/events/{eventId}/participants/{participantId}/acr-status-one")
    Call<ResponseBody> updateAcrStatusOne(@Path("eventId") Integer eventId,
                                          @Path("participantId") Integer participantId,
                                          @Body Integer newStatus);
}
