package com.wazir.warehousing.ModelObject;

public class TitleObj {
    String title, date;

    public TitleObj() {
    }

    public TitleObj(String title, String date) {
        this.title = title;
        this.date = date;
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
}
