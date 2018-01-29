package com.example.naejang.chat_bot_project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by naejang on 2017-09-20.
 */

public class ServiceGenerator {
    private static final String SW_API_ROOT_URL = "http://openAPI.seoul.go.kr:8088/";

    /**
     * Get Retrofit Instance
     */
    //baseUrl은 /로 끝나야 한다.
    private static Retrofit getListInstance() {
        return new Retrofit.Builder()
                .baseUrl(SW_API_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //서울시 문화정보
    public static SeoulCultureApiService getSeoul_Culture_ListApiService() {
        return getListInstance().create(SeoulCultureApiService.class);
    }


    //서울시 스포츠
    public static SeoulSportApiService getSeoul_Sport_ListApiService() {
        return getListInstance().create(SeoulSportApiService.class);
    }

    //서울시 교육
    public static SeoulEducationApiService getSeoul_Education_ListApiService() {
        return getListInstance().create(SeoulEducationApiService.class);
    }
    //서울시 시설대관
    public static SeoulInstitutionApiService getSeoul_Institution_ListApiService() {
        return getListInstance().create(SeoulInstitutionApiService.class);
    }

}

