package com.maksat.uni.models;

import com.google.gson.annotations.SerializedName;

public class AuthBody {
    private String userName;
    private String password;

    public AuthBody(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @SerializedName("access_token")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }
}
