package com.maksat.uni.models;

import java.util.List;

public class EventProgram {
    public static class extendedInfo{
        private Integer registeredCount;
        private Integer approvedRegisteredCount;
        private Integer draftRegisteredCount;
        private Integer rejectedRegisteredCount;
        private Integer visitedCount;
        private Integer notVisitedCount;

        public extendedInfo(Integer registeredCount, Integer approvedRegisteredCount, Integer draftRegisteredCount,
                            Integer rejectedRegisteredCount, Integer visitedCount, Integer notVisitedCount) {
            this.registeredCount = registeredCount;
            this.approvedRegisteredCount = approvedRegisteredCount;
            this.draftRegisteredCount = draftRegisteredCount;
            this.rejectedRegisteredCount = rejectedRegisteredCount;
            this.visitedCount = visitedCount;
            this.notVisitedCount = notVisitedCount;
        }

        public Integer getRegisteredCount() {
            return registeredCount;
        }

        public void setRegisteredCount(Integer registeredCount) {
            this.registeredCount = registeredCount;
        }

        public Integer getApprovedRegisteredCount() {
            return approvedRegisteredCount;
        }

        public void setApprovedRegisteredCount(Integer approvedRegisteredCount) {
            this.approvedRegisteredCount = approvedRegisteredCount;
        }

        public Integer getDraftRegisteredCount() {
            return draftRegisteredCount;
        }

        public void setDraftRegisteredCount(Integer draftRegisteredCount) {
            this.draftRegisteredCount = draftRegisteredCount;
        }

        public Integer getRejectedRegisteredCount() {
            return rejectedRegisteredCount;
        }

        public void setRejectedRegisteredCount(Integer rejectedRegisteredCount) {
            this.rejectedRegisteredCount = rejectedRegisteredCount;
        }

        public Integer getVisitedCount() {
            return visitedCount;
        }

        public void setVisitedCount(Integer visitedCount) {
            this.visitedCount = visitedCount;
        }

        public Integer getNotVisitedCount() {
            return notVisitedCount;
        }

        public void setNotVisitedCount(Integer notVisitedCount) {
            this.notVisitedCount = notVisitedCount;
        }
    }
    public static class speakers{
        private Integer id;
        private String firstNameRus;
        private String lastNameRus;
        private String patronymicRus;
        private String firstNameEng;
        private String lastNameEng;
        private String firstNameAra;
        private String lastNameAra;
        private String patronymicAra;
        private String companyRus;
        private String companyEng;
        private String companyAra;
        private String positionRus;
        private String positionAra;
        private String photo;

