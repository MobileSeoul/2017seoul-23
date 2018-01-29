package com.example.naejang.chat_bot_project;

/**
 * Created by naejang on 2017-09-22.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeoulCulture {

    @SerializedName("ListPublicReservationCulture")
    @Expose
    private ListPublicReservationCulture listPublicReservationCulture;

    public ListPublicReservationCulture getListPublicReservationCulture() {
        return listPublicReservationCulture;
    }

    public void setListPublicReservationCulture(ListPublicReservationCulture listPublicReservationCulture) {
        this.listPublicReservationCulture = listPublicReservationCulture;
    }

}