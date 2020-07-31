package com.wazir.warehousing.ModelObject;

import java.util.ArrayList;

public class BodyObj {
    boolean checked;
    String title, description;
    String timeOfTask;
    ArrayList<Assignees> assignees;

    public BodyObj() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTimeOfTask() {
        return timeOfTask;
    }

    public void setTimeOfTask(String timeOfTask) {
        this.timeOfTask = timeOfTask;
    }

    public ArrayList<Assignees> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<Assignees> assignees) {
        this.assignees = assignees;
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
