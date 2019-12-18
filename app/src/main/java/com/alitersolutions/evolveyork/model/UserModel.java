package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName("EvolveUser_EmailID")
    @Expose
    String UserEmail;

    @SerializedName("EvolveUser_password")
    @Expose
    String UserPassword;

    @SerializedName("EvolveToken")
    @Expose
    String EvolveToken;

    @SerializedName("EvolveUser_Name")
    @Expose
    String EvolveUserName;

    @SerializedName("EvolveUser_ID")
    @Expose
    String EvolveUserID;

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getEvolveToken() {
        return EvolveToken;
    }

    public void setEvolveToken(String evolveToken) {
        EvolveToken = evolveToken;
    }

    public String getEvolveUserName() {
        return EvolveUserName;
    }

    public void setEvolveUserName(String evolveUserName) {
        EvolveUserName = evolveUserName;
    }

    public String getEvolveUserID() {
        return EvolveUserID;
    }

    public void setEvolveUserID(String evolveUserID) {
        EvolveUserID = evolveUserID;
    }
}
