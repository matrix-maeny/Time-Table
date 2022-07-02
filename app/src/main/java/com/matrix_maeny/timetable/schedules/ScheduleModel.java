package com.matrix_maeny.timetable.schedules;

public class ScheduleModel {

    private String taskName;
    private String taskTime;
    private int serialNo;

    public ScheduleModel(int serialNo,String taskName, String taskTime) {
        this.taskName = taskName;
        this.taskTime = taskTime;
        this.serialNo = serialNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getSerialNo() {
        return serialNo+".";
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }
}
