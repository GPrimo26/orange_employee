package com.maksat.uni.models;

public class RecoverPassword {
    public String email;
    public String username;
    public String password;
    public String passwordConfirm;
    public String code;

    public RecoverPassword(String email, String username, String password, String passwordConfirm, String code) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.code = code;
    }
}
