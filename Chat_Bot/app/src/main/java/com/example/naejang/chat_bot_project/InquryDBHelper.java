package com.example.naejang.chat_bot_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 홍휘랑 on 2017-10-19.
 */


public class InquryDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DBFILE_CONTACT = "inqury_list.db";

    public InquryDBHelper(Context context) {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InquryDBCtrct.SQL_CREATE_TBL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL(InquryDBCtrct.SQL_DROP_TBL) ;
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion); }
    }


}