package com.maksat.uni.models;

public class Sport {
    public Integer sportId;
    public String sportNameRus;
    public String sportNameEng;

    public Sport(Integer sportId, String sportNameRus, String sportNameEng) {
        this.sportId = sportId;
        this.sportNameRus = sportNameRus;
        this.sportNameEng = sportNameEng;
    }
}
