package com.example.dechuan.model.carimage;


public class CarImage {
    /**
     *   主键
     */
    private Integer imky;

    /**
     *   进出单主键
     */
    private Integer woky;

    /**
     *   车牌号图片
     */
    private String carnoimage;

    /**
     *   正常图片
     */
    private String vormalvehicleimage;

    /**
     *   水印图片
     */
    private String watermarkimage;


    public Integer getImky() {
        return imky;
    }


    public void setImky(Integer imky) {
        this.imky = imky;
    }

    public Integer getWoky() {
        return woky;
    }


    public void setWoky(Integer woky) {
        this.woky = woky;
    }

    public String getCarnoimage() {
        return carnoimage;
    }

    public void setCarnoimage(String carnoimage) {
        this.carnoimage = carnoimage;
    }

    public String getVormalvehicleimage() {
        return vormalvehicleimage;
    }

    public void setVormalvehicleimage(String vormalvehicleimage) {
        this.vormalvehicleimage = vormalvehicleimage;
    }

    public String getWatermarkimage() {
        return watermarkimage;
    }

    public void setWatermarkimage(String watermarkimage) {
        this.watermarkimage = watermarkimage;
    }
}