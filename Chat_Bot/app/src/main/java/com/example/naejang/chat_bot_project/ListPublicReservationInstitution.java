package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ListPublicReservationInstitution {

    @SerializedName("row")
    @Expose
    private List<SeoulInstitutiondetail> row = null;

    public List<SeoulInstitutiondetail> getRow() {
        return row;
    }

    public void setRow(List<SeoulInstitutiondetail> row) {
        this.row = row;
    }


}
