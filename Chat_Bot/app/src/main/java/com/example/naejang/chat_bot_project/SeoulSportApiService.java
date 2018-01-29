package com.example.naejang.chat_bot_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeoulSportApiService {
    //@GET의 내용은 공공데이터 페이지를 조금만 살펴보면 알 수 있다.
    @GET("{apikey}/json/ListPublicReservationSport/{first}/{last}/{sport}")
    Call<SeoulSport> get_seoul_sportList(
            @Path("apikey") String apikey,
            @Path("first") String first,
            @Path("last") String last,
            @Path("sport") String sport
    );


}