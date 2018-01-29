package com.example.naejang.chat_bot_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 홍휘랑 on 2017-10-19.
 */

public class SubActivity extends AppCompatActivity {

    String[] Sid;
    String[] LIST_MENU;

    InquryDBHelper dbHelper = null;
    Menu mMenu;

    ArrayAdapter adapter;
    ListView listview;
    TextView textView;


    @Override
    protected void onResume() {
        super.onResume();
        //textView = (TextView) findViewById(R.id.textView);
        init_tables();
        load_values();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;
        listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, final int position, long id) {

                Toast.makeText(getApplicationContext(), LIST_MENU[position], Toast.LENGTH_SHORT).show();
                // get TextView's Text.
                new AlertDialog.Builder(SubActivity.this)
                        .setTitle("예약")
                        // .setIcon(R.drawable.order)
                        .setMessage("예약 하러 가시겠습니까?")
                        .setPositiveButton("예약하러가기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "http://yeyak.seoul.go.kr/mobile/detailView.web?rsvsvcid="+Sid[position];
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();


            }
        }) ;
    }

    private void delete_values(){
        SQLiteDatabase db = dbHelper.getWritableDatabase() ;
        db.execSQL(InquryDBCtrct.SQL_DELETE) ;
        LIST_MENU = null;

        adapter.notifyDataSetChanged();
        onResume();
    }

    private void load_values() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(InquryDBCtrct.SQL_SELECT, null);
        int count = cursor.getCount();
        Sid = new String[count];
        LIST_MENU = new String[count];

        for (int i = count-1; i >=0; i--) {
                cursor.moveToNext();
                Sid[i] = cursor.getString(0);
                LIST_MENU[i] = "이름 : " + cursor.getString(1)+ "\n" +
                                "카테고리 : " + cursor.getString(2)+ "\n" +
                                "지역 : "+cursor.getString(3) + "\n" +
                                "접수날짜 : "+cursor.getString(4);
        }

    }

    private void init_tables() {
        dbHelper = new InquryDBHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inqury_list);

        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F39700")));

        getSupportActionBar().setTitle("최근 본 예약");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }


        if (id == R.id.action_delete) {
            Toast.makeText(this, "delete클릭", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(SubActivity.this)
                    .setTitle("조회 정보 삭제")
                    // .setIcon(R.drawable.order)
                    .setMessage("조회 정보 삭제하시겠습니까?")
                    .setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete_values();

                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }







}
