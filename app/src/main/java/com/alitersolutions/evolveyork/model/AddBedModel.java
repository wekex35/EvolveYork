package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBedModel {
    @Expose
    @SerializedName("EvolveBed_RFID")
    private String EvolveBed_RFID;

    @Expose
    @SerializedName("EvolveBed_Make")
    private String EvolveBed_Make;

    @Expose
    @SerializedName("EvolveBedSize_ID")
    private float EvolveBedSize_ID;

    @Expose
    @SerializedName("EvolveBedType_ID")
    private float EvolveBedType_ID;

    @Expose
    @SerializedName("EvolveBed_Desc")
    private String EvolveBed_Desc;



    // Getter Methods

        public String getEvolveBed_RFID() {
            return EvolveBed_RFID;
        }

        public String getEvolveBed_Make() {
            return EvolveBed_Make;
        }

        public float getEvolveBedSize_ID() {
            return EvolveBedSize_ID;
        }

        public float getEvolveBedType_ID() {
            return EvolveBedType_ID;
        }

        public String getEvolveBed_Desc() {
            return EvolveBed_Desc;
        }

        // Setter Methods

        public void setEvolveBed_RFID( String EvolveBed_RFID ) {
            this.EvolveBed_RFID = EvolveBed_RFID;
        }

        public void setEvolveBed_Make( String EvolveBed_Make ) {
            this.EvolveBed_Make = EvolveBed_Make;
        }

        public void setEvolveBedSize_ID( float EvolveBedSize_ID ) {
            this.EvolveBedSize_ID = EvolveBedSize_ID;
        }

        public void setEvolveBedType_ID( float EvolveBedType_ID ) {
            this.EvolveBedType_ID = EvolveBedType_ID;
        }

        public void setEvolveBed_Desc( String EvolveBed_Desc ) {
            this.EvolveBed_Desc = EvolveBed_Desc;
        }
}
