package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SeoulEducation {

    @SerializedName("ListPublicReservationEducation")
    @Expose
    private ListPublicReservationEducation listPublicReservationEducation;

    public ListPublicReservationEducation getListPublicReservationEducation() {
        return listPublicReservationEducation;
    }

    public void setListPublicReservationEducation(ListPublicReservationEducation listPublicReservationEducation) {
        this.listPublicReservationEducation = listPublicReservationEducation;
    }
}
