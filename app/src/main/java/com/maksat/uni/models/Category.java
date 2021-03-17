package com.maksat.uni.models;

public class Category {
    private Integer id;
    private Integer eventCategoryId;
    private Integer eventId;
    private String nameRus;
    private String nameEng;
    private String nameAra;
    private String descriptionRus;
    private String descriptionEng;
    private String descriptionAra;
    private String code;
    private String color;
    private Integer priceRouble;
    private Integer priceDollar;

    public Category(Integer id, Integer eventCategoryId, Integer eventId, String nameRus, String nameEng, String nameAra, String descriptionRus, String descriptionEng, String descriptionAra, String code, String color, Integer priceRouble, Integer priceDollar) {
        this.id = id;
        this.eventCategoryId = eventCategoryId;
        this.eventId = eventId;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.nameAra = nameAra;
        this.descriptionRus = descriptionRus;
        this.descriptionEng = descriptionEng;
        this.descriptionAra = descriptionAra;
        this.code = code;
        this.color = color;
        this.priceRouble = priceRouble;
        this.priceDollar = priceDollar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPriceRouble() {
        return priceRouble;
    }

    public void setPriceRouble(Integer priceRouble) {
        this.priceRouble = priceRouble;
    }

    public Integer getPriceDollar() {
        return priceDollar;
    }

    public void setPriceDollar(Integer priceDollar) {
        this.priceDollar = priceDollar;
    }
}
