package com.maksat.uni.models;

public class ProgramFilterBody {
    public Integer categoryId;
    public Integer statusId;
    public String dateStart;
    public String dateFinish;
    public Integer sportId;
    public Integer genderId;
    public String placement;

    public ProgramFilterBody(Integer categoryId, Integer statusId, String dateStart, String dateFinish, Integer sportId, Integer genderId, String placement) {
        this.categoryId = categoryId;
        this.statusId = statusId;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.sportId = sportId;
        this.genderId = genderId;
        this.placement = placement;
    }
}
