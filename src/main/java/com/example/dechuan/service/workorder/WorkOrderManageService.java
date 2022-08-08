package com.example.dechuan.service.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
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

    List<WorkOrderManage> doGetWorkOrderManageTimeList(WorkOrderManage wom);
}
