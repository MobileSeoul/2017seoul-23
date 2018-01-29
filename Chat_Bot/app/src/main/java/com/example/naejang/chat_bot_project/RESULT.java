package com.example.naejang.chat_bot_project;

/**
 * Created by naejang on 2017-09-20.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESULT {

    @SerializedName("CODE")
    @Expose
    private String cODE;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;

    public String getCODE() {
        return cODE;
    }

    public void setCODE(String cODE) {
        this.cODE = cODE;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

}