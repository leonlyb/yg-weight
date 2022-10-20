package com.example.dechuan.controller.carimage;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.carimage.TruckinoutImage;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.carimage.CarImageService;
import com.example.dechuan.service.impl.workorder.WorkOrderManageServiceImpl;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author eden
 * @description:
 * @menu 图片查询
 * @date 2022/7/8 10:00
 */
@RestController
@RequestMapping(value = "/car/image")
public class CarImageController {
    private static Logger log = LoggerFactory.getLogger(CarImageController.class);

    @Autowired
    private CarImageService carImageService;

    @Autowired
    WorkOrderManageService workOrderManageService;

    @Autowired
    private AsyncImageTask asyncImageTask;
    /**
     * @Author eden
     * @Description 图片url查询
     * @Date  2022/09/23 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(CarImage ci, QueryDt qt) {
        return ResultBody.success(carImageService.doGetCarImageList(ci,qt));
    }


    @RequestMapping("/imagereark")
    @ResponseBody
    public void imageReark(Integer woKy){
        TruckinoutImage tr = new TruckinoutImage();
        tr.setWoKy(woKy);
        List<TruckinoutImage> list = workOrderManageService.doGetWorkOrderStatusList(tr);
        if(list.size() > 0){
            Double entranceWeight = list.get(0).getEntranceWeight();
            Double exitWeight = list.get(0).getExitWeight();
            Double netWeight = list.get(0).getNetWeight();
            if(entranceWeight == null){
                entranceWeight = 0.0;
            }
            if(exitWeight == null){
                exitWeight=0.0;
            }
            if(netWeight== null){
                netWeight=0.0;
            }
            asyncImageTask.doTask(list.get(0).getVormalVehicleImage(),list.get(0).getCarNo(),list.get(0).getWoKy(),entranceWeight,exitWeight,netWeight);
        }
       log.debug("无查询结果");
    }

}