        public speakers(Integer id, String firstNameRus, String lastNameRus, String patronymicRus,
                        String firstNameEng, String lastNameEng, String firstNameAra, String lastNameAra,
                        String patronymicAra, String companyRus, String companyEng, String companyAra,
                        String positionRus, String positionAra, String photo) {
            this.id = id;
            this.firstNameRus = firstNameRus;
            this.lastNameRus = lastNameRus;
            this.patronymicRus = patronymicRus;
            this.firstNameEng = firstNameEng;
            this.lastNameEng = lastNameEng;
            this.firstNameAra = firstNameAra;
            this.lastNameAra = lastNameAra;
            this.patronymicAra = patronymicAra;
            this.companyRus = companyRus;
            this.companyEng = companyEng;
            this.companyAra = companyAra;
            this.positionRus = positionRus;
            this.positionAra = positionAra;
            this.photo = photo;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstNameRus() {
            return firstNameRus;
        }

        public void setFirstNameRus(String firstNameRus) {
            this.firstNameRus = firstNameRus;
        }

        public String getLastNameRus() {
            return lastNameRus;
        }

        public void setLastNameRus(String lastNameRus) {
            this.lastNameRus = lastNameRus;
        }

        public String getPatronymicRus() {
            return patronymicRus;
        }

        public void setPatronymicRus(String patronymicRus) {
            this.patronymicRus = patronymicRus;
        }

        public String getFirstNameEng() {
            return firstNameEng;
        }

        public void setFirstNameEng(String firstNameEng) {
            this.firstNameEng = firstNameEng;
        }

        public String getLastNameEng() {
            return lastNameEng;
        }

        public void setLastNameEng(String lastNameEng) {
            this.lastNameEng = lastNameEng;
        }

        public String getFirstNameAra() {
            return firstNameAra;
        }

        public void setFirstNameAra(String firstNameAra) {
            this.firstNameAra = firstNameAra;
        }

        public String getLastNameAra() {
            return lastNameAra;
        }

        public void setLastNameAra(String lastNameAra) {
            this.lastNameAra = lastNameAra;
        }

        public String getPatronymicAra() {
            return patronymicAra;
        }

        public void setPatronymicAra(String patronymicAra) {
            this.patronymicAra = patronymicAra;
        }

        public String getCompanyRus() {
            return companyRus;
        }

        public void setCompanyRus(String companyRus) {
            this.companyRus = companyRus;
        }

        public String getCompanyEng() {
            return companyEng;
        }

        public void setCompanyEng(String companyEng) {
            this.companyEng = companyEng;
        }

        public String getCompanyAra() {
            return companyAra;
        }

        public void setCompanyAra(String companyAra) {
            this.companyAra = companyAra;
        }

        public String getPositionRus() {
            return positionRus;
        }

        public void setPositionRus(String positionRus) {
            this.positionRus = positionRus;
        }

        public String getPositionAra() {
            return positionAra;
        }

        public void setPositionAra(String positionAra) {
            this.positionAra = positionAra;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
    private extendedInfo extendedInfo;
    private Integer id;
    private Integer eventId;
    private String nameRus;
    private String nameEng;
    private String nameAra;
    private String dateTimeStart;
    private String dateTimeFinish;
    private String placeRus;
    private String placeEng;
    private String placeAra;
    private Boolean isVip;
    private String descriptionRus;
    private String descriptionEng;
    private String descriptionAra;
    private Boolean registered;
    private Integer statusId;
    private Integer sportId;
    private String sportNameRus;
    private String sportNameEng;
    private List<speakers> speakers;


    public EventProgram(EventProgram.extendedInfo extendedInfo, Integer id, Integer eventId, String nameRus, String nameEng, String nameAra, String dateTimeStart, String dateTimeFinish, String placeRus, String placeEng, String placeAra, Boolean isVip, String descriptionRus, String descriptionEng, String descriptionAra, Boolean registered, Integer statusId, Integer sportId, String sportNameRus, String sportNameEng, List<EventProgram.speakers> speakers) {
        this.extendedInfo = extendedInfo;
        this.id = id;
        this.eventId = eventId;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.nameAra = nameAra;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeFinish = dateTimeFinish;
        this.placeRus = placeRus;
        this.placeEng = placeEng;
        this.placeAra = placeAra;
        this.isVip = isVip;
        this.descriptionRus = descriptionRus;
        this.descriptionEng = descriptionEng;
        this.descriptionAra = descriptionAra;
        this.registered = registered;
        this.statusId = statusId;
        this.sportId = sportId;
        this.sportNameRus = sportNameRus;
        this.sportNameEng = sportNameEng;
        this.speakers = speakers;
    }

    public String getSportNameRus() {
        return sportNameRus;
    }

    public void setSportNameRus(String sportNameRus) {
        this.sportNameRus = sportNameRus;
    }

    public String getSportNameEng() {
        return sportNameEng;
    }

    public void setSportNameEng(String sportNameEng) {
        this.sportNameEng = sportNameEng;
    }

    public EventProgram.extendedInfo getExtendedInfo() {
        return extendedInfo;
    }

    public void setExtendedInfo(EventProgram.extendedInfo extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public String getNameAra() {
        return nameAra;
    }

    public void setNameAra(String nameAra) {
        this.nameAra = nameAra;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeFinish() {
        return dateTimeFinish;
    }

    public void setDateTimeFinish(String dateTimeFinish) {
        this.dateTimeFinish = dateTimeFinish;
    }

    public String getPlaceRus() {
        return placeRus;
    }

    public void setPlaceRus(String placeRus) {
        this.placeRus = placeRus;
    }

    public String getPlaceEng() {
        return placeEng;
    }

    public void setPlaceEng(String placeEng) {
        this.placeEng = placeEng;
    }

    public String getPlaceAra() {
        return placeAra;
    }

    public void setPlaceAra(String placeAra) {
        this.placeAra = placeAra;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
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

    public String getDescriptionAra() {
        return descriptionAra;
    }

    public void setDescriptionAra(String descriptionAra) {
        this.descriptionAra = descriptionAra;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public List<EventProgram.speakers> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<EventProgram.speakers> speakers) {
        this.speakers = speakers;
    }
}
