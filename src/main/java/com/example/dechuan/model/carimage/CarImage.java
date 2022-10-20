package com.example.dechuan.model.carimage;


public class CarImage {
    /**
     *   主键
     */
    private Integer imKy;

    /**
     *   进出单主键
     */
    private Integer woKy;

    /**
     *   车牌号图片
     */
    private String carNo;
    /**
     *   车牌号图片
     */
    private String carNoImage;

    /**
     *   正常图片
     */
    private String vormalVehicleImage;

    /**
     *   水印图片
     */
    private String watermarkImage;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Integer getImKy() {
        return imKy;
    }

    public void setImKy(Integer imKy) {
        this.imKy = imKy;
    }

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public String getCarNoImage() {
        return carNoImage;
    }

    public void setCarNoImage(String carNoImage) {
        this.carNoImage = carNoImage;
    }

    public String getVormalVehicleImage() {
        return vormalVehicleImage;
    }

    public void setVormalVehicleImage(String vormalVehicleImage) {
        this.vormalVehicleImage = vormalVehicleImage;
    }

    public String getWatermarkImage() {
        return watermarkImage;
    }

    public void setWatermarkImage(String watermarkImage) {
        this.watermarkImage = watermarkImage;
    }
}