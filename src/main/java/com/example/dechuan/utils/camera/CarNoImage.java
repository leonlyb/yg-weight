package com.example.dechuan.utils.camera;

import com.example.dechuan.service.workorder.WorkOrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/9/23 11:07
 */
@Component
public class CarNoImage {

    @Autowired
    WorkOrderManageService workOrderManageService;

    public static CarNoImage carNoImage;

    //初始化
    @PostConstruct
    public void init() {
        carNoImage= this;
        carNoImage.workOrderManageService= this.workOrderManageService;
    }

    public static void getcarno(String carno, String clImgName, String imgName) {

        carNoImage.workOrderManageService.doAutomaticWorkorder(carno,clImgName,imgName);

    }
}
