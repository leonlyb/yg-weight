package com.example.dechuan.model.workorder;


import lombok.Data;


public class WorkTempoLog {

    private Integer wtKy;


    private Integer woKy;


    private Integer timeStatus;


    private String timeStatusDesc;


    public Integer getWtKy() {
        return wtKy;
    }

    public void setWtKy(Integer wtKy) {
        this.wtKy = wtKy;
    }

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public Integer getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(Integer timeStatus) {
        this.timeStatus = timeStatus;
    }

    public String getTimeStatusDesc() {
        return timeStatusDesc;
    }

    public void setTimeStatusDesc(String timeStatusDesc) {
        this.timeStatusDesc = timeStatusDesc;
    }
}