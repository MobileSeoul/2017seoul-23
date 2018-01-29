package com.example.naejang.chat_bot_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */

public interface SeoulEducationApiService  {
    @GET("{apikey}/json/ListPublicReservationEducation/{first}/{last}/{edu}")
    Call<SeoulEducation> get_seoul_educationList(
            @Path("apikey") String apikey,
             @Path("first") String first,
            @Path("last") String last,
            @Path("edu") String edu
    );


}