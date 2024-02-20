package com.google.fajarpro.response;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


public class GetRoomDetails {
    private int code;
    private String message;
    private Data data;

    public GetRoomDetails(int code, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    public class Data{
        private int id;
        private String guest_name;
        private String greeting;
        private String no;
        private String device_name;
        private int is_birthday;
        public Data(int id, String guest_name, String greeting, String device_name, String no, int is_birthday) {
            this.id = id;
            this.guest_name = guest_name;
            this.greeting = greeting;
            this.device_name = device_name;
            this.no = no;
            this.is_birthday = is_birthday;
        }

        public int getId() {
            return id;
        }

        public String getGuest_name() {
            return guest_name;
        }

        public String getGreeting() {
            return greeting;
        }

        public String getDevice_name() {
            return device_name;
        }

        public String getNo() {
            return no;
        }

        public int getIs_birthday() {
            return is_birthday;
        }
    }
}

