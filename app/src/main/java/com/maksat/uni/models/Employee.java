package com.maksat.uni.models;

import java.util.List;

public class Employee {
    private Integer id;
    private String fistName;
    private String  lastName;
    private String partonymic;
    private String birthday;
    private Integer genderId;
    private Integer languageId;
    private String email;
    private String userName;
    private List<String> roles;
    private String claim;
    private String phoneNumber;
    private Integer positionId;
    private String positionName;

    public Employee(Integer id, String fistName, String lastName, String partonymic, String birthday, Integer genderId, Integer languageId, String email, String userName, List<String> roles, String claim, String phoneNumber, Integer positionId, String positionName) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.partonymic = partonymic;
        this.birthday = birthday;
        this.genderId = genderId;
        this.languageId = languageId;
        this.email = email;
        this.userName = userName;
        this.roles = roles;
        this.claim = claim;
        this.phoneNumber = phoneNumber;
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPartonymic() {
        return partonymic;
    }

    public void setPartonymic(String partonymic) {
        this.partonymic = partonymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
