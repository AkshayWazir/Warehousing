package com.wazir.warehousing.ModelObject;

import java.util.ArrayList;
import java.util.Date;

public class BodyObj {
    boolean checked;
    String title, description, level1Id, level2Id;
    Date timeOfTask;
    ArrayList<Assignees> assignees;

    public BodyObj() {
    }

    public BodyObj(boolean checked, String title, String description, String level1Id, String level2Id, Date timeOfTask, ArrayList<Assignees> assignees) {
        this.checked = checked;
        this.title = title;
        this.description = description;
        this.level1Id = level1Id;
        this.level2Id = level2Id;
        this.timeOfTask = timeOfTask;
        this.assignees = assignees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeOfTask() {
        return timeOfTask;
    }

    public void setTimeOfTask(Date timeOfTask) {
        this.timeOfTask = timeOfTask;
    }

    public ArrayList<Assignees> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<Assignees> assignees) {
        this.assignees = assignees;
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
