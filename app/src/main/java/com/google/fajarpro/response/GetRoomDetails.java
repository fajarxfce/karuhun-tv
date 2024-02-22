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

    public class Data {
        private int id;
        private String guest_name;
        private String greeting;
        private String no;
        private String device_name;
        private int is_birthday;
        private Hotel hotel;

        public Data(int id, String guest_name, String greeting, String device_name, String no, int is_birthday, Hotel hotel) {
            this.id = id;
            this.guest_name = guest_name;
            this.greeting = greeting;
            this.device_name = device_name;
            this.no = no;
            this.is_birthday = is_birthday;
            this.hotel = hotel;
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

        public Hotel getHotel() {
            return hotel;
        }

        public class Hotel {
            private int id;
            private String name;
            private String branch;
            private String address;
            private String phone;
            private String email;
            private String website;
            private String default_greeting;
            private int is_active;
            private String password_setting;

            public Hotel(int id, String name, String branch, String address, String phone, String email, String website, String default_greeting, int is_active, String password_setting) {
                this.id = id;
                this.name = name;
                this.branch = branch;
                this.address = address;
                this.phone = phone;
                this.email = email;
                this.website = website;
                this.default_greeting = default_greeting;
                this.is_active = is_active;
                this.password_setting = password_setting;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getBranch() {
                return branch;
            }

            public String getAddress() {
                return address;
            }

            public String getPhone() {
                return phone;
            }

            public String getEmail() {
                return email;
            }

            public String getWebsite() {
                return website;
            }

            public String getDefault_greeting() {
                return default_greeting;
            }

            public int getIs_active() {
                return is_active;
            }

            public String getPassword_setting() {
                return password_setting;
            }
        }
    }
}

