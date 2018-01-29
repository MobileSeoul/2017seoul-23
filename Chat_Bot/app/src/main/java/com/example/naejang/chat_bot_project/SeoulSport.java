package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeoulSport {
    @SerializedName("ListPublicReservationSport")
    @Expose
    private ListPublicReservationSport listPublicReservationSport;

    public ListPublicReservationSport getListPublicReservationSport() {
        return listPublicReservationSport;
    }

    public void setListPublicReservationSport(ListPublicReservationSport listPublicReservationSport) {
        this.listPublicReservationSport = listPublicReservationSport;
    }


}
