package com.google.fajarpro.response;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


public class GetHotelProfileResponse {
    private int code;
    private String message;
    private Data data;

    public GetHotelProfileResponse(int code, String message, Data data) {
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
        private String name;
        private String branch;
        private String address;
        private String phone;
        private String email;
        private String website;
        private String default_greeting;
        private String created_at;
        private String updated_at;
        private int is_active;
        private String password_setting;
        private Profile profile;

        public Data(int id, String name, String branch, String address, String phone, String email, String website, String default_greeting, String created_at, String updated_at, int is_active, String password_setting, Profile profile) {
            this.id = id;
            this.name = name;
            this.branch = branch;
            this.address = address;
            this.phone = phone;
            this.email = email;
            this.website = website;
            this.default_greeting = default_greeting;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.is_active = is_active;
            this.password_setting = password_setting;
            this.profile = profile;
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

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getIs_active() {
            return is_active;
        }

        public String getPassword_setting() {
            return password_setting;
        }

        public Profile getProfile() {
            return profile;
        }

        public class Profile {
            private int id;
            private int hotel_id;
            private String logo_color;
            private String logo_white;
            private String logo_black;
            private String primary_color;
            private String description;
            private String main_photo;
            private String background_photo;
            private String intro_video;

            public Profile(int id, int hotel_id, String logo_color, String logo_white, String logo_black, String primary_color, String description, String main_photo, String background_photo, String intro_video) {
                this.id = id;
                this.hotel_id = hotel_id;
                this.logo_color = logo_color;
                this.logo_white = logo_white;
                this.logo_black = logo_black;
                this.primary_color = primary_color;
                this.description = description;
                this.main_photo = main_photo;
                this.background_photo = background_photo;
                this.intro_video = intro_video;
            }

            public int getId() {
                return id;
            }

            public int getHotel_id() {
                return hotel_id;
            }

            public String getLogo_color() {
                return logo_color;
            }

            public String getLogo_white() {
                return logo_white;
            }

            public String getLogo_black() {
                return logo_black;
            }

            public String getPrimary_color() {
                return primary_color;
            }

            public String getDescription() {
                return description;
            }

            public String getMain_photo() {
                return main_photo;
            }

            public String getBackground_photo() {
                return background_photo;
            }

            public String getIntro_video() {
                return intro_video;
            }
        }
    }
}
