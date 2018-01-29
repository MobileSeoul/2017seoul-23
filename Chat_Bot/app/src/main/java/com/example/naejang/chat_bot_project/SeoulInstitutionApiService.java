package com.example.naejang.chat_bot_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 홍휘랑 on 2017-09-29.
 */

public interface SeoulInstitutionApiService  {
    @GET("{apikey}/json/ListPublicReservationInstitution/{first}/{last}/{inst}")
    Call<SeoulInstitution> get_seoul_institutionList(
            @Path("apikey") String apikey,
            @Path("first") String first,
            @Path("last") String last,
            @Path("inst") String inst
    );


}