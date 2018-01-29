package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SeoulInstitution {
    @SerializedName("ListPublicReservationInstitution")
    @Expose
    private ListPublicReservationInstitution listPublicReservationInstitution;

    public ListPublicReservationInstitution getListPublicReservationInstitution() {
        return listPublicReservationInstitution;
    }

    public void setListPublicReservationInstitution(ListPublicReservationInstitution listPublicReservationInstitution) {
        this.listPublicReservationInstitution = listPublicReservationInstitution;
    }
}
