package com.example.dechuan.controller.workorder;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @description:
 * @menu 工作单管理
 * @date 2022/7/8 10:00
 */
@RestController
@RequestMapping(value = "/work/order")
public class WorkOrderManageController {

    @Autowired
    private WorkOrderManageService workOrderManageService;

    /**
     * @Author eden
     * @Description 工作单查询
     * @Date  2022/07/05 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(WorkOrderManage wom, QueryDt qt) {
        return ResultBody.success(workOrderManageService.doGetWorkOrderManageList(wom,qt));
    }

    /**
     * @Author eden
     * @Description 工作单新增
     * @Date  2022/07/05 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultBody add(WorkOrderManage wom) {
        return ResultBody.success(workOrderManageService.doAddWorkOrderManage(wom));
    }

    /**
     * @Author eden
     * @Description 工作单修改
     * @Date  2022/07/05 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResultBody edit(WorkOrderManage wom) {
        return ResultBody.success(workOrderManageService.doEditWorkOrderManage(wom));
    }

    /**
     * @Author eden
     * @Description 工作单删除
     * @Date  2022/07/05 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultBody delete(Integer woKy) {
        return ResultBody.success(workOrderManageService.doDeleteWorkOrderManage(woKy));
    }
}