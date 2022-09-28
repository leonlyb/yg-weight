package com.example.dechuan.model.workorder;

import com.example.dechuan.utils.DateUtils;
import freemarker.template.utility.DateUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table truckinout_workorder
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class WorkOrderManage {
    /**
     * 主键
     */
    private Integer woKy;
    /**
     *   进地磅称表号
     */
    private Integer entranceLoadCellNumber;

    /**
     *   出地磅表号
     */
    private Integer exitLoadCellNumber;

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
     *   供应商id
     */
    private String supplierId;

    /**
     *   司机id
     */
    private String driverId;

    /**
     *   其他信息
     */
    private String driverInfo;

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

    /**
     *   货单号
     */
    private Integer tracingNo;

    /**
     *   磅单打印状态
     */
    private Integer productListStatus;

    /**
     *   司磅员姓名
     */
    private String weighmanName;
    /**
     *   工单完成状态
     */
    private Integer completionStatus;
    /**
     *   是否直通 1-直通 0-非直通
     */
    private Integer isPass;

    /**
     *   正常图片
     */
    private String vormalVehicleImage;

    public String getVormalVehicleImage() {
        return vormalVehicleImage;
    }

    public void setVormalVehicleImage(String vormalVehicleImage) {
        this.vormalVehicleImage = vormalVehicleImage;
    }

    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    public Integer getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Integer completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Integer getWoKy() {
        return woKy;
    }

    public void setWoKy(Integer woKy) {
        this.woKy = woKy;
    }

    public Integer getEntranceLoadCellNumber() {
        return entranceLoadCellNumber;
    }

    public void setEntranceLoadCellNumber(Integer entranceLoadCellNumber) {
        this.entranceLoadCellNumber = entranceLoadCellNumber;
    }

    public Integer getExitLoadCellNumber() {
        return exitLoadCellNumber;
    }

    public void setExitLoadCellNumber(Integer exitLoadCellNumber) {
        this.exitLoadCellNumber = exitLoadCellNumber;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

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

    public Integer getTracingNo() {
        return tracingNo;
    }

    public void setTracingNo(Integer tracingNo) {
        this.tracingNo = tracingNo;
    }

    public Integer getProductListStatus() {
        return productListStatus;
    }

    public void setProductListStatus(Integer productListStatus) {
        this.productListStatus = productListStatus;
    }

    public String getWeighmanName() {
        return weighmanName;
    }

    public void setWeighmanName(String weighmanName) {
        this.weighmanName = weighmanName;
    }
}