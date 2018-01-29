package com.example.naejang.chat_bot_project;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ListPublicReservationEducation {


    @SerializedName("row")
    @Expose
    private List<SeoulEducationdetail> row = null;

    public List<SeoulEducationdetail> getRow() {
        return row;
    }

    public void setRow(List<SeoulEducationdetail> row) {
        this.row = row;
    }

}
