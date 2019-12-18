package com.alitersolutions.evolveyork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseModel implements Serializable {
    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("statusCode")
    private int statusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("EvolveToken")
    private String EvolveToken;

    @Expose
    @SerializedName("result")
    private Object result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEvolveToken() {
        return EvolveToken;
    }

    public void setEvolveToken(String evolveToken) {
        EvolveToken = evolveToken;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
