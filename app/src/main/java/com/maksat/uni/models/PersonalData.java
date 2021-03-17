package com.maksat.uni.models;

public class PersonalData {
    public Integer titleId;
    public String firstNameRus;
    public String lastNameRus;
    public String patronymic;
    public String firstNameEng;
    public String lastNameEng;
    public Integer genderId;
    public Integer residenceId;
    public String comment;
    public Integer sportId;

    public PersonalData(Integer titleId, String firstNameRus, String lastNameRus, String patronymic,
                        String firstNameEng, String lastNameEng, Integer genderId, Integer residenceId,
                        String comment, Integer sportId) {
        this.titleId = titleId;
        this.firstNameRus = firstNameRus;
        this.lastNameRus = lastNameRus;
        this.patronymic = patronymic;
        this.firstNameEng = firstNameEng;
        this.lastNameEng = lastNameEng;
        this.genderId = genderId;
        this.residenceId = residenceId;
        this.comment = comment;
        this.sportId = sportId;
    }
}
