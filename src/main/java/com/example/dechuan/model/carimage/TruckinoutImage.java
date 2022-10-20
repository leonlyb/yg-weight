package com.example.dechuan.model.carimage;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/10/18 15:18
 */
public class TruckinoutImage {

    /**
     *  工单主键
     */

    private Integer woKy;
    /**
     * 车牌
     */
    private String carNo;

    /**
     *   正常图片
     */
    private String vormalVehicleImage;
    /**
     *   毛重
     */
    private Double entranceWeight;

    /**
     *   皮重
     */
    private Double exitWeight;

    /**
     *   净重
     */
    private Double netWeight;

    public Double getEntranceWeight() {
        return entranceWeight;
    }

    public void setEntranceWeight(Double entranceWeight) {
        this.entranceWeight = entranceWeight;
    }

    public Double getExitWeight() {
        return exitWeight;
    }

    public void setExitWeight(Double exitWeight) {
        this.exitWeight = exitWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getVormalVehicleImage() {
        return vormalVehicleImage;
    }

    public void setVormalVehicleImage(String vormalVehicleImage) {
        this.vormalVehicleImage = vormalVehicleImage;
    }
}
