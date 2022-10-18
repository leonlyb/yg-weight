package com.example.dechuan.model.carimage;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/10/18 15:18
 */
public class truckinoutImage {

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
