package com.google.fajarpro.response;

import java.util.List;

public class GetChannelResponse {
    private int code;
    private String message;
    private List<Data> data;

    public GetChannelResponse(int code, String message, List<Data> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data{
        private String name;
        private String url;
        private String icon;

        public Data(String channel_name, String url, String icon) {
            this.name = channel_name;
            this.url = url;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getIcon() {
            return icon;
        }
    }
}
