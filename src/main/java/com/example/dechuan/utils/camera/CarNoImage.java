package com.example.dechuan.utils.camera;

import com.example.dechuan.controller.camera.CameraController;
import com.example.dechuan.controller.carimage.AsyncImageTask;
import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.camera.CustomMultiThreadingService;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.DateUtils;
import com.example.dechuan.utils.ImageRemarkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/9/23 11:07
 */
@Component
//@RestController
public class CarNoImage {
    private Logger log = LoggerFactory.getLogger(CarNoImage.class);

    @Autowired
    WorkOrderManageService workOrderManageService;

    @Autowired
    private AsyncImageTask asyncImageTask;

    public static CarNoImage carNoImage;

    //初始化
    @PostConstruct
    public void init() {
        carNoImage= this;
        carNoImage.workOrderManageService= this.workOrderManageService;
        carNoImage.asyncImageTask= this.asyncImageTask;
    }

    public static void getcarno(String carno, String clImgName, String imgName,int isPass) {
        //开启异步操作
        carNoImage.asyncImageTask.dogetCarNoTask(carno,clImgName,imgName,isPass);

    }

//    @RequestMapping("/test")
//    @ResponseBody
    public static  void closeworkorder(String carno){
        WorkOrderManage wom = new WorkOrderManage();
        List<WorkOrderManage> list = carNoImage.workOrderManageService.doGetWorkOrderStatusList(carno);
        if(list.size() > 0){
            //存在完成工单
            wom.setWoKy(list.get(0).getWoKy());
            wom.setCompletionStatus(1);
            wom.setExitDateTime(DateUtils.getCurrentDate());
            carNoImage.workOrderManageService.doEditWorkOrderManage(wom);
            //更新完成，打水印
            carNoImage.asyncImageTask.doTask(list.get(0).getVormalVehicleImage(),carno,list.get(0).getWoKy());

        }

    }

}
