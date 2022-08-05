package com.example.dechuan.controller.job;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.service.workorder.WorkOrderTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @description: 工单时间超时job
 * @menu
 * @date 2022/8/5 13:37
 */
@RestController
@RequestMapping(value = "/work/tempo")
public class WorkOrderTimeJob {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderTimeJob.class);

    @Autowired
    private WorkOrderTimeService workOrderTimeService;

    /**
     * @Author eden
     * @Description 工单时效查询
     * @Date  2022/08/05 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(WorkTempo wt, QueryDt qt) {
        if(qt.getPageNum() == null || qt.getPageSize() == null){
            qt.setPageNum(1);
            qt.setPageSize(9999);
        }
        return ResultBody.success(workOrderTimeService.doGetWorkOrderTimeList(wt,qt));
    }


}
