package com.wazir.warehousing.ModelObject;

import java.util.Date;

public class NoticeObject {
    int priority;
    String title, description;
    Date date;

    public NoticeObject() {
    }

    public NoticeObject(int priority, String title, String description, Date date) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}
