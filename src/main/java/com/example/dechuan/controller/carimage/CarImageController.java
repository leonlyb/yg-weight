package com.example.dechuan.controller.carimage;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.carimage.CarImageService;
import com.example.dechuan.service.workorder.WorkOrderManageService;
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
    public void imageReark(String carno){
        List<WorkOrderManage> list = workOrderManageService.doGetWorkOrderStatusList(carno);
        if(list.size() > 0){
            asyncImageTask.doTask(list.get(0).getVormalVehicleImage(),carno,list.get(0).getWoKy());
        }
    }

}
