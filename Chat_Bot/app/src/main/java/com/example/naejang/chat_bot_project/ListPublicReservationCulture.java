package com.example.naejang.chat_bot_project;

/**
 * Created by naejang on 2017-09-22.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPublicReservationCulture {

    @SerializedName("list_total_count")
    @Expose
    private Integer listTotalCount;
    @SerializedName("RESULT")
    @Expose
    private RESULT rESULT;

    @SerializedName("row")
    @Expose
    private List<SeoulCulturedetail> row = null;

    public Integer getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(Integer listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public RESULT getRESULT() {
        return rESULT;
    }

    public void setRESULT(RESULT rESULT) {
        this.rESULT = rESULT;
    }

    public List<SeoulCulturedetail> getRow() {
        return row;
    }

    public void setRow(List<SeoulCulturedetail> row) {
        this.row = row;
    }

}