package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MasterItems implements Serializable {

    @Expose
    @SerializedName("EvolveItemID")
    private int EvolveItemID;

    @Expose
    @SerializedName("EvolveItemSite")
    private String EvolveItemSite;

    @Expose
    @SerializedName("EvolveItemLoc")
    private String EvolveItemLoc;

    @Expose
    @SerializedName("EvolveItemPart")
    private String EvolveItemPart;

    @Expose
    @SerializedName("EvolveItemRef")
    private String EvolveItemRef;

    @Expose
    @SerializedName("EvolveItemQty")
    private String EvolveItemQty;

    @Expose
    @SerializedName("EvolveItemCreatedDate")
    private String EvolveItemCreatedDate;

    @Expose
    @SerializedName("EvolveItemUpdatedDate")
    private String EvolveItemUpdatedDate;

    @Expose
    @SerializedName("EvolveItemUOM")
    private String EvolveItemUOM;

    @Expose
    @SerializedName("EvolveItemType")
    private String EvolveItemType;

    @Expose
    @SerializedName("EvolveItemStatus")
    private String EvolveItemStatus;

    @Expose
    @SerializedName("EvolveItemDescription")
    private String EvolveItemDescription;

    @Expose
    @SerializedName("EvolveItemLot")
    private String EvolveItemLot;

    public int getEvolveItemID() {
        return EvolveItemID;
    }

    public void setEvolveItemID(int evolveItemID) {
        EvolveItemID = evolveItemID;
    }

    public String getEvolveItemSite() {
        return EvolveItemSite;
    }

    public void setEvolveItemSite(String evolveItemSite) {
        EvolveItemSite = evolveItemSite;
    }

    public String getEvolveItemLoc() {
        return EvolveItemLoc;
    }

    public void setEvolveItemLoc(String evolveItemLoc) {
        EvolveItemLoc = evolveItemLoc;
    }

    public String getEvolveItemPart() {
        return EvolveItemPart;
    }

    public void setEvolveItemPart(String evolveItemPart) {
        EvolveItemPart = evolveItemPart;
    }

    public String getEvolveItemRef() {
        return EvolveItemRef;
    }

    public void setEvolveItemRef(String evolveItemRef) {
        EvolveItemRef = evolveItemRef;
    }

    public String getEvolveItemQty() {
        return EvolveItemQty;
    }

    public void setEvolveItemQty(String evolveItemQty) {
        EvolveItemQty = evolveItemQty;
    }

    public String getEvolveItemCreatedDate() {
        return EvolveItemCreatedDate;
    }

    public void setEvolveItemCreatedDate(String evolveItemCreatedDate) {
        EvolveItemCreatedDate = evolveItemCreatedDate;
    }

    public String getEvolveItemUpdatedDate() {
        return EvolveItemUpdatedDate;
    }

    public void setEvolveItemUpdatedDate(String evolveItemUpdatedDate) {
        EvolveItemUpdatedDate = evolveItemUpdatedDate;
    }

    public String getEvolveItemUOM() {
        return EvolveItemUOM;
    }

    public void setEvolveItemUOM(String evolveItemUOM) {
        EvolveItemUOM = evolveItemUOM;
    }

    public String getEvolveItemType() {
        return EvolveItemType;
    }

    public void setEvolveItemType(String evolveItemType) {
        EvolveItemType = evolveItemType;
    }

    public String getEvolveItemStatus() {
        return EvolveItemStatus;
    }

    public void setEvolveItemStatus(String evolveItemStatus) {
        EvolveItemStatus = evolveItemStatus;
    }

    public String getEvolveItemDescription() {
        return EvolveItemDescription;
    }

    public void setEvolveItemDescription(String evolveItemDescription) {
        EvolveItemDescription = evolveItemDescription;
    }

    public String getEvolveItemLot() {
        return EvolveItemLot;
    }

    public void setEvolveItemLot(String evolveItemLot) {
        EvolveItemLot = evolveItemLot;
    }
}
