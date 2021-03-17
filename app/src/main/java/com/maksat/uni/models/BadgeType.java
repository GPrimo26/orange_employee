package com.maksat.uni.models;

import java.util.List;

public class BadgeType {
    public static class Category{
        private Integer id;
        private String nameRus;
        private String nameEng;
        private String nameAra;

        public Category(Integer id, String nameRus, String nameEng, String nameAra) {
            this.id = id;
            this.nameRus = nameRus;
            this.nameEng = nameEng;
            this.nameAra = nameAra;
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

        public String getNameAra() {
            return nameAra;
        }

        public void setNameAra(String nameAra) {
            this.nameAra = nameAra;
        }
    }

    private Integer id;
    private String type;
    private String color;
    private String photo;
    private List<Category> categories;

    public BadgeType(Integer id, String type, String color, String photo, List<Category> categories) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.photo = photo;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
