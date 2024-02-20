package com.google.fajarpro.retrofit;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import com.google.fajarpro.response.GetChannelResponse;
import com.google.fajarpro.response.GetHotelProfileResponse;
import com.google.fajarpro.response.GetRoomDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Api {
    @GET("tv")
    Call<GetChannelResponse> getUrl(@Header("X-API-KEY") String apikey);

    @GET("hotel")
    Call<GetHotelProfileResponse> getHotelProfile(@Header("X-API-KEY") String apikey);

    @GET("room/{id}")
    Call<GetRoomDetails> getRoomDetails(@Header("X-API-KEY") String apikey,
                                        @Path("id") String id);


}
