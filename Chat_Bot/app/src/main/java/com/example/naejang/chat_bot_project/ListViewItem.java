package com.example.naejang.chat_bot_project;

import android.graphics.drawable.Drawable;

/**
 * Created by 홍휘랑 on 2017-10-21.
 */

public class ListViewItem {

    private Drawable icon ;

    private String name ;
    private String cate ;
    private String area ;
    private String date ;

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public Drawable getIcon() {
        return this.icon ;
    }


    public void setName(String text) {
        this.name = text ;
    }
    public String getName() {
        return this.name ;
    }


    public void setCate(String text) {
        this.cate = text ;
    }
    public String getCate() {
        return this.cate ;
    }


    public void setArea(String text) {
        this.area = text ;
    }
    public String getArea() {
        return this.area ;
    }


    public void setDate(String text) {
        this.date = text ;
    }
    public String getDate() {
        return this.date ;
    }


}
