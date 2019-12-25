package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SentModel {

    @Expose
    @SerializedName("EvolveItemRemarks")
    private String EvolveItemRemarks;

    @Expose
    @SerializedName("EvolveItemID")
    private int EvolveItemID;

    @Expose
    @SerializedName("EvolveBatchNo")
    private String EvolveBatchNo;

    @Expose
    @SerializedName("EvolveItemQty")
    private int EvolveItemQty;

    @Expose
    @SerializedName("EvolveItemLocationID")
    private int EvolveItemLocationID;

    @Expose
    @SerializedName("EvolveItemMeasuringUnits")
    private String EvolveItemMeasuringUnits;
    @Expose
    @SerializedName("EvolveItemName")
    private String EvolveItemName;

    @Expose
    @SerializedName("EvolveLocationName")
    private String EvolveLocationName;

    @Expose
    @SerializedName("UpdateDate")
    private String UpdatedDate;

    @Expose
    @SerializedName("UploadStatus")
    private String UploadStatus;

    @Expose
    @SerializedName("PrinterText")
    private String PrinterText;

    public String getEvolveItemRemarks() {
        return EvolveItemRemarks;
    }

    public void setEvolveItemRemarks(String evolveItemRemarks) {
        EvolveItemRemarks = evolveItemRemarks;
    }

    public int getEvolveItemID() {
        return EvolveItemID;
    }

    public void setEvolveItemID(int evolveItemID) {
        EvolveItemID = evolveItemID;
    }

    public String getEvolveBatchNo() {
        return EvolveBatchNo;
    }

    public void setEvolveBatchNo(String evolveBatchNo) {
        EvolveBatchNo = evolveBatchNo;
    }

    public int getEvolveItemQty() {
        return EvolveItemQty;
    }

    public void setEvolveItemQty(int evolveItemQty) {
        EvolveItemQty = evolveItemQty;
    }

    public int getEvolveItemLocationID() {
        return EvolveItemLocationID;
    }

    public void setEvolveItemLocationID(int evolveItemLocationID) {
        EvolveItemLocationID = evolveItemLocationID;
    }

    public String getEvolveItemMeasuringUnits() {
        return EvolveItemMeasuringUnits;
    }

    public void setEvolveItemMeasuringUnits(String evolveItemMeasuringUnits) {
        EvolveItemMeasuringUnits = evolveItemMeasuringUnits;
    }

    public String getEvolveItemName() {
        return EvolveItemName;
    }

    public void setEvolveItemName(String evolveItemName) {
        EvolveItemName = evolveItemName;
    }

    public String getEvolveLocationName() {
        return EvolveLocationName;
    }

    public void setEvolveLocationName(String evolveLocationName) {
        EvolveLocationName = evolveLocationName;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
    }

    public String getPrinterText() {
        return PrinterText;
    }

    public void setPrinterText(String printerText) {
        PrinterText = printerText;
    }
}
