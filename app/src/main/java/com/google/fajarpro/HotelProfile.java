package com.google.fajarpro;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.content.Context;
import android.widget.Toast;

import com.google.fajarpro.response.GetHotelProfileResponse;
import com.google.fajarpro.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelProfile {
    public boolean initiated = false;
    public String logoWhite = "";
    public String primaryColor = "";

    public interface Listener {
        void onGetProfile(String str, String str2);
    }

    public void getProfile(Context context, final String apiKey, final Listener listener) {
        Call<GetHotelProfileResponse> call = RetrofitClient.getInstance().
                getApi().
                getHotelProfile(apiKey);
        call.enqueue(new Callback<GetHotelProfileResponse>() {
            @Override
            public void onResponse(Call<GetHotelProfileResponse> call, Response<GetHotelProfileResponse> response) {
                if (response.isSuccessful()) {
                    GetHotelProfileResponse getHotelProfileResponse = response.body();

                    logoWhite = "30_profiles_LOGO WHITE.png";
                    String string = "#C09C50";
                    primaryColor = string;
                    initiated = true;
                    listener.onGetProfile(logoWhite, string);

                } else {
                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetHotelProfileResponse> call, Throwable t) {
                Toast.makeText(context, "Gagal mendapatkan response dari server bang", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
