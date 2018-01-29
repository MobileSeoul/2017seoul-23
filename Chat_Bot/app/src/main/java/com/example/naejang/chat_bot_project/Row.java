package com.example.naejang.chat_bot_project;

/**
 * Created by naejang on 2017-09-20.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("GUBUN")
    @Expose
    private String gUBUN;
    @SerializedName("SVCID")
    @Expose
    private String sVCID;
    @SerializedName("MAXCLASSNM")
    @Expose
    private String mAXCLASSNM;
    @SerializedName("MINCLASSNM")
    @Expose
    private String mINCLASSNM;
    @SerializedName("SVCSTATNM")
    @Expose
    private String sVCSTATNM;
    @SerializedName("SVCNM")
    @Expose
    private String sVCNM;
    @SerializedName("PAYATNM")
    @Expose
    private String pAYATNM;
    @SerializedName("PLACENM")
    @Expose
    private String pLACENM;
    @SerializedName("USETGTINFO")
    @Expose
    private String uSETGTINFO;
    @SerializedName("SVCURL")
    @Expose
    private String sVCURL;
    @SerializedName("X")
    @Expose
    private String x;
    @SerializedName("Y")
    @Expose
    private String y;
    @SerializedName("SVCOPNBGNDT")
    @Expose
    private String sVCOPNBGNDT;
    @SerializedName("SVCOPNENDDT")
    @Expose
    private String sVCOPNENDDT;
    @SerializedName("RCPTBGNDT")
    @Expose
    private String rCPTBGNDT;
    @SerializedName("RCPTENDDT")
    @Expose
    private String rCPTENDDT;
    @SerializedName("AREANM")
    @Expose
    private String aREANM;

    public String getGUBUN() {
        return gUBUN;
    }

    public void setGUBUN(String gUBUN) {
        this.gUBUN = gUBUN;
    }

    public String getSVCID() {
        return sVCID;
    }

    public void setSVCID(String sVCID) {
        this.sVCID = sVCID;
    }

    public String getMAXCLASSNM() {
        return mAXCLASSNM;
    }

    public void setMAXCLASSNM(String mAXCLASSNM) {
        this.mAXCLASSNM = mAXCLASSNM;
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

    public String getPAYATNM() {
        return pAYATNM;
    }

    public void setPAYATNM(String pAYATNM) {
        this.pAYATNM = pAYATNM;
    }

    public String getPLACENM() {
        return pLACENM;
    }

    public void setPLACENM(String pLACENM) {
        this.pLACENM = pLACENM;
    }

    public String getUSETGTINFO() {
        return uSETGTINFO;
    }

    public void setUSETGTINFO(String uSETGTINFO) {
        this.uSETGTINFO = uSETGTINFO;
    }

    public String getSVCURL() {
        return sVCURL;
    }

    public void setSVCURL(String sVCURL) {
        this.sVCURL = sVCURL;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getSVCOPNBGNDT() {
        return sVCOPNBGNDT;
    }

    public void setSVCOPNBGNDT(String sVCOPNBGNDT) {
        this.sVCOPNBGNDT = sVCOPNBGNDT;
    }

    public String getSVCOPNENDDT() {
        return sVCOPNENDDT;
    }

    public void setSVCOPNENDDT(String sVCOPNENDDT) {
        this.sVCOPNENDDT = sVCOPNENDDT;
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