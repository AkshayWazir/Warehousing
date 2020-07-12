package com.wazir.warehousing.ModelObject;

public class BodyObj {
    boolean checked;
    String title, level1Id, level2Id;

    public BodyObj(boolean checked, String title) {
        this.checked = checked;
        this.title = title;
        this.level1Id = "";
        this.level2Id = "";
    }

    public String getLevel1Id() {
        return level1Id;
    }

    public void setLevel1Id(String level1Id) {
        this.level1Id = level1Id;
    }

    public void setLevel2Id(String level2Id) {
        this.level2Id = level2Id;
    }

    public String getLevel2Id() {
        return level2Id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
