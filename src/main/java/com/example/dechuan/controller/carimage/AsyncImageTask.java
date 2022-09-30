package com.example.dechuan.controller.carimage;

import com.example.dechuan.mapper.first.vehicle.VehicleMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.vehicle.Vehicle;
import com.example.dechuan.service.carimage.CarImageService;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.ImageRemarkUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/9/28 16:05
 */
@Component
public class AsyncImageTask {
    private Logger log = LoggerFactory.getLogger(AsyncImageTask.class);

    @Autowired
    CarImageService carImageService;

    @Autowired
    WorkOrderManageService workOrderManageService;

    @Autowired
    private VehicleMapper vehicleMapper;


    @SneakyThrows
    @Async
    public void doTask(String vormalVehicleImage, String carno,int woKy) {
       log.info("<------------ 要开启新开启新线程了 -------->");
        long t1 = System.currentTimeMillis();
        CarImage ci = new CarImage();
        ci.setWoKy(woKy);
        String imurl = vormalVehicleImage.substring(12, 15);
        if (imurl.equals("244")) {
            String srcImgPath = "D:/ITCP Web/hkimg/carImg/192.168.1.244/" + vormalVehicleImage.substring(16);
            String targerTextPath = "D:/ITCP Web/hkimg/watermark/192.168.1.244/" + vormalVehicleImage.substring(16);
            File file = new File(targerTextPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            ImageRemarkUtil.markImageByText(carno, srcImgPath, targerTextPath);
            ci.setWatermarkImage("/view/watermark/image/244/"+vormalVehicleImage.substring(16));
            carImageService.updateByPrimaryKey(ci);
        } else {
            String srcImgPath = "D:/ITCP Web/hkimg/carImg/192.168.1.247/" + vormalVehicleImage.substring(16);
            String targerTextPath = "D:/ITCP Web/hkimg/watermark/192.168.1.247/" + vormalVehicleImage.substring(16);
            File file = new File(targerTextPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            ImageRemarkUtil.markImageByText(carno, srcImgPath, targerTextPath);
            ci.setWatermarkImage("/view/watermark/image/247/"+vormalVehicleImage.substring(16));
            carImageService.updateByPrimaryKey(ci);
        }
        long t2 = System.currentTimeMillis();
        log.info("task1 cost {} ms" , t2-t1);
        log.info("<------------ 线程结束啦 -------->");
    }

    @SneakyThrows
    @Async
    public void dogetCarNoTask(String carno, String clImgName, String imgName, int isPass) {
        workOrderManageService.doAutomaticWorkorder(carno,clImgName,imgName,isPass);
    }
    @SneakyThrows
    @Async
    public void dogetCarInfo( String carno,int woKy,String date) {
        Vehicle vehicle = new Vehicle();
        //存log记录
        vehicle.setCarNo(carno);
        vehicle.setViStatus(2);
        vehicle.setWoKy(woKy);
        vehicle.setVehicleinTime(date);
        vehicleMapper.insertSelective(vehicle);
    }
}
