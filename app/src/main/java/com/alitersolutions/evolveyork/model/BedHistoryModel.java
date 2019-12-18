package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BedHistoryModel {

    @Expose
    @SerializedName("EvolveBedHistory_ID")
    private int EvolveBedHistory_ID;

    @Expose
    @SerializedName("EvolveBed_ID")
    private int EvolveBed_ID;

    @Expose
    @SerializedName("EvolveRoom_No")
    private int EvolveRoom_No;

    @Expose
    @SerializedName("EvolveBedHistory_Comment")
    private String EvolveBedHistory_Comment;

    @Expose
    @SerializedName("EvolveBedHistory_OutTime")
    private String EvolveBedHistory_OutTime;

    @Expose
    @SerializedName("EvolveBedHistory_InTime")
    private String EvolveBedHistory_InTime;

    @Expose
    @SerializedName("EvolveBedHistory_Duration")
    private String EvolveBedHistory_Duration;

    public int getEvolveBedHistory_ID() {
        return EvolveBedHistory_ID;
    }

    public void setEvolveBedHistory_ID(int evolveBedHistory_ID) {
        EvolveBedHistory_ID = evolveBedHistory_ID;
    }

    public int getEvolveBed_ID() {
        return EvolveBed_ID;
    }

    public void setEvolveBed_ID(int evolveBed_ID) {
        EvolveBed_ID = evolveBed_ID;
    }

    public int getEvolveRoom_No() {
        return EvolveRoom_No;
    }

    public void setEvolveRoom_No(int evolveRoom_No) {
        EvolveRoom_No = evolveRoom_No;
    }

    public String getEvolveBedHistory_Comment() {
        return EvolveBedHistory_Comment;
    }

    public void setEvolveBedHistory_Comment(String evolveBedHistory_Comment) {
        EvolveBedHistory_Comment = evolveBedHistory_Comment;
    }

    public String getEvolveBedHistory_OutTime() {
        return EvolveBedHistory_OutTime;
    }

    public void setEvolveBedHistory_OutTime(String evolveBedHistory_OutTime) {
        EvolveBedHistory_OutTime = evolveBedHistory_OutTime;
    }

    public String getEvolveBedHistory_InTime() {
        return EvolveBedHistory_InTime;
    }

    public void setEvolveBedHistory_InTime(String evolveBedHistory_InTime) {
        EvolveBedHistory_InTime = evolveBedHistory_InTime;
    }

    public String getEvolveBedHistory_Duration() {
        return EvolveBedHistory_Duration;
    }

    public void setEvolveBedHistory_Duration(String evolveBedHistory_Duration) {
        EvolveBedHistory_Duration = evolveBedHistory_Duration;
    }
}
