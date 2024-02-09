package com.google.fajarpro.retrofit;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import com.google.fajarpro.response.GetChannelResponse;
import com.google.fajarpro.response.GetHotelProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {
    @GET("tv")
    Call<GetChannelResponse> getUrl(@Header("X-API-KEY") String apikey);

    @GET("profile")
    Call<GetHotelProfileResponse> getHotelProfile(@Header("Authorization") String apikey);


}
