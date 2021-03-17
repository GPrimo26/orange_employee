package com.maksat.uni.models;

public class UpdateParticipantModel {
    public static class Photo {
        public String filename;
        public String path;
        public Boolean isNew;
        public String content;

        public Photo(String filename, String path, Boolean isNew, String content) {
            this.filename = filename;
            this.path = path;
            this.isNew = isNew;
            this.content = content;
        }
    }
    public Boolean needHotel;
    public String checkInDateTime;
    public String checkOutDateTime;
    public Integer hotelId;
    public Integer roomId;
    public String phoneNumber;
    public String email;
    public String webSite;
    public String arrivalStation;
    public String arrivalDateTime;
    public String arrivalFlightInfo;
    public String departureStation;
    public String departureDateTime;
    public String departureFlightInfo;
    public Integer languageId;
    public Integer categoryId;
    public Integer titleId;
    public String firstNameRus;
    public String lastNameRus;
    public String patronymic;
    public String firstNameEng;
    public String lastNameEng;
    public Integer genderId;
    public Integer residenceId;
    public Photo photo;
    public String comment;
    public Integer sportId;
    public String sportCategoryName;
    public Integer regionId;
    public Integer citizenshipId;
    public String birthday;
    public String passportNumber;
    public String issuedBy;
    public String issuedDate;
    public String validUntilDate;
    public String birthPlace;
    public Boolean visa;
    public ParticipantsModel.item.company company;

    public UpdateParticipantModel(Boolean needHotel, String checkInDateTime, String checkOutDateTime, Integer hotelId, Integer roomId, String phoneNumber, String email, String webSite, String arrivalStation,
                                  String arrivalDateTime, String arrivalFlightInfo, String departureStation, String departureDateTime, String departureFlightInfo, Integer languageId, Integer categoryId,
                                  Integer titleId, String firstNameRus, String lastNameRus, String patronymic, String firstNameEng, String lastNameEng, Integer genderId, Integer residenceId, Photo photo,
                                  String comment, Integer sportId, String sportCategoryName, Integer regionId, Integer citizenshipId, String birthday, String passportNumber, String issuedBy, String issuedDate,
                                  String validUntilDate, String birthPlace, Boolean visa, ParticipantsModel.item.company company) {
        this.needHotel = needHotel;
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.webSite = webSite;
        this.arrivalStation = arrivalStation;
        this.arrivalDateTime = arrivalDateTime;
        this.arrivalFlightInfo = arrivalFlightInfo;
        this.departureStation = departureStation;
        this.departureDateTime = departureDateTime;
        this.departureFlightInfo = departureFlightInfo;
        this.languageId = languageId;
        this.categoryId = categoryId;
        this.titleId = titleId;
        this.firstNameRus = firstNameRus;
        this.lastNameRus = lastNameRus;
        this.patronymic = patronymic;
        this.firstNameEng = firstNameEng;
        this.lastNameEng = lastNameEng;
        this.genderId = genderId;
        this.residenceId = residenceId;
        this.photo = photo;
        this.comment = comment;
        this.sportId = sportId;
        this.sportCategoryName = sportCategoryName;
        this.regionId = regionId;
        this.citizenshipId = citizenshipId;
        this.birthday = birthday;
        this.passportNumber = passportNumber;
        this.issuedBy = issuedBy;
        this.issuedDate = issuedDate;
        this.validUntilDate = validUntilDate;
        this.birthPlace = birthPlace;
        this.visa = visa;
        this.company = company;
    }
}
