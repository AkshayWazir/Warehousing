package com.wazir.warehousing.ModelObject;

import static com.wazir.warehousing.GloabalFunctions.Constants.N_FIRE;
import static com.wazir.warehousing.GloabalFunctions.Constants.N_FLOOD;
import static com.wazir.warehousing.GloabalFunctions.Constants.N_HUMID;
import static com.wazir.warehousing.GloabalFunctions.Constants.N_LIGHT;
import static com.wazir.warehousing.GloabalFunctions.Constants.N_TEMP;

public class NoticeObject {
    String date, title, desc;
    String cate;
    String temp, moist, light, loc;

    public NoticeObject() {
    }

    public NoticeObject(String date, String cate, String temp, String moist, String light, String loc) {
        this.date = date;
        this.cate = cate;
        this.temp = temp;
        this.moist = moist;
        this.light = light;
        this.loc = loc;
        makeNotice();
    }

    public void makeNotice() {
        switch (this.cate) {
            case N_FIRE:
                this.title = "Fire Alert";
                this.desc = this.loc + " is under Fire, please proceed with caution and apply appropriate measures";
                break;
            case N_FLOOD:
                this.title = "Flood Alert";
                this.desc = this.loc + " is Flooded, please proceed with caution and apply appropriate measures";
                break;
            case N_HUMID:
                this.title = "Humidity Surge";
                this.desc = this.loc + " has sudden surge of Humidity of about " + this.moist + "% kindly look into it.";
                break;
            case N_LIGHT:
                this.title = "Light Intensity";
                this.desc = this.loc + " has increased light intensity of about " + this.light + "% kindly look into it";
                break;
            case N_TEMP:
                this.title = "Temperature Variations";
                this.desc = this.loc + " has increased temperature of about " + this.temp + "C kindly look into it";
                break;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMoist() {
        return moist;
    }

    public void setMoist(String moist) {
        this.moist = moist;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
