package com.wazir.warehousing.ModelObject;

public class TitleObj {
    String title;
    String level1Id;

    public TitleObj(String title) {
        this.title = title;
        this.level1Id = "";
    }

    public String getLevel1Id() {
        return level1Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
