package com.maksat.uni.models;

import java.util.List;

public class Dashboard {
    private Integer arrive;
    private Integer departure;
    private Integer draft;
    private Integer approved;
    private Integer checkIn;
    private Integer checkOut;
    private List<categories> categories;

    public static class categories{
        private String itemNameRus;
        private String itemNameEng;
        private Integer participantCount;

        public categories(String itemNameRus, String itemNameEng, Integer participantCount) {
            this.itemNameRus = itemNameRus;
            this.itemNameEng = itemNameEng;
            this.participantCount = participantCount;
        }

        public String getItemNameRus() {
            return itemNameRus;
        }

        public void setItemNameRus(String itemNameRus) {
            this.itemNameRus = itemNameRus;
        }

        public String getItemNameEng() {
            return itemNameEng;
        }

        public void setItemNameEng(String itemNameEng) {
            this.itemNameEng = itemNameEng;
        }

        public Integer getParticipantCount() {
            return participantCount;
        }

        public void setParticipantCount(Integer participantCount) {
            this.participantCount = participantCount;
        }
    }

    public Dashboard(Integer arrive, Integer departure, Integer draft, Integer approved, Integer checkIn, Integer checkOut, List<Dashboard.categories> categories) {
        this.arrive = arrive;
        this.departure = departure;
        this.draft = draft;
        this.approved = approved;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.categories = categories;
    }

    public List<Dashboard.categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Dashboard.categories> categories) {
        this.categories = categories;
    }

    public Integer getArrive() {
        return arrive;
    }

    public void setArrive(Integer arrive) {
        this.arrive = arrive;
    }

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    public Integer getDraft() {
        return draft;
    }

    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public Integer getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Integer checkIn) {
        this.checkIn = checkIn;
    }

    public Integer getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Integer checkOut) {
        this.checkOut = checkOut;
    }
}
