package com.example.naejang.chat_bot_project;
/**
 * Created by 홍휘랑 on 2017-10-19.
 */
public class InquryDBCtrct {

        private InquryDBCtrct() {};

        public static final String TBL_INQURY = "INQURY_T";
        public static final String COL_SID = "SID";
        public static final String COL_NAME = "NAME";
        public static final String COL_CATE = "CATE";
        public static final String COL_AREA = "AREA";
        public static final String COL_DATE = "DATE";


        //CREATE TABLE IF NOT EXISTS CONTACT_T
        public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_INQURY + " " +
                "(" +COL_SID + " TEXT NOT NULL" + ", " + COL_NAME + " TEXT" + ", " + COL_CATE + " TEXT" + ", "
                + COL_AREA + " TEXT" + ", " + COL_DATE + " TEXT" + ")" ;


        // DROP TABLE IF EXISTS CONTACT_T
         public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_INQURY ;

        // SELECT * FROM CONTACT_T
        public static final String SQL_SELECT = "SELECT * FROM " + TBL_INQURY ;

         //INSERT OR REPLACE INTO CONTACT_T (NO, NAME, PHONE, OVER20) VALUES (x, x, x, x)
         public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_INQURY + " " +
         "(" + COL_SID + ", " + COL_NAME + ", " + COL_CATE + ", " + COL_AREA + ", " + COL_DATE + ") VALUES " ;

        // DELETE FROM CONTACT_T
        public static final String SQL_DELETE = "DELETE FROM " + TBL_INQURY ;


}