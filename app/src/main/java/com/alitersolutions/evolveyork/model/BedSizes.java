package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BedSizes implements Serializable {
        @Expose
        @SerializedName("EvolveSize_ID")
        private int EvolveSize_ID;

        @Expose
        @SerializedName("EvolveSize_Name")
        private String EvolveSize_Name;

    public int getEvolveSize_ID() {
        return EvolveSize_ID;
    }

    public void setEvolveSize_ID(int evolveSize_ID) {
        EvolveSize_ID = evolveSize_ID;
    }

    public String getEvolveSize_Name() {
        return EvolveSize_Name;
    }

    public void setEvolveSize_Name(String evolveSize_Name) {
        EvolveSize_Name = evolveSize_Name;
    }
}
