package com.example.dechuan.controller.carimage;

import com.example.dechuan.mapper.first.carimage.CarImageMapper;
import com.example.dechuan.mapper.first.vehicle.VehicleMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.vehicle.Vehicle;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/9/30 14:13
 */
@Component
public class AsyncImageUrlTask {
    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    CarImageMapper carImageMapper;

    @SneakyThrows
    @Async
    public void insertSelective(String carno, Integer woKy, String date) {
        Vehicle vehicle = new Vehicle();
        vehicle.setCarNo(carno);
        vehicle.setViStatus(0);
        vehicle.setWoKy(woKy);
        vehicle.setVehicleTime(date);
        vehicleMapper.insertSelective(vehicle);
    }

    @SneakyThrows
    @Async
    public void doAddImageUrl(Integer woKy, String imgName, String clImgName) {
        CarImage ci = new CarImage();
        ci.setWoKy(woKy);
        ci.setCarNoImage(imgName);
        ci.setVormalVehicleImage(clImgName);
        carImageMapper.doAddImageUrl(ci);
    }

    @SneakyThrows
    @Async
    public void dogetCarInfo( String carno,int woKy,String date) {
        Vehicle vehicle = new Vehicle();
        //存log记录
        vehicle.setCarNo(carno);
        vehicle.setViStatus(2);
        vehicle.setWoKy(woKy);
        vehicle.setVehicleTime(date);
        vehicleMapper.insertSelective(vehicle);
    }

}
