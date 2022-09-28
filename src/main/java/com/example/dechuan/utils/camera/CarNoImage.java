package com.example.dechuan.utils.camera;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.DateUtils;
import com.example.dechuan.utils.ImageRemarkUtil;
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
//@Component
@RestController
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

    public static void getcarno(String carno, String clImgName, String imgName,int isPass) {

        carNoImage.workOrderManageService.doAutomaticWorkorder(carno,clImgName,imgName,isPass);

    }

    @RequestMapping("/test")
    @ResponseBody
    public void closeworkorder(String carno,String date){
//         carno ="皖AG2701";
         date= DateUtils.getCurrentDate();
        WorkOrderManage wom = new WorkOrderManage();
        List<WorkOrderManage> list = carNoImage.workOrderManageService.doGetWorkOrderStatusList(carno);
        if(list.size() > 0){
            //存在完成工单
            wom.setWoKy(list.get(0).getWoKy());
//            wom.setCompletionStatus(1);
            wom.setExitDateTime(date);
            carNoImage.workOrderManageService.doEditWorkOrderManage(wom);
            //更新完成，打水印
            String vormalVehicleImage = list.get(0).getVormalVehicleImage();
            if(vormalVehicleImage != null){
               String imurl = vormalVehicleImage.substring(12,15);
               if(imurl.equals("244")){
                   String srcImgPath = "D:/ITCP Web/hkimg/carImg/192.168.1.244/"+ vormalVehicleImage.substring(16);
                   String targerTextPath = "D:/ITCP Web/hkimg/watermark/192.168.1.244/"+ vormalVehicleImage.substring(16);
                   File file = new File(targerTextPath);
                   if (!file.getParentFile().exists()) {
                       file.getParentFile().mkdirs();
                   }
                   ImageRemarkUtil.markImageByText(carno, srcImgPath, targerTextPath);

               }else{
                   String srcImgPath = "D:/ITCP Web/hkimg/carImg/192.168.1.247/"+ vormalVehicleImage.substring(16);
                   String targerTextPath = "D:/ITCP Web/hkimg/watermark/192.168.1.247/"+ vormalVehicleImage.substring(16);
                   File file = new File(targerTextPath);
                   if (!file.getParentFile().exists()) {
                       file.getParentFile().mkdirs();
                   }
                   ImageRemarkUtil.markImageByText(carno, srcImgPath, targerTextPath);
               }
            }

        }

    }
}
