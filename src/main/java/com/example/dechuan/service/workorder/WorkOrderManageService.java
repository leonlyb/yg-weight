package com.example.dechuan.service.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.carimage.truckinoutImage;
import com.example.dechuan.model.workorder.WorkOrderManage;

import java.util.List;

/**
 * @author eden
 * @date 2022/7/8
 * @menu
 */
public interface WorkOrderManageService {
    PageResult doGetWorkOrderManageList(WorkOrderManage wom, QueryDt qt);

    int doAddWorkOrderManage(WorkOrderManage wom);

    int doEditWorkOrderManage(WorkOrderManage wom);

    int doDeleteWorkOrderManage(Integer woKy);

    int doAutomaticWorkorder(String carno,String clImgName,String imgName,int isPass);

    List<WorkOrderManage> doGetWorkOrderManageTimeList(WorkOrderManage wom);

    List<WorkOrderManage> doGetWorkOrderManageTimeStatusList(Integer woKy);

    List<truckinoutImage> doGetWorkOrderStatusList(String carno);
}
