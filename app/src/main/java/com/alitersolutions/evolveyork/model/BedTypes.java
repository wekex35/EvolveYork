package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BedTypes {

    @Expose
    @SerializedName("EvolveType_ID")
    private int EvolveType_ID;

    @Expose
    @SerializedName("EvolveType_Name")
    private String EvolveType_Name;

    public int getEvolveType_ID() {
        return EvolveType_ID;
    }

    public void setEvolveType_ID(int evolveType_ID) {
        EvolveType_ID = evolveType_ID;
    }

    public String getEvolveType_Name() {
        return EvolveType_Name;
    }

    public void setEvolveType_Name(String evolveType_Name) {
        EvolveType_Name = evolveType_Name;
    }
}
