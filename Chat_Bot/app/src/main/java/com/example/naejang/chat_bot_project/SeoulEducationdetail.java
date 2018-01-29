package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeoulEducationdetail {


    @SerializedName("SVCID")
    @Expose
    private String sVCID;

    @SerializedName("MINCLASSNM")
    @Expose
    private String mINCLASSNM;
    @SerializedName("SVCSTATNM")
    @Expose
    private String sVCSTATNM;
    @SerializedName("SVCNM")
    @Expose
    private String sVCNM;


    @SerializedName("RCPTBGNDT")
    @Expose
    private String rCPTBGNDT;
    @SerializedName("RCPTENDDT")
    @Expose
    private String rCPTENDDT;
    @SerializedName("AREANM")
    @Expose
    private String aREANM;



    public String getSVCID() {
        return sVCID;
    }

    public void setSVCID(String sVCID) {
        this.sVCID = sVCID;
    }



    public String getMINCLASSNM() {
        return mINCLASSNM;
    }

    public void setMINCLASSNM(String mINCLASSNM) {
        this.mINCLASSNM = mINCLASSNM;
    }

    public String getSVCSTATNM() {
        return sVCSTATNM;
    }

    public void setSVCSTATNM(String sVCSTATNM) {
        this.sVCSTATNM = sVCSTATNM;
    }

    public String getSVCNM() {
        return sVCNM;
    }

    public void setSVCNM(String sVCNM) {
        this.sVCNM = sVCNM;
    }



    public String getRCPTBGNDT() {
        return rCPTBGNDT;
    }

    public void setRCPTBGNDT(String rCPTBGNDT) {
        this.rCPTBGNDT = rCPTBGNDT;
    }

    public String getRCPTENDDT() {
        return rCPTENDDT;
    }

    public void setRCPTENDDT(String rCPTENDDT) {
        this.rCPTENDDT = rCPTENDDT;
    }

    public String getAREANM() {
        return aREANM;
    }

    public void setAREANM(String aREANM) {
        this.aREANM = aREANM;
    }
}
