package com.google.fajarpro;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.content.Context;
import android.widget.Toast;

import com.google.fajarpro.response.GetHotelProfileResponse;
import com.google.fajarpro.response.GetRoomDetails;
import com.google.fajarpro.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelProfile {
    public boolean initiated = false;
    public String logoWhite = "";
    public String primaryColor = "";

    public interface Listener {
        void onGetProfile(String logoWhite, String str2);
    }

    public interface RoomListener {
        void onGetRoomDetails(String guestName);
    }

    public void getProfile(Context context, final String apiKey, final Listener listener) {
        Call<GetHotelProfileResponse> call = RetrofitClient.getInstance().
                getApi().
                getHotelProfile(apiKey);
        call.enqueue(new Callback<GetHotelProfileResponse>() {
            @Override
            public void onResponse(Call<GetHotelProfileResponse> call, Response<GetHotelProfileResponse> response) {
                if (response.isSuccessful()) {
                    GetHotelProfileResponse profile = response.body();


                    logoWhite = profile.getData().getProfile().getLogo_white();
                    String string = "#C09C50";
                    primaryColor = string;
                    initiated = true;
                    listener.onGetProfile(logoWhite, string);

                } else {
//                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetHotelProfileResponse> call, Throwable t) {
//                Toast.makeText(context, "Gagal mendapatkan response dari server bang", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRoomDetail(Context context, final String apiKey, String id, final RoomListener listener) {
        Call<GetRoomDetails> call = RetrofitClient.getInstance().
                getApi().
                getRoomDetails(apiKey, id);
        call.enqueue(new Callback<GetRoomDetails>() {
            @Override
            public void onResponse(Call<GetRoomDetails> call, Response<GetRoomDetails> response) {
                if (response.isSuccessful()) {
                    GetRoomDetails room = response.body();
                    String guestName = room.getData().getGuest_name();
                    initiated = true;
                    listener.onGetRoomDetails(guestName);

                } else {
//                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetRoomDetails> call, Throwable t) {
//                Toast.makeText(context, "Gagal mendapatkan response dari server bang", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
