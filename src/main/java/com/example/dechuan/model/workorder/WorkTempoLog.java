package com.example.dechuan.model.workorder;


import lombok.Data;

import java.sql.Timestamp;


public class WorkTempoLog {

    private Integer wtKy;


    private Integer woKy;


    private Integer timeStatus;


    private String timeStatusDesc;

    private String carNo;
   private Timestamp  entranceDateTime;
    private Timestamp exitDateTime;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Timestamp getEntranceDateTime() {
        return entranceDateTime;
    }

    public void setEntranceDateTime(Timestamp entranceDateTime) {
        this.entranceDateTime = entranceDateTime;
    }

    public Timestamp getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(Timestamp exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

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