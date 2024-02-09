package com.google.fajarpro.m3u;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import retrofit2.Response;

public class M3ULoader {
    private OnPlaylistLoadedListener listener;

    /* renamed from: com.infinitv.tv.m3u.M3ULoader$OnPlaylistLoadedListener */
    /* loaded from: classes4.dex */
    public interface OnPlaylistLoadedListener {
        void onPlaylistLoaded(M3UPlaylist m3UPlaylist);
    }

    public void setOnItemClickListener(OnPlaylistLoadedListener listener) {
        this.listener = listener;
    }

    public void load(Context context, String apiKey) {
//        getUrl(context, apiKey);
    }

//    public void getUrl(Context context, final String apiKey) {
//        Log.d("TAG", "getUrl: asu");
//        Call<GetUrlResponse> call = RetrofitClient.getInstance().
//                getApi().
//                getUrl();
//        call.enqueue(new Callback<GetUrlResponse>() {
//            private static final String TAG = "TAG";
//
//            @Override
//            public void onResponse(Call<GetUrlResponse> call, Response<GetUrlResponse> response) {
//                if (response.isSuccessful()) {
//                    GetUrlResponse getUrlResponse = response.body();
//                    String m3uUrl = getUrlResponse.getData().getUrl();
//                    Log.d(TAG, "onResponse: "+getUrlResponse.getData().getUrl());
//                    getM3U(context, m3uUrl);
//
//
//                } else {
//                    Log.d(TAG, "onResponse: "+response.message());
//                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<GetUrlResponse> call, Throwable t) {
//                Toast.makeText(context, "Gagal mendapatkan response dari server njir", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void getM3U(final Context context, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonReq = new StringRequest(0, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                M3UParser parser = new M3UParser();
                M3UPlaylist playlist = parser.parse(response);
                OnPlaylistLoadedListener onPlaylistLoadedListener = listener;
                if (onPlaylistLoadedListener != null) {
                    onPlaylistLoadedListener.onPlaylistLoaded(playlist);
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Can't load M3u", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonReq);

    }


}
