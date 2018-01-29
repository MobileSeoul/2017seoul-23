package com.example.naejang.chat_bot_project;


import java.util.List;

/**
 * Created by naejang on 2017-09-22.
 */
public interface Get_informationCallbacks {

    void onError();
    void onSuccess_culture(List<SeoulCulturedetail> information);
    void onSuccess_sport(List<SeoulSportdetail> information);
    void onSuccess_education(List<SeoulEducationdetail> information);
    void onSuccess_institution(List<SeoulInstitutiondetail> information);
}