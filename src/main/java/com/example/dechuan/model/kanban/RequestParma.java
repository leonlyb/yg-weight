package com.example.dechuan.model.kanban;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2023/2/16 13:31
 */
public class RequestParma {

    private String plateNo;
    private String weight;
    private String date;
    private String uniqueCode;

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}