package com.example.naejang.chat_bot_project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */

public class ListPublicReservationSport {


    @SerializedName("row")
    @Expose
    private List<SeoulSportdetail> row = null;


    public List<SeoulSportdetail> getRow() {
        return row;
    }

    public void setRow(List<SeoulSportdetail> row) {
        this.row = row;
    }



}
