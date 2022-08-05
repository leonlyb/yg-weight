package com.example.dechuan.service.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkTempo;

/**
 * @author eden
 * @date 2022/8/5
 * @menu
 */
public interface WorkOrderTimeService {
    PageResult doGetWorkOrderTimeList(WorkTempo wt, QueryDt qt);

    int doGetEditWorkOrderTime(WorkTempo wt);
}
