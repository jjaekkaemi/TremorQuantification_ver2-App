package com.ahnbcilab.tremorquantification.data;

public class TaskItem {

    String taskNum ;
    String taskDate;
    String taskTime;

    public TaskItem(String taskNum, String taskDate, String taskTime) {
        this.taskNum = taskNum;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }
}