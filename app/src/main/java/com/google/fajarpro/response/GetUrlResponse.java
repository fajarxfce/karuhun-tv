package com.google.fajarpro.response;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


public class GetUrlResponse {
    private int response_code;
    private String message;
    private Data data;

    public GetUrlResponse(int response_code, String message, Data data) {
        this.response_code = response_code;
        this.message = message;
        this.data = data;
    }

    public int getResponse_code() {
        return response_code;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data{
        private String url;
        private String cctv;
        private String vod;

        public Data(String url, String cctv, String vod) {
            this.url = url;
            this.cctv = cctv;
            this.vod = vod;
        }

        public String getUrl() {
            return url;
        }

        public String getCctv() {
            return cctv;
        }

        public String getVod() {
            return vod;
        }
    }
}
