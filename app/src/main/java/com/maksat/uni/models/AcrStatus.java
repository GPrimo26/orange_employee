package com.maksat.uni.models;

public class AcrStatus {
    private Integer id;
    private String nameRus;
    private String nameEng;
    private String descriptionRus;
    private String descriptionEng;

    public AcrStatus(Integer id, String nameRus, String nameEng, String descriptionRus, String descriptionEng) {
        this.id = id;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.descriptionRus = descriptionRus;
        this.descriptionEng = descriptionEng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescriptionRus() {
        return descriptionRus;
    }

    public void setDescriptionRus(String descriptionRus) {
        this.descriptionRus = descriptionRus;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }
}
