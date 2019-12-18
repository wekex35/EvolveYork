package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BedHistoryResponse {

    @Expose
    @SerializedName("draw")
    private String draw;

    @Expose
    @SerializedName("recordsTotal")
    private float recordsTotal;

    @Expose
    @SerializedName("recordsFiltered")
    private float recordsFiltered;

    @Expose
    @SerializedName("data")
    ArrayList<BedHistoryModel> data = new ArrayList<>();

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public float getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(float recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public float getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(float recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public ArrayList<BedHistoryModel> getData() {
        return data;
    }

    public void setData(ArrayList<BedHistoryModel> data) {
        this.data = data;
    }
}
