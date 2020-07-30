package com.wazir.warehousing.ModelObject;

import java.util.Date;

public class NoticeObject {
    int pri_str;
    int cate;
    String title, description, temp, moist, location;
    Date date;

    public NoticeObject() {
    }

    public void setData(int category) {
        this.cate = category;
        setContent(this.cate);
    }

    void setContent(int category) {
        switch (category) {
            case (1):
                this.title = "Temperature Rising";
                this.description = this.temp + " C Rise in temperature has been Detected";
                break;
            case (2):
                this.title = "Moisture Increasing";
                this.description = "There are elevated levels of Moisture in " + this.location + " to " + this.moist + "%";
                break;
        }
    }

    public int getPri_str() {
        return pri_str;
    }

    public void setPri_str(int pri_str) {
        this.pri_str = pri_str;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
