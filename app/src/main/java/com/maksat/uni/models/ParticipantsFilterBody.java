package com.maksat.uni.models;

public class ParticipantsFilterBody {
    public static  class sorting{
        public String field;
        public Boolean byDesc;

        public sorting(String field, Boolean byDesc) {
            this.field = field;
            this.byDesc = byDesc;
        }
    }
    public Integer acrStatusStepOneId;
    public Integer corporateAccessId;
    public Integer categoryId;
    public Integer id;
    public sorting sorting;
    public String name;
    public String companyName;
    public Integer countryId;
    public String positionName;
    public String email;
    public String comment;
    public String sphereName;
    public Integer sportId;

    public ParticipantsFilterBody(Integer acrStatusStepOneId, Integer corporateAccessId, Integer categoryId, Integer id, ParticipantsFilterBody.sorting sorting, String name, String companyName, Integer countryId, String positionName, String email, String comment, String sphereName, Integer sportId) {
        this.acrStatusStepOneId = acrStatusStepOneId;
        this.corporateAccessId = corporateAccessId;
        this.categoryId = categoryId;
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
