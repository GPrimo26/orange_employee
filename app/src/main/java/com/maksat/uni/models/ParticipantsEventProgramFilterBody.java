package com.maksat.uni.models;

public class ParticipantsEventProgramFilterBody {
    public Integer eventProgramId;
    public Integer id;
    public ParticipantsFilterBody.sorting sorting;
    public String name;
    public String companyName;
    public Integer countryId;
    public String positionName;
    public String email;
    public String comment;
    public String sphereName;
    public Integer sportId;

    public ParticipantsEventProgramFilterBody(Integer eventProgramId, Integer id, ParticipantsFilterBody.sorting sorting, String name, String companyName, Integer countryId, String positionName, String email, String comment, String sphereName, Integer sportId) {
        this.eventProgramId = eventProgramId;
        this.id = id;
        this.sorting = sorting;
        this.name = name;
        this.companyName = companyName;
        this.countryId = countryId;
        this.positionName = positionName;
        this.email = email;
        this.comment = comment;
        this.sphereName = sphereName;
        this.sportId = sportId;
    }

}
