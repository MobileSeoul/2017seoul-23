package com.example.naejang.chat_bot_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by naejang on 2017-09-22.
 */
public interface SeoulCultureApiService {
    //@GET의 내용은 공공데이터 페이지를 조금만 살펴보면 알 수 있다.
    @GET("{apikey}/json/ListPublicReservationCulture/1/100/")
    Call<SeoulCulture> get_seoul_cultureList(
            @Path("apikey") String apikey
    );


}
