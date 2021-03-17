package com.maksat.uni.models;

public class BadgeStatus {
    public Integer id;
    public String nameRus;
    public String nameEng;
    public String descriptionRus;
    public String descriptionEng;

    public BadgeStatus(Integer id, String nameRus, String nameEng, String descriptionRus, String descriptionEng) {
        this.id = id;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.descriptionRus = descriptionRus;
        this.descriptionEng = descriptionEng;
    }
}
