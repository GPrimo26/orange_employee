package com.maksat.uni.models;

public class ForgotPassword {
    public String email;
    public String username;
    public Integer languageId;

    public ForgotPassword(String email, String username, Integer languageId) {
        this.email = email;
        this.username = username;
        this.languageId = languageId;
    }
}
