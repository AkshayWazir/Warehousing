package com.wazir.warehousing.ModelObject;

import java.util.ArrayList;

public class TaskObject {
    String title, desc, timeDate;
    int status;
    ArrayList<WorkerObjTask> assignees;

    public TaskObject() {
    }

    public TaskObject(String title, String desc, String timeDate, int status, ArrayList<WorkerObjTask> assignees) {
        this.title = title;
        this.desc = desc;
        this.timeDate = timeDate;
        this.status = status;
        this.assignees = assignees;
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

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<WorkerObjTask> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<WorkerObjTask> assignees) {
        this.assignees = assignees;
    }
}
