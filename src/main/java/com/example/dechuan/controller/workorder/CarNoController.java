package com.example.dechuan.controller.workorder;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.workorder.HtCarNo;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.HtCarNoService;
import com.example.dechuan.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @description:
 * @menu 历史车牌
 * @date 2022/10/26 9:54
 */
@RestController
@RequestMapping(value = "/work/carno")
public class CarNoController {

    @Autowired
    HtCarNoService htCarNoService;


    /**
     * @Author eden
     * @Description 历史车牌查询
     * @Date  2022/07/05 14:18
     * @status doney
     * @return
     */

    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(HtCarNo hcn, QueryDt qt) {
        if(hcn.getEndEntranceTime() != null){
            hcn.setBeginEntranceTime(hcn.getBeginEntranceTime());
            hcn.setEndEntranceTime(DateUtils.getAddDay(hcn.getEndEntranceTime()));
        }
        return ResultBody.success(htCarNoService.doGetHistoryCarNoList(hcn,qt));
    }


}
