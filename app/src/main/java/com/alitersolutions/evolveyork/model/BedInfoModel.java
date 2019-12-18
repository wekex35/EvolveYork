package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BedInfoModel implements Serializable {

    @Expose
    @SerializedName("EvolveBed_ID")
    private int EvolveBed_ID;
    @Expose
    @SerializedName("bed_id")
    private int bed_id;

    @Expose
    @SerializedName("EvolveBed_Code")
    private String EvolveBed_Code;

    @Expose
    @SerializedName("EvolveBed_RFID")
    private String EvolveBed_RFID;

    @Expose
    @SerializedName("EvolveBed_Make")
    private String EvolveBed_Make;

    @Expose
    @SerializedName("EvolveSize_Name")
    private String EvolveSize_Name;

    @Expose
    @SerializedName("EvolveType_Name")
    private String EvolveType_Name;

    @Expose
    @SerializedName("EvolveBed_Status")
    private boolean EvolveBed_Status;


    @Expose
    @SerializedName("EvolveBedHistory_ID")
    private int EvolveBedHistory_ID;


        // Getter Methods

        public int getEvolveBed_ID() {
            return EvolveBed_ID;
        }

        public String getEvolveBed_Code() {
            return EvolveBed_Code;
        }

        public String getEvolveBed_RFID() {
            return EvolveBed_RFID;
        }

        public String getEvolveBed_Make() {
            return EvolveBed_Make;
        }

        public String getEvolveSize_Name() {
            return EvolveSize_Name;
        }

        public String getEvolveType_Name() {
            return EvolveType_Name;
        }

        public boolean getEvolveBed_Status() {
            return EvolveBed_Status;
        }

        // Setter Methods

        public void setEvolveBed_ID( int EvolveBed_ID ) {
            this.EvolveBed_ID = EvolveBed_ID;
        }

        public void setEvolveBed_Code( String EvolveBed_Code ) {
            this.EvolveBed_Code = EvolveBed_Code;
        }

        public void setEvolveBed_RFID( String EvolveBed_RFID ) {
            this.EvolveBed_RFID = EvolveBed_RFID;
        }

        public void setEvolveBed_Make( String EvolveBed_Make ) {
            this.EvolveBed_Make = EvolveBed_Make;
        }

        public void setEvolveSize_Name( String EvolveSize_Name ) {
            this.EvolveSize_Name = EvolveSize_Name;
        }

        public void setEvolveType_Name( String EvolveType_Name ) {
            this.EvolveType_Name = EvolveType_Name;
        }

        public void setEvolveBed_Status( boolean EvolveBed_Status ) {
            this.EvolveBed_Status = EvolveBed_Status;
        }


        public int getEvolveBedHistory_ID() {
            return EvolveBedHistory_ID;
        }

        public void setEvolveBedHistory_ID(int evolveBedHistory_ID) {
            EvolveBedHistory_ID = evolveBedHistory_ID;
        }

    public int getBed_id() {
        return bed_id;
    }

    public void setBed_id(int bed_id) {
        this.bed_id = bed_id;
    }

    public boolean isEvolveBed_Status() {
        return EvolveBed_Status;
    }
}
