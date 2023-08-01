package com.example.smartcity.entry;

import java.util.List;

/**
 * @author Amy
 * @date -
 **/
public class WeatherBean {

    private String city;
    private String cityEn;
    private String cityid;
    private String country;
    private String countryEn;
    private List<DataDTO> data;
    private String update_time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "city='" + city + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", cityid='" + cityid + '\'' +
                ", country='" + country + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", data=" + data +
                ", update_time='" + update_time + '\'' +
                '}';
    }

    public static class DataDTO {
        private String air;
        private String air_level;
        private String air_tips;
        private AlarmDTO alarm;
        private String date;
        private String day;
        private List<HoursDTO> hours;
        private String humidity;
        private List<IndexDTO> index;
        private String pressure;
        private String sunrise;
        private String sunset;
        private String tem;
        private String tem1;
        private String tem2;
        private String visibility;
        private String wea;
        private String wea_day;
        private String wea_day_img;
        private String wea_img;
        private String wea_night;
        private String wea_night_img;
        private String week;
        private List<String> win;
        private String win_meter;
        private String win_speed;



        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getAir_level() {
            return air_level;
        }

        public void setAir_level(String air_level) {
            this.air_level = air_level;
        }

        public String getAir_tips() {
            return air_tips;
        }

        public void setAir_tips(String air_tips) {
            this.air_tips = air_tips;
        }

        public AlarmDTO getAlarm() {
            return alarm;
        }

        public void setAlarm(AlarmDTO alarm) {
            this.alarm = alarm;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public List<HoursDTO> getHours() {
            return hours;
        }

        public void setHours(List<HoursDTO> hours) {
            this.hours = hours;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public List<IndexDTO> getIndex() {
            return index;
        }

        public void setIndex(List<IndexDTO> index) {
            this.index = index;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getTem1() {
            return tem1;
        }

        public void setTem1(String tem1) {
            this.tem1 = tem1;
        }

        public String getTem2() {
            return tem2;
        }

        public void setTem2(String tem2) {
            this.tem2 = tem2;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getWea() {
            return wea;
        }

        public void setWea(String wea) {
            this.wea = wea;
        }

        public String getWea_day() {
            return wea_day;
        }

        public void setWea_day(String wea_day) {
            this.wea_day = wea_day;
        }

        public String getWea_day_img() {
            return wea_day_img;
        }

        public void setWea_day_img(String wea_day_img) {
            this.wea_day_img = wea_day_img;
        }

        public String getWea_img() {
            return wea_img;
        }

        public void setWea_img(String wea_img) {
            this.wea_img = wea_img;
        }

        public String getWea_night() {
            return wea_night;
        }

        public void setWea_night(String wea_night) {
            this.wea_night = wea_night;
        }

        public String getWea_night_img() {
            return wea_night_img;
        }

        public void setWea_night_img(String wea_night_img) {
            this.wea_night_img = wea_night_img;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public List<String> getWin() {
            return win;
        }

        public void setWin(List<String> win) {
            this.win = win;
        }

        public String getWin_meter() {
            return win_meter;
        }

        public void setWin_meter(String win_meter) {
            this.win_meter = win_meter;
        }

        public String getWin_speed() {
            return win_speed;
        }

        public void setWin_speed(String win_speed) {
            this.win_speed = win_speed;
        }


        @Override
        public String toString() {
            return "DataDTO{" +
                    "air='" + air + '\'' +
                    ", air_level='" + air_level + '\'' +
                    ", air_tips='" + air_tips + '\'' +
                    ", alarm=" + alarm +
                    ", date='" + date + '\'' +
                    ", day='" + day + '\'' +
                    ", hours=" + hours +
                    ", humidity='" + humidity + '\'' +
                    ", index=" + index +
                    ", pressure='" + pressure + '\'' +
                    ", sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    ", tem='" + tem + '\'' +
                    ", tem1='" + tem1 + '\'' +
                    ", tem2='" + tem2 + '\'' +
                    ", visibility='" + visibility + '\'' +
                    ", wea='" + wea + '\'' +
                    ", wea_day='" + wea_day + '\'' +
                    ", wea_day_img='" + wea_day_img + '\'' +
                    ", wea_img='" + wea_img + '\'' +
                    ", wea_night='" + wea_night + '\'' +
                    ", wea_night_img='" + wea_night_img + '\'' +
                    ", week='" + week + '\'' +
                    ", win=" + win +
                    ", win_meter='" + win_meter + '\'' +
                    ", win_speed='" + win_speed + '\'' +
                    '}';
        }

        public static class AlarmDTO {
            private String alarm_content;
            private String alarm_level;
            private String alarm_type;

            public String getAlarm_content() {
                return alarm_content;
            }

            public void setAlarm_content(String alarm_content) {
                this.alarm_content = alarm_content;
            }

            public String getAlarm_level() {
                return alarm_level;
            }

            public void setAlarm_level(String alarm_level) {
                this.alarm_level = alarm_level;
            }

            public String getAlarm_type() {
                return alarm_type;
            }

            public void setAlarm_type(String alarm_type) {
                this.alarm_type = alarm_type;
            }

            @Override
            public String toString() {
                return "AlarmDTO{" +
                        "alarm_content='" + alarm_content + '\'' +
                        ", alarm_level='" + alarm_level + '\'' +
                        ", alarm_type='" + alarm_type + '\'' +
                        '}';
            }
        }

        public static class HoursDTO {
            private String hours;
            private String tem;
            private String wea;
            private String wea_img;
            private String win;
            private String win_speed;

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getTem() {
                return tem;
            }

            public void setTem(String tem) {
                this.tem = tem;
            }

            public String getWea() {
                return wea;
            }

            public void setWea(String wea) {
                this.wea = wea;
            }

            public String getWea_img() {
                return wea_img;
            }

            public void setWea_img(String wea_img) {
                this.wea_img = wea_img;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public String getWin_speed() {
                return win_speed;
            }

            public void setWin_speed(String win_speed) {
                this.win_speed = win_speed;
            }

            @Override
            public String toString() {
                return "HoursDTO{" +
                        "hours='" + hours + '\'' +
                        ", tem='" + tem + '\'' +
                        ", wea='" + wea + '\'' +
                        ", wea_img='" + wea_img + '\'' +
                        ", win='" + win + '\'' +
                        ", win_speed='" + win_speed + '\'' +
                        '}';
            }
        }

        public static class IndexDTO {
            private String desc;
            private String level;
            private String title;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "IndexDTO{" +
                        "desc='" + desc + '\'' +
                        ", level='" + level + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }
    }
}


