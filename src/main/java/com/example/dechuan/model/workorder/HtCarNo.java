package com.example.dechuan.model.workorder;

import com.example.dechuan.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/10/26 9:58
 */

public class HtCarNo {

    /**
     * 主键
     */
    private Integer woKy;

    /**
     *   进门时间
     */
    private Timestamp entranceDateTime;

    /**
     *   出门时间
     */
    private Timestamp exitDateTime;

    /**
     *   车牌号
     */
    private String carNo;

    /**
     * 开始时间
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String beginEntranceTime;
    /**
     * 结束时间
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String endEntranceTime;

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public String getEntranceDateTime() {
        return DateUtils.formatDate(entranceDateTime, "yyyy-MM-dd HH:mm:ss");
    }


    public void setEntranceDateTime(String entranceDateTime) {
        this.entranceDateTime = Timestamp.valueOf(entranceDateTime);
    }

    public String getExitDateTime() {
        return DateUtils.formatDate(exitDateTime, "yyyy-MM-dd HH:mm:ss");
    }


    public void setExitDateTime(String exitDateTime) {
        this.exitDateTime = Timestamp.valueOf(exitDateTime);
    }
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

//    public Timestamp getBeginEntranceTime() {
//        return beginEntranceTime;
//    }
//
//    public void setBeginEntranceTime(Timestamp beginEntranceTime) {
//        this.beginEntranceTime = beginEntranceTime;
//    }

//    public void setBeginEntranceTime(String beginEntranceTime) {
//        this.beginEntranceTime = Timestamp.valueOf(beginEntranceTime);
//    }
//
//    public String getBeginEntranceTime() {
//        return DateUtils.formatDate(beginEntranceTime, "yyyy-MM-dd");
//    }
//
//    public void setEndEntranceTime(String endEntranceTime) {
//        this.endEntranceTime = Timestamp.valueOf(endEntranceTime);
//    }
//
//    public String getEndEntranceTime() {
//        return DateUtils.formatDate(endEntranceTime, "yyyy-MM-dd");
//    }


    public String getBeginEntranceTime() {
        return beginEntranceTime;
    }

    public void setBeginEntranceTime(String beginEntranceTime) {
        this.beginEntranceTime = beginEntranceTime;
    }

    public String getEndEntranceTime() {
        return endEntranceTime;
    }

    public void setEndEntranceTime(String endEntranceTime) {
        this.endEntranceTime = endEntranceTime;
    }
}
