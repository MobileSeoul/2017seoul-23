package com.example.naejang.chat_bot_project;



import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.utils.IMessageStatusIconFormatter;
import com.github.bassaer.chatmessageview.utils.ITimeFormatter;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.github.bassaer.chatmessageview.views.MessageView;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by naejang on 2017-09-21.
 */

public class MainActivity extends Activity{

    InquryDBHelper dbHelper = null;

    private ChatView mChatView;


    //conversation 함수
    public void Watson_Conversation(String input, Map<String, Object> context, final ConversationCallbacks getcallbacks){

        //watson 정보
        final ConversationService myConversationService =
                new ConversationService(
                        "2017-05-26",
                        getString(R.string.username),
                        getString(R.string.password)
                );
        myConversationService.setEndPoint("https://gateway.aibril-watson.kr/conversation/api");

        //request 생성
        final MessageRequest request = new MessageRequest.Builder()
                .inputText(input)
                .context(context)
                .build();

        //response
        myConversationService.message(getString(R.string.workspace), request).enqueue(new ServiceCallback<MessageResponse>() {
            @Override
            public void onResponse(final MessageResponse response) {

                Map<String, Object> response_context = null;
                response_context = response.getContext();

                //봇이 내보내는 말
                final String outputText = response.getText().get(0);
                //output.get(action)을 위하여
                final Map<String, Object> output = response.getOutput();

                getcallbacks.test(response);
                //getcallbacks.onSuccess(outputText);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }


    //@@@@@@@@@@@@@@@서울시 문화 api
    public void Seoul_Culture_inqury_information(final Get_informationCallbacks getcallbacks)
    {
        SeoulCultureApiService api = ServiceGenerator.getSeoul_Culture_ListApiService();
        Call<SeoulCulture> call = api.get_seoul_cultureList(getString(R.string.api_key));

        call.enqueue(new Callback<SeoulCulture>() {

            @Override
            public void onResponse(Call<SeoulCulture> call, Response<SeoulCulture> response) {

                if (response.isSuccessful()) {

                    ListPublicReservationCulture list_public_reservation_culture;
                    list_public_reservation_culture = response.body().getListPublicReservationCulture();

                    List<SeoulCulturedetail> information = list_public_reservation_culture.getRow();
                    getcallbacks.onSuccess_culture(information);

                } else {
                    //Log.v("SearchActivity",linenum);
                    Toast.makeText(getApplicationContext(), "respons fail_seoul", Toast.LENGTH_SHORT).show();
                    getcallbacks.onError();
                }
            }

            @Override
            public void onFailure(Call<SeoulCulture> call, Throwable t) {
                //Log.v("SearchActivity","onFailure"+linenum);
                getcallbacks.onError();
                Toast.makeText(getApplicationContext(), "onFailure_seoul", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //@@@@@@@@@@@@@@@@@@@@@서울시 스포츠 api
    public void Seoul_Sport_inqury_information(String first, String last, String sport,final Get_informationCallbacks getcallbacks)
    {
        SeoulSportApiService api = ServiceGenerator.getSeoul_Sport_ListApiService();
        Call<SeoulSport> call = api.get_seoul_sportList(getString(R.string.api_key),first,last,sport);

        call.enqueue(new Callback<SeoulSport>() {

            @Override
            public void onResponse(Call<SeoulSport> call, Response<SeoulSport> response) {

                if (response.isSuccessful()) {

                    ListPublicReservationSport listPublicReservationSport;
                    listPublicReservationSport = response.body().getListPublicReservationSport();

                    List<SeoulSportdetail> information = listPublicReservationSport.getRow();
                    getcallbacks.onSuccess_sport(information);

                } else {
                    Toast.makeText(getApplicationContext(), "respons fail_seoul sport", Toast.LENGTH_SHORT).show();
                    getcallbacks.onError();
                }
            }

            @Override
            public void onFailure(Call<SeoulSport> call, Throwable t) {
                //Log.v("SearchActivity","onFailure"+linenum);
                getcallbacks.onError();
                Toast.makeText(getApplicationContext(), "onFailure_seoul sport", Toast.LENGTH_SHORT).show();
            }
        });

    }

//@@@@@@@@@@@@@@@@@@@@서울시 교육
public void Seoul_Education_inqury_information(String first, String last, String edu, final Get_informationCallbacks getcallbacks)
{
    SeoulEducationApiService api = ServiceGenerator.getSeoul_Education_ListApiService();
    Call<SeoulEducation> call = api.get_seoul_educationList(getString(R.string.api_key),first,last,edu);

    call.enqueue(new Callback<SeoulEducation>() {

        @Override
        public void onResponse(Call<SeoulEducation> call, Response<SeoulEducation> response) {

            if (response.isSuccessful()) {

                ListPublicReservationEducation listPublicReservationEducation;
                listPublicReservationEducation = response.body().getListPublicReservationEducation();

                List<SeoulEducationdetail> information = listPublicReservationEducation.getRow();
                getcallbacks.onSuccess_education(information);

            } else {
                Toast.makeText(getApplicationContext(), "respons fail_seoul education", Toast.LENGTH_SHORT).show();
                getcallbacks.onError();
            }
        }

        @Override
        public void onFailure(Call<SeoulEducation> call, Throwable t) {
            //Log.v("SearchActivity","onFailure"+linenum);
            getcallbacks.onError();
            Toast.makeText(getApplicationContext(), "onFailure_seoul education", Toast.LENGTH_SHORT).show();
        }
    });

}
    //@@@@@@@@@@@@@@@@@@@@서울시 시설대관 api
    public void Seoul_Institution_inqury_information(String first, String last, String inst, final Get_informationCallbacks getcallbacks)
    {
        SeoulInstitutionApiService api = ServiceGenerator.getSeoul_Institution_ListApiService();
        Call<SeoulInstitution> call = api.get_seoul_institutionList(getString(R.string.api_key),first,last,inst);

        call.enqueue(new Callback<SeoulInstitution>() {

            @Override
            public void onResponse(Call<SeoulInstitution> call, Response<SeoulInstitution> response) {

                if (response.isSuccessful()) {

                    ListPublicReservationInstitution listPublicReservationInstitution;
                    listPublicReservationInstitution = response.body().getListPublicReservationInstitution();

                    List<SeoulInstitutiondetail> information = listPublicReservationInstitution.getRow();
                    getcallbacks.onSuccess_institution(information);

                } else {
                    Toast.makeText(getApplicationContext(), "respons fail_seoul institution", Toast.LENGTH_SHORT).show();
                    getcallbacks.onError();
                }
            }

            @Override
            public void onFailure(Call<SeoulInstitution> call, Throwable t) {
                //Log.v("SearchActivity","onFailure"+linenum);
                getcallbacks.onError();
                Toast.makeText(getApplicationContext(), "onFailure_seoul institution", Toast.LENGTH_SHORT).show();
            }
        });

    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //sqlite

    private void init_tables() {

        dbHelper = new InquryDBHelper(this);

    }

    //COL_SID + ", " + COL_NAME + ", " + COL_CATE + ", " + COL_AREA + ", " + COL_DATE + ") V
    private void save_values(String sid, String name, String cate, String area, String date ) {
        SQLiteDatabase db = dbHelper.getWritableDatabase() ;


        String sqlInsert = InquryDBCtrct.SQL_INSERT +
                " (" +
                "'" + sid + "', " +
                "'" + name + "', " +
                "'" + cate + "', " +
                "'" + area + "', " +
                "'" + date + "'" +
                ")";

        db.execSQL(sqlInsert);
    }

    private void delete_values(){
        SQLiteDatabase db = dbHelper.getWritableDatabase() ;
        db.execSQL(InquryDBCtrct.SQL_DELETE) ;
    }

    private void load_values(){

        SQLiteDatabase db = dbHelper.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(InquryDBCtrct.SQL_SELECT, null) ;

        if(cursor.moveToFirst()){

            String SID = cursor.getString(0);
            Toast.makeText(getApplicationContext(), SID, Toast.LENGTH_SHORT).show();
        }


    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startActivity(new Intent(this,SubTActivity.class));
        startActivity(new Intent(this,Splash.class));


        final ConversationService myConversationService =
                new ConversationService(
                        "2017-05-26",
                        getString(R.string.username),
                        getString(R.string.password)
                );
        myConversationService.setEndPoint("https://gateway.aibril-watson.kr/conversation/api");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//메세지 전송 부분//////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TextView textView = (TextView)findViewById(R.id.textView);

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "나";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.icic);
        String yourName = "해치BOT";
        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        mChatView = (ChatView)findViewById(R.id.chat_view);


//Set UI parameters if you need

        mChatView.setRightBubbleColor(Color.rgb(211,211,211));
        mChatView.setLeftBubbleColor(Color.rgb(243,151,0));
        mChatView.setSendIcon(R.drawable.go);
        mChatView.setRightMessageTextColor(Color.BLACK);
        mChatView.setLeftMessageTextColor(Color.WHITE);
        mChatView.setUsernameTextColor(Color.BLACK);
        mChatView.setSendTimeTextColor(Color.BLACK);
        mChatView.setDateSeparatorColor(Color.BLACK);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);



        final String first = "환영합니다!!! 서울시 공공서비스 예약을 도와드리는 서울시 해치 BOT 입니다.\n\n문화행사/시설대관/체육시설/교육/ 중에 한 분야를 입력 해주세요.\n\n☞ 대화 다시하기 ☜\n '처음' 또는 '재시작'";
        //new message 보내는
        Message message = new Message.Builder()
                .setUser(you)
                .setRightMessage(false)
                .setMessageText(first)
                .hideIcon(false)
                .build();
        mChatView.send(message);



//옵션 버튼 클릭
        mChatView.setOnClickOptionButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), "옵션눌러", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SubTActivity.class);
                startActivity(intent);
            }
        });


//클립보드에 복사
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        mChatView.setOnBubbleLongClickListener(new Message.OnBubbleLongClickListener() {
            @Override
            public void onLongClick(Message message) {
                clipboardManager.setText(message.getMessageText());
                Toast.makeText(getApplicationContext(), "클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show();

            }
        });

        init_tables();
//first
        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {

            /////대화 상태 유지
            Map<String, Object> context = null;

            /////기억하고있을 context
            Map<String, String> temp = new HashMap<String,String>();

            //SID값 저장Q
            ArrayList<String> SID = new ArrayList<String>();

            //insert_db
            ArrayList<String> INSERT = new ArrayList<String>();

            //////////////////////
            @Override
            public void onClick(View view) {

                final String SEND = mChatView.getInputText();

                //new message 보내는
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(SEND)
                        .setUsernameVisibility(false)
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");

                Log.e("Log1","Log1");
                final MessageRequest request = new MessageRequest.Builder()
                        .inputText(SEND)
                        .context(context)
                        .build();

                Log.e("Log2","Log2");
                //response 받기
                myConversationService.message(getString(R.string.workspace), request).enqueue(new ServiceCallback<MessageResponse>() {
                    @Override
                    public void onResponse(final MessageResponse response) {

                        //???
                        Log.e("Log3","Log3");
                        //Map<String, Object> t_context = null;
                        context = response.getContext();
                        Log.e("context",context.toString());

                        Log.e("Log4","Log4");
                        //edu
                        if(context.get("eduCate") !=null) {

                            if (context.containsKey("eduCate")) {
                                temp.put("eduCate", context.get("eduCate").toString());
                            }
                            if (context.containsKey("edu_gu")) {
                                temp.put("edu_gu", context.get("edu_gu").toString());
                            }
                            if (context.containsKey("edu_date")) {
                                temp.put("edu_date", context.get("edu_date").toString());
                            }
                        }
                        Log.e("Log5","Log5");

                        if(context.get("cultureCate") !=null) {
                            //culture
                            if (context.containsKey("cultureCate")) {
                                temp.put("cultureCate", context.get("cultureCate").toString());
                            }
                            if (context.containsKey("culture_gu")) {
                                temp.put("culture_gu", context.get("culture_gu").toString());
                            }
                            if (context.containsKey("culture_date")) {
                                temp.put("culture_date", context.get("culture_date").toString());
                            }
                        }
                        //sport
                        Log.e("Log6","Log6");

                        if(context.get("sportCate") !=null) {

                            if (context.containsKey("sportCate")) {
                                temp.put("sportCate", context.get("sportCate").toString());
                            }
                            if (context.containsKey("sport_gu")) {
                                temp.put("sport_gu", context.get("sport_gu").toString());
                            }
                            if (context.containsKey("sport_date")) {
                                temp.put("sport_date", context.get("sport_date").toString());
                            }
                        }


                        Log.e("Log7","Log7");
                        if(context.get("instCate") !=null) {
                            //시설대관
                            if (context.containsKey("instCate")) {
                                temp.put("instCate", context.get("instCate").toString());
                            }
                            if (context.containsKey("inst_gu")) {
                                temp.put("inst_gu", context.get("inst_gu").toString());
                            }
                            if (context.containsKey("inst_date")) {
                                temp.put("inst_date", context.get("inst_date").toString());
                            }
                        }




                        Log.e("temp",temp.toString());
                        Log.e("Log8","Log8");
                        //봇이 내보내는 말
                        final String outputText = response.getText().get(0);

                        //output.get(action)을 위하여
                        final Map<String, Object> output = response.getOutput();
                        final String actionString = (String) output.get("action");

                        ///action 없음
                        if(actionString == null) {

                            Log.e("action_null","null");
                            //Receive message 받는

                                final Message receivedMessage = new Message.Builder()
                                        .setUser(you)
                                        .setRightMessage(false)
                                        .setMessageText(outputText)
                                        .build();

                                mChatView.receive(receivedMessage);

                        }


                        //action 있음
                        else {
                            Log.e("action_notnull","not null");
                            if (actionString != null) {

                                //번호 선택시 데이터베이스에저장 및 예약창
                                if (actionString.equals("select_number") == true) {

                                    Log.e("select_number","okokokokokokokokokok");
                                    String[] number = outputText.split(" ");
                                    int sid = Integer.parseInt(number[0]);
                                    SID.get(sid - 1);
                                    Log.e("SID",SID.get(sid - 1));
                                    //db에 저장

                                    //예약 페이지로이동

                                    String url = "http://yeyak.seoul.go.kr/mobile/detailView.web?rsvsvcid=" + SID.get(sid - 1);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);

                                    //String insert_data = INSERT.get(sid - 1);
                                    Log.e("insert_data",INSERT.get(sid - 1));
                                    //String insert = name + "/" + Min_class + "/" + sportgu + "/" + start_date[0] + "/" + finish_date[0];
                                    if(INSERT.get(sid - 1) != null) {
                                        String[] insert_data_parsing = INSERT.get(sid - 1).split("#");
                                        Log.e("parsing1","parsing1");
                                        Log.e("data1",insert_data_parsing[0]);
                                        Log.e("data2", insert_data_parsing[1]);
                                        Log.e("data3",insert_data_parsing[2]);
                                        Log.e("data4",insert_data_parsing[3] + "~" + insert_data_parsing[4]);
                                        //save_values(String sid, String name, String cate, String area, String date )
                                        save_values(SID.get(sid - 1), insert_data_parsing[0].replaceAll("'"," "), insert_data_parsing[1], insert_data_parsing[2], insert_data_parsing[3] + "~" + insert_data_parsing[4]);

                                        Log.e("parsing2","parsing2");
                                    }
                                    Log.e("parsing","good");

                                    final Message m = new Message.Builder()
                                            .setUser(you)
                                            .setRightMessage(false)
                                            .setMessageText("다른 [번호]를 선택하고 싶으시면 ☞'뒤로'☜ 라고 입력해주세요!!!")
                                            .build();
                                    mChatView.receive(m);

                                }//selectnumber



                                //재조회
                                else if(actionString.equals("reinqury_information")){

                                    //String insert = name + "#" + Min_class + "#" + instgu + "#" + start_date[0] + "#" + finish_date[0];
                                    //INSERT.add(insert);
                                    String[] temp;
                                    String t = null;
                                    String info ="";
                                    int num = 1;

                                    int count = INSERT.size();

                                    for(int i=0; i<count; i ++){

                                        String numStr = String.valueOf(num);
                                        num++;
                                        temp = INSERT.get(i).split("#");

                                        t = "[" + numStr + "]" + "\n" + "[이름] : " + temp[0] + "\n" +
                                                "[지역명] : " + temp[2] + "\n" +
                                                "[분류] : " + temp[1] + "\n" +
                                                "[접수 날짜] : " + temp[3] + "~" + temp[4] + "\n\n";

                                        info = info + t;

                                    }

                                    final Message m = new Message.Builder()
                                            .setUser(you)
                                            .setRightMessage(false)
                                            .setMessageText(info + "\n" + "예약 페이지로 이동하고 싶으시면 해당 ☞번호☜를 입력해주세요.")
                                            .build();
                                    mChatView.receive(m);


                                }






                                //sport_gu_print
                                else if(actionString.equals("print_sportgu_information")){
                                    //카테고리값
                                    final String sportCate = temp.get("sportCate");
                                    final String sportdate = temp.get("sport_date");

                                    Seoul_Sport_inqury_information("1","1000",sportCate,new Get_informationCallbacks() {
                                        boolean is_in = false;
                                        @Override
                                        public void onSuccess_sport(List<SeoulSportdetail> information) {

                                            List<SeoulSportdetail> list_information = information;

                                            ArrayList<String> GU_LIST = new ArrayList<String>();



                                            //temp 초기화
                                            temp.clear();

                                            Date dat = new Date();

                                            dat.setYear(Integer.parseInt(sportdate.substring(0, 4)));
                                            dat.setMonth(Integer.parseInt(sportdate.substring(5, 7)));
                                            dat.setDate(Integer.parseInt(sportdate.substring(8, 10)));

                                            Date dat2 = new Date(), dat3 = new Date();

                                            int i;
                                            int size = list_information.size();
                                            String info = "";
                                            int num = 1;
                                            int sizeofGU;
                                            boolean is_duplicated;
                                            for (i = 0; i < size; i++) {
                                                //날짜가 값이 있을때만
                                                if(list_information.get(i).getRCPTBGNDT() != "" || list_information.get(i).getRCPTENDDT() !="") {

                                                    dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                    dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                    dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                    dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                    dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                    dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                    if (list_information.get(i).getMINCLASSNM().contains(sportCate) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                        is_in = true;
                                                        String area = list_information.get(i).getAREANM();

                                                        sizeofGU = GU_LIST.size();
                                                        is_duplicated = false;

                                                        for(int j =0; j < sizeofGU; j++){

                                                            if(GU_LIST.get(j).equals(area) == true){
                                                                is_duplicated = true;
                                                                break;
                                                            }
                                                        }

                                                        if(is_duplicated == false)
                                                            GU_LIST.add(area);

                                                        //t = area+"/";
                                                        //info = info + t;

                                                    }
                                                }//if
                                            }//for

                                            if (is_in == true) {

                                                String temp = null;
                                                sizeofGU = GU_LIST.size();

                                                for(int k = 0; k< sizeofGU; k++){
                                                    temp = GU_LIST.get(k) + "/";
                                                    info += temp;
                                                }

                                                GU_LIST.clear();

                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText(outputText + "\n" + info + "\n\n" + "원하시는 [구]를 입력 해주세요")
                                                        .build();
                                                mChatView.receive(m);


                                            }//true

                                            else {


                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n ☞대화 다시하기☜\n'처음'또는 '재시작' ")
                                                        .build();
                                                mChatView.receive(m);


                                            }

                                        }

                                        @Override
                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}
                                        @Override
                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                        @Override
                                        public void onError() {}
                                        @Override
                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {}

                                    });

                                }



                                ///////서울시 체육시설 inqury
                                else if (actionString.equals("sport_inqury_information") == true){

                                    Log.e("sport_inqury","okokokokokokokokokok");
                                                    //카테고리값
                                                    final String sportCate = temp.get("sportCate");
                                                    final String sportdate = temp.get("sport_date");
                                                    final String sportgu = temp.get("sport_gu");

                                                    Seoul_Sport_inqury_information("1","1000",sportCate,new Get_informationCallbacks() {

                                                        @Override
                                                        public void onSuccess_sport(List<SeoulSportdetail> information) {

                                                            boolean is_in = false;
                                                            List<SeoulSportdetail> list_information = information;

                                                            //초기화
                                                            SID.clear();
                                                            INSERT.clear();
                                                            //Log.e("SID",SID.toString());



                                                            //temp 초기화
                                                            temp.clear();

                                                            Date dat = new Date();

                                                            dat.setYear(Integer.parseInt(sportdate.substring(0, 4)));
                                                            dat.setMonth(Integer.parseInt(sportdate.substring(5, 7)));
                                                            dat.setDate(Integer.parseInt(sportdate.substring(8, 10)));

                                                            Date dat2 = new Date(), dat3 = new Date();

                                                            int i;
                                                            int size = list_information.size();
                                                            String info = "";
                                                            int num = 1;
                                                            for (i = 0; i < size; i++) {
                                                                //날짜가 값이 있을때만
                                                                if(list_information.get(i).getRCPTBGNDT() != "" || list_information.get(i).getRCPTENDDT() !="") {

                                                                            dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                                            dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                                            dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                                            dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                                            dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                                            dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                                            if (list_information.get(i).getMINCLASSNM().contains(sportCate) && list_information.get(i).getAREANM().equals(sportgu) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                                                is_in = true;
                                                                                String t = null;
                                                                                String numStr = String.valueOf(num);
                                                                                num++;

                                                                                String id = list_information.get(i).getSVCID();
                                                                                SID.add(id);


                                                                                String Min_class = list_information.get(i).getMINCLASSNM();
                                                                                String name = list_information.get(i).getSVCNM();
                                                                                //String service_state = list_information.get(i).getSVCSTATNM();
                                                                                String service_Target = list_information.get(i).getUSETGTINFO();
                                                                                String reservation_start_date = list_information.get(i).getRCPTBGNDT();
                                                                                String reservation_finish_date = list_information.get(i).getRCPTENDDT();

                                                                                //index 0에는 날짜 index 1에는 시간
                                                                                String[] start_date = reservation_start_date.split(" ");
                                                                                String[] finish_date = reservation_finish_date.split(" ");

                                                                                //db
                                                                                String insert = name + "#" + Min_class + "#" + sportgu + "#" + start_date[0] + "#" + finish_date[0];
                                                                                INSERT.add(insert);


                                                                                t = "[" + numStr + "]" + "\n" + "[이름] : " + name + "\n" +
                                                                                        "[지역명] : " + sportgu + "\n" +
                                                                                        "[분류] : " + Min_class + "\n" +
                                                                                        "[접수 날짜] : " + start_date[0] + "~" + finish_date[0] + "\n\n";

                                                                                info = info + t;

                                                                            }
                                                                        }//if
                                                            }//for

                                                            if (is_in == true) {

                                                                final Message m = new Message.Builder()
                                                                        .setUser(you)
                                                                        .setRightMessage(false)
                                                                        .setMessageText(outputText + "\n\n" + info + "\n" + "예약 페이지로 이동하고 싶으시면 ☞번호☜를 입력해주세요.")
                                                                        .build();
                                                                mChatView.receive(m);


                                                            }//true

                                                            else {


                                                                final Message m = new Message.Builder()
                                                                        .setUser(you)
                                                                        .setRightMessage(false)
                                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n☞다시'카테고리'선택 \n'뒤로' \n ☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                                        .build();
                                                                mChatView.receive(m);


                                                            }

                                                        }

                                                        @Override
                                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}
                                                        @Override
                                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                                        @Override
                                                        public void onError() {}
                                                        @Override
                                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {}

                                                    });

                                }///체육시설 조회





                                //시설대관 구 프린트
                                else if(actionString.equals("print_instgu_information")){
                                    //카테고리값
                                    final String instCate = temp.get("instCate");
                                    final String instdate = temp.get("inst_date");

                                    Seoul_Institution_inqury_information("1","1000",instCate, new Get_informationCallbacks() {
                                        boolean is_in = false;

                                        @Override
                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {
                                            //List<SeoulInstitutiondetail> list_information = information;

                                            ArrayList<String> GU_LIST = new ArrayList<String>();
                                            Date dat = new Date();

                                            dat.setYear(Integer.parseInt(instdate.substring(0, 4)));
                                            dat.setMonth(Integer.parseInt(instdate.substring(5, 7)));
                                            dat.setDate(Integer.parseInt(instdate.substring(8, 10)));

                                            Date dat2 = new Date(), dat3 = new Date();

                                            int i;
                                            int size = information.size();
                                            String info = "";
                                            int num = 1;
                                            int sizeofGU;
                                            boolean is_duplicated;

                                            for (i = 0; i < size; i++) {
                                                //날짜가 값이 있을때만
                                                if(information.get(i).getRCPTBGNDT() != "" ) {

                                                    dat2.setYear(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                    dat2.setMonth(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                    dat2.setDate(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                    dat3.setYear(Integer.parseInt(information.get(i).getRCPTENDDT().substring(0, 4)));
                                                    dat3.setMonth(Integer.parseInt(information.get(i).getRCPTENDDT().substring(5, 7)));
                                                    dat3.setDate(Integer.parseInt(information.get(i).getRCPTENDDT().substring(8, 10)));

                                                    if (information.get(i).getMINCLASSNM().contains(instCate)&&dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && information.get(i).getSVCSTATNM().equals("접수중")) {
                                                        is_in = true;
                                                        String area = information.get(i).getAREANM();

                                                        sizeofGU = GU_LIST.size();
                                                        is_duplicated = false;

                                                        for(int j =0; j < sizeofGU; j++){

                                                            if(GU_LIST.get(j).equals(area) == true){
                                                                is_duplicated = true;
                                                                break;
                                                            }
                                                        }

                                                        if(is_duplicated == false)
                                                            GU_LIST.add(area);

                                                    }
                                                }//if
                                            }//for

                                            if (is_in == true) {

                                                String temp = null;
                                                sizeofGU = GU_LIST.size();

                                                for(int k = 0; k< sizeofGU; k++){
                                                    temp = GU_LIST.get(k) + "/";
                                                    info += temp;
                                                }

                                                GU_LIST.clear();

                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText(outputText + "\n" + info + "\n\n" + "원하시는 [구]를 입력 해주세요")
                                                        .build();
                                                mChatView.receive(m);


                                            }//true

                                            else {

                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                        .build();
                                                mChatView.receive(m);


                                            }

                                        }
                                        @Override
                                        public void onError() {}
                                        @Override
                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {}
                                        @Override
                                        public void onSuccess_sport(List<SeoulSportdetail> information) {}
                                        @Override
                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}

                                    });

                                    //temp 초기화
                                    temp.clear();



                                }



                                //시설대관 조회
                                else if(actionString.equals("inst_inqury_information") == true){

                                    final String instCate = temp.get("instCate");
                                    final String instdate = temp.get("inst_date");
                                    final String instgu = temp.get("inst_gu");

                                    Log.e("inst_inqury","okokokokokokokokokok");
                                                    Seoul_Institution_inqury_information("1","1000",instCate,new Get_informationCallbacks() {

                                                        @Override
                                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {

                                                            boolean is_in = false;
                                                            //List<SeoulInstitutiondetail> list_information = information;

                                                            //초기화
                                                            SID.clear();
                                                            INSERT.clear();


                                                            //temp 초기화
                                                            temp.clear();

                                                            Date dat = new Date();

                                                            dat.setYear(Integer.parseInt(instdate.substring(0, 4)));
                                                            dat.setMonth(Integer.parseInt(instdate.substring(5, 7)));
                                                            dat.setDate(Integer.parseInt(instdate.substring(8, 10)));

                                                            Date dat2 = new Date(), dat3 = new Date();


                                                            int i;
                                                            int size = information.size();
                                                            String info = "";
                                                            int num = 1;
                                                            for (i = 0; i < size; i++) {
                                                                //날짜가 값이 있을때만
                                                                if(information.get(i).getRCPTBGNDT() != "" || information.get(i).getRCPTENDDT() !="") {

                                                                    dat2.setYear(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                                    dat2.setMonth(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                                    dat2.setDate(Integer.parseInt(information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                                    dat3.setYear(Integer.parseInt(information.get(i).getRCPTENDDT().substring(0, 4)));
                                                                    dat3.setMonth(Integer.parseInt(information.get(i).getRCPTENDDT().substring(5, 7)));
                                                                    dat3.setDate(Integer.parseInt(information.get(i).getRCPTENDDT().substring(8, 10)));

                                                                    if (information.get(i).getMINCLASSNM().contains(instCate) && information.get(i).getAREANM().equals(instgu) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && information.get(i).getSVCSTATNM().equals("접수중")) {
                                                                        is_in = true;
                                                                        String t = null;
                                                                        String numStr = String.valueOf(num);
                                                                        num++;

                                                                        String id = information.get(i).getSVCID();
                                                                        SID.add(id);

                                                                        String Min_class = information.get(i).getMINCLASSNM();
                                                                        String name = information.get(i).getSVCNM();

                                                                        String reservation_start_date =information.get(i).getRCPTBGNDT();
                                                                        String reservation_finish_date = information.get(i).getRCPTENDDT();

                                                                        //index 0에는 날짜 index 1에는 시간
                                                                        String[] start_date = reservation_start_date.split(" ");
                                                                        String[] finish_date = reservation_finish_date.split(" ");

                                                                        //db
                                                                        String insert = name + "#" + Min_class + "#" + instgu + "#" + start_date[0] + "#" + finish_date[0];
                                                                        INSERT.add(insert);



                                                                        t = "[" + numStr + "]" + "\n" + "[이름] : " + name + "\n" +
                                                                                "[지역명] : " + instgu + "\n" +
                                                                                "[분류] : " + Min_class + "\n" +
                                                                                "[접수 날짜] : " + start_date[0] + "~" + finish_date[0] + "\n\n";

                                                                        info = info + t;

                                                                    }
                                                                }//if
                                                            }//for

                                                            if (is_in == true) {

                                                                final Message m = new Message.Builder()
                                                                        .setUser(you)
                                                                        .setRightMessage(false)
                                                                        .setMessageText(outputText + "\n" + info + "\n" + "예약 페이지로 이동하고 싶으시면 [번호]를 입력 해주세요.")
                                                                        .build();
                                                                mChatView.receive(m);


                                                            }//true

                                                            else {


                                                                final Message m = new Message.Builder()
                                                                        .setUser(you)
                                                                        .setRightMessage(false)
                                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                                        .build();
                                                                mChatView.receive(m);


                                                            }



                                                        }

                                                        @Override
                                                        public void onError() {}
                                                        @Override
                                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {}
                                                        @Override
                                                        public void onSuccess_sport(List<SeoulSportdetail> information) {}
                                                        @Override
                                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}


                                                    });

                                }//


                                //edu_gu_pirnt
                                else if(actionString.equals("print_edugu_information")){
                                            //카테고리값
                                            final String eduCate = temp.get("eduCate");
                                            final String edudate = temp.get("edu_date");

                                            Seoul_Education_inqury_information("1","1000",eduCate,new Get_informationCallbacks() {
                                                boolean is_in = false;

                                                @Override
                                                public void onSuccess_education(List<SeoulEducationdetail> information) {

                                                    List<SeoulEducationdetail> list_information = information;

                                                    ArrayList<String> GU_LIST = new ArrayList<String>();



                                                    //temp 초기화
                                                    temp.clear();

                                                    Date dat = new Date();

                                                    dat.setYear(Integer.parseInt(edudate.substring(0, 4)));
                                                    dat.setMonth(Integer.parseInt(edudate.substring(5, 7)));
                                                    dat.setDate(Integer.parseInt(edudate.substring(8, 10)));

                                                    Date dat2 = new Date(), dat3 = new Date();

                                                    int i;
                                                    int size = list_information.size();
                                                    String info = "";
                                                    int num = 1;
                                                    int sizeofGU;
                                                    boolean is_duplicated;

                                                    for (i = 0; i < size; i++) {
                                                        //날짜가 값이 있을때만
                                                        if(list_information.get(i).getRCPTBGNDT() != "" || list_information.get(i).getRCPTENDDT() !="") {

                                                            dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                            dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                            dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                            dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                            dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                            dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                            if (list_information.get(i).getMINCLASSNM().contains(eduCate) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                                is_in = true;
                                                                String area = list_information.get(i).getAREANM();

                                                                sizeofGU = GU_LIST.size();
                                                                is_duplicated = false;

                                                                for(int j =0; j < sizeofGU; j++){

                                                                    if(GU_LIST.get(j).equals(area) == true){
                                                                        is_duplicated = true;
                                                                        break;
                                                                    }
                                                                }

                                                                if(is_duplicated == false)
                                                                    GU_LIST.add(area);

                                                            }
                                                        }//if
                                                    }//for

                                                    if (is_in == true) {

                                                        String temp = null;
                                                        sizeofGU = GU_LIST.size();

                                                        for(int k = 0; k< sizeofGU; k++){
                                                            temp = GU_LIST.get(k) + "/";
                                                            info += temp;
                                                        }

                                                        GU_LIST.clear();

                                                        final Message m = new Message.Builder()
                                                                .setUser(you)
                                                                .setRightMessage(false)
                                                                .setMessageText(outputText + "\n" + info + "\n\n" + "원하시는 [구]를 입력 해주세요")
                                                                .build();
                                                        mChatView.receive(m);


                                                    }//true

                                                    else {

                                                        final Message m = new Message.Builder()
                                                                .setUser(you)
                                                                .setRightMessage(false)
                                                                .setMessageText("찾으시는 정보가 없습니다.\n\n☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                                .build();
                                                        mChatView.receive(m);

                                                    }

                                                }

                                                @Override
                                                public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                                @Override
                                                public void onError() {}
                                                @Override
                                                public void onSuccess_culture(List<SeoulCulturedetail> information) {}
                                                @Override
                                                public void onSuccess_sport(List<SeoulSportdetail> information) {}
                                            });



                                }


                                //edu_inqruy
                                else if(actionString.equals("edu_inqury_information") == true){

                                    //카테고리값
                                    final String eduCate = temp.get("eduCate");
                                    final String edudate = temp.get("edu_date");
                                    final String edugu = temp.get("edu_gu");

                                    Log.e("edu_inqury","okokokokokokokokokok");
                                            Seoul_Education_inqury_information("1","1000",eduCate,new Get_informationCallbacks() {

                                                @Override
                                                public void onSuccess_education(List<SeoulEducationdetail> information) {


                                                    boolean is_in = false;
                                                    List<SeoulEducationdetail> list_information = information;

                                                    //초기화
                                                    SID.clear();
                                                    INSERT.clear();
                                                    //temp 초기화
                                                    temp.clear();

                                                    Log.e("clear",temp.toString());

                                                    Date dat = new Date();

                                                    dat.setYear(Integer.parseInt(edudate.substring(0, 4)));
                                                    dat.setMonth(Integer.parseInt(edudate.substring(5, 7)));
                                                    dat.setDate(Integer.parseInt(edudate.substring(8, 10)));

                                                    Date dat2 = new Date(), dat3 = new Date();

                                                    int i;
                                                    int size = list_information.size();
                                                    String info = "";
                                                    int num = 1;
                                                    for (i = 0; i < size; i++) {
                                                        //날짜가 값이 있을때만
                                                        if(list_information.get(i).getRCPTBGNDT() != "") {

                                                            dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                            dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                            dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                            dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                            dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                            dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                            if (list_information.get(i).getMINCLASSNM().contains(eduCate) && list_information.get(i).getAREANM().equals(edugu) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                                is_in = true;
                                                                String t = null;
                                                                String numStr = String.valueOf(num);
                                                                num++;

                                                                String id = list_information.get(i).getSVCID();
                                                                SID.add(id);

                                                                String Min_class = list_information.get(i).getMINCLASSNM();
                                                                String name = list_information.get(i).getSVCNM();

                                                                String reservation_start_date = list_information.get(i).getRCPTBGNDT();
                                                                String reservation_finish_date = list_information.get(i).getRCPTENDDT();

                                                                //index 0에는 날짜 index 1에는 시간
                                                                String[] start_date = reservation_start_date.split(" ");
                                                                String[] finish_date = reservation_finish_date.split(" ");

                                                                //db
                                                                String insert = name + "#" + Min_class + "#" + edugu + "#" + start_date[0] + "#" + finish_date[0];
                                                                INSERT.add(insert);

                                                                t = "[" + numStr + "]" + "\n" + "[이름] : " + name + "\n" +
                                                                        "[지역명] : " + edugu + "\n" +
                                                                        "[분류] : " + Min_class + "\n" +
                                                                        "[접수 날짜] : " + start_date[0] + "~" + finish_date[0] + "\n\n";

                                                                info = info + t;

                                                            }
                                                        }//if
                                                    }//for

                                                    if (is_in == true) {

                                                        final Message m = new Message.Builder()
                                                                .setUser(you)
                                                                .setRightMessage(false)
                                                                .setMessageText(outputText + "\n" + info + "\n" + "예약 페이지로 이동하고 싶으시면 [번호]를 입력하세요.")
                                                                .build();
                                                        mChatView.receive(m);


                                                    }//true

                                                    else {


                                                        final Message m = new Message.Builder()
                                                                .setUser(you)
                                                                .setRightMessage(false)
                                                                .setMessageText("찾으시는 정보가 없습니다 \n\n ☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                                .build();
                                                        mChatView.receive(m);


                                                    }



                                                }

                                                @Override
                                                public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                                @Override
                                                public void onError() {}
                                                @Override
                                                public void onSuccess_culture(List<SeoulCulturedetail> information) {}
                                                @Override
                                                public void onSuccess_sport(List<SeoulSportdetail> information) {}
                                            });




                                }//edu



                                //컬처 구 프린트
                                else if(actionString.equals("print_culturegu_information")){
                                    Seoul_Culture_inqury_information(new Get_informationCallbacks() {
                                        boolean is_in = false;
                                        @Override
                                        public void onError() {}

                                        @Override
                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {
                                            List<SeoulCulturedetail> list_information = information;

                                            ArrayList<String> GU_LIST = new ArrayList<String>();

                                            //카테고리값
                                            String cultureCate = temp.get("cultureCate");
                                            String culturedate = temp.get("culture_date");

                                            //temp 초기화
                                            temp.clear();

                                            Date dat = new Date();

                                            dat.setYear(Integer.parseInt(culturedate.substring(0, 4)));
                                            dat.setMonth(Integer.parseInt(culturedate.substring(5, 7)));
                                            dat.setDate(Integer.parseInt(culturedate.substring(8, 10)));

                                            Date dat2 = new Date(), dat3 = new Date();

                                            int i;
                                            int size = list_information.size();
                                            String info = "";
                                            int num = 1;
                                            int sizeofGU;
                                            boolean is_duplicated;
                                            for (i = 0; i < size; i++) {
                                                //날짜가 값이 있을때만
                                                if(list_information.get(i).getRCPTBGNDT() != "" || list_information.get(i).getRCPTENDDT() !="") {

                                                    dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                    dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                    dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                    dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                    dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                    dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                    if (list_information.get(i).getMINCLASSNM().contains(cultureCate) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                        is_in = true;
                                                        String area = list_information.get(i).getAREANM();

                                                        sizeofGU = GU_LIST.size();
                                                        is_duplicated = false;

                                                        for(int j =0; j < sizeofGU; j++){

                                                            if(GU_LIST.get(j).equals(area) == true){
                                                                is_duplicated = true;
                                                                break;
                                                            }
                                                        }

                                                        if(is_duplicated == false)
                                                            GU_LIST.add(area);

                                                        //t = area+"/";
                                                        //info = info + t;

                                                    }
                                                }//if
                                            }//for

                                            if (is_in == true) {

                                                String temp = null;
                                                sizeofGU = GU_LIST.size();

                                                for(int k = 0; k< sizeofGU; k++){
                                                    temp = GU_LIST.get(k) + "/";
                                                    info += temp;
                                                }

                                                GU_LIST.clear();

                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText(outputText + "\n" + info + "\n\n" + "원하시는 [구]를 입력 해주세요.")
                                                        .build();
                                                mChatView.receive(m);


                                            }//true

                                            else {


                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                        .build();
                                                mChatView.receive(m);


                                            }
                                        }

                                        @Override
                                        public void onSuccess_sport(List<SeoulSportdetail> information) {}

                                        @Override
                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}

                                        @Override
                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                    });
                                }


                                //문화 조회
                                else if(actionString.equals("culture_inqury_information")){
                                    Log.e("culture_inqury","okokokokokokokokokok");

                                    Seoul_Culture_inqury_information(new Get_informationCallbacks() {
                                        @Override
                                        public void onError() {}

                                        @Override
                                        public void onSuccess_culture(List<SeoulCulturedetail> information) {


                                            boolean is_in = false;
                                            List<SeoulCulturedetail> list_information = information;

                                            //초기화
                                            SID.clear();
                                            INSERT.clear();

                                            //카테고리값
                                            String cultureCate = temp.get("cultureCate");
                                            String culturedate = temp.get("culture_date");
                                            String culturegu = temp.get("culture_gu");

                                            //temp 초기화
                                            temp.clear();

                                            Log.e("clear",temp.toString());

                                            Date dat = new Date();

                                            dat.setYear(Integer.parseInt(culturedate.substring(0, 4)));
                                            dat.setMonth(Integer.parseInt(culturedate.substring(5, 7)));
                                            dat.setDate(Integer.parseInt(culturedate.substring(8, 10)));

                                            Date dat2 = new Date(), dat3 = new Date();

                                            int i;
                                            int size = list_information.size();
                                            String info = "";
                                            int num = 1;
                                            for (i = 0; i < size; i++) {
                                                //날짜가 값이 있을때만
                                                if(list_information.get(i).getRCPTBGNDT() != "") {

                                                    dat2.setYear(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(0, 4)));
                                                    dat2.setMonth(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(5, 7)));
                                                    dat2.setDate(Integer.parseInt(list_information.get(i).getRCPTBGNDT().substring(8, 10)));

                                                    dat3.setYear(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(0, 4)));
                                                    dat3.setMonth(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(5, 7)));
                                                    dat3.setDate(Integer.parseInt(list_information.get(i).getRCPTENDDT().substring(8, 10)));

                                                    if (list_information.get(i).getMINCLASSNM().contains(cultureCate) && list_information.get(i).getAREANM().equals(culturegu) && dat.compareTo(dat2) >= 0 && dat.compareTo(dat3) <= 0 && list_information.get(i).getSVCSTATNM().equals("접수중")) {
                                                        is_in = true;
                                                        String t = null;
                                                        String numStr = String.valueOf(num);
                                                        num++;

                                                        String id = list_information.get(i).getSVCID();
                                                        SID.add(id);

                                                        String Min_class = list_information.get(i).getMINCLASSNM();
                                                        String name = list_information.get(i).getSVCNM();
                                                        //String service_state = list_information.get(i).getSVCSTATNM();
                                                        String reservation_start_date = list_information.get(i).getRCPTBGNDT();
                                                        String reservation_finish_date = list_information.get(i).getRCPTENDDT();


                                                        //index 0에는 날짜 index 1에는 시간
                                                        String[] start_date = reservation_start_date.split(" ");
                                                        String[] finish_date = reservation_finish_date.split(" ");

                                                        //db
                                                        String insert = name + "#" + Min_class + "#" + culturegu + "#" + start_date[0] + "#" + finish_date[0];
                                                        INSERT.add(insert);

                                                        t = "[" + numStr + "]" + "\n" + "[이름] : " + name + "\n" +
                                                                "[지역명] : " + culturegu + "\n" +
                                                                "[분류] : " + Min_class + "\n" +
                                                                "[접수 날짜] : " + start_date[0] + "~" + finish_date[0] + "\n\n";

                                                        info = info + t;

                                                    }
                                                }//if
                                            }//for

                                            if (is_in == true) {

                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText(outputText + "\n\n" + info + "\n" + "예약 페이지로 이동하고 싶으시면 [번호]를 입력하세요.")
                                                        .build();
                                                mChatView.receive(m);


                                            }//true

                                            else {


                                                final Message m = new Message.Builder()
                                                        .setUser(you)
                                                        .setRightMessage(false)
                                                        .setMessageText("찾으시는 정보가 없습니다 \n\n☞대화 다시하기 \n '처음'또는 '재시작' ")
                                                        .build();
                                                mChatView.receive(m);


                                            }


                                        }

                                        @Override
                                        public void onSuccess_sport(List<SeoulSportdetail> information) {}

                                        @Override
                                        public void onSuccess_education(List<SeoulEducationdetail> information) {}

                                        @Override
                                        public void onSuccess_institution(List<SeoulInstitutiondetail> information) {}
                                    });




                                }


                            }//action string not null
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });



            }

        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }



}


