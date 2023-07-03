package com.example.dechuan.model.workorder;




import com.example.dechuan.utils.DateUtils;

import java.sql.Timestamp;


public class WorkorderPrintInfo {

    private Integer id;

    private Integer woKy;

    private String imgPath;

    private String filePath;

    private  Timestamp printTime;

    private int printCount;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPrintTime() {

        return DateUtils.formatDate(printTime, "yyyy-MM-dd HH:mm:ss");
    }

    public void setPrintTime(String printTime) {

        this.printTime = Timestamp.valueOf(printTime);;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}