package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterLocation {
    @Expose
    @SerializedName("EvolveItemLocationID")
    private int EvolveItemLocationID;

    @Expose
    @SerializedName("EvolveItemLocationSite")
    private String EvolveItemLocationSite;

    @Expose
    @SerializedName("EvolveItemLocation")
    private String EvolveItemLocation;

    @Expose
    @SerializedName("EvolveItemDescription")
    private String EvolveItemDescription;

    @Expose
    @SerializedName("EvolveItemLocationInvStatus")
    private String EvolveItemLocationInvStatus;

    @Expose
    @SerializedName("EvolveItemLocationCreatedDate")
    private String EvolveItemLocationCreatedDate;

    public int getEvolveItemLocationID() {
        return EvolveItemLocationID;
    }

    public void setEvolveItemLocationID(int evolveItemLocationID) {
        EvolveItemLocationID = evolveItemLocationID;
    }

    public String getEvolveItemLocationSite() {
        return EvolveItemLocationSite;
    }

    public void setEvolveItemLocationSite(String evolveItemLocationSite) {
        EvolveItemLocationSite = evolveItemLocationSite;
    }

    public String getEvolveItemLocation() {
        return EvolveItemLocation;
    }

    public void setEvolveItemLocation(String evolveItemLocation) {
        EvolveItemLocation = evolveItemLocation;
    }

    public String getEvolveItemDescription() {
        return EvolveItemDescription;
    }

    public void setEvolveItemDescription(String evolveItemDescription) {
        EvolveItemDescription = evolveItemDescription;
    }

    public String getEvolveItemLocationInvStatus() {
        return EvolveItemLocationInvStatus;
    }

    public void setEvolveItemLocationInvStatus(String evolveItemLocationInvStatus) {
        EvolveItemLocationInvStatus = evolveItemLocationInvStatus;
    }

    public String getEvolveItemLocationCreatedDate() {
        return EvolveItemLocationCreatedDate;
    }

    public void setEvolveItemLocationCreatedDate(String evolveItemLocationCreatedDate) {
        EvolveItemLocationCreatedDate = evolveItemLocationCreatedDate;
    }
}
