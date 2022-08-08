package com.example.dechuan.controller.job;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.service.workorder.WorkOrderTimeService;
import com.example.dechuan.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/8/8 14:05
 */
@Component
public class WorkOrderTimeJob {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderTimeJob.class);

    @Autowired
    private WorkOrderManageService workOrderManageService;

    @Autowired
    private WorkOrderTimeService workOrderTimeService;
    /**
     * @Author eden
     * @Description 工单超时job
     * @Date  2022/08/08 14:18
     * @status doney
     * @return
     */
//     @Scheduled(cron="0/5 * * * * ?") //每个5秒执行一次
    public void list() throws ParseException {
        int tempo=0;
        // 轮询查询是否满足条件的工单
        WorkOrderManage wom = new WorkOrderManage();
        //查询用户设置的时效
        List<WorkTempo> lsitj = workOrderTimeService.doGetWorkOrderJobTimeList();
        if(lsitj.size() > 0){
             tempo = lsitj.get(0).getTempo();//默认48小时
        }else {
            log.info("时效未设置");
        }
        wom.setCompletionStatus(0);
        List<WorkOrderManage> list = workOrderManageService.doGetWorkOrderManageTimeList(wom);
        if(list.size() >0){
            for (WorkOrderManage workOrderManage : list) {
//                String entranceDateTime = workOrderManage.getEntranceDateTime();
                Date date = new Date();//当前时间
                String entranceDateTime = workOrderManage.getEntranceDateTime(); //获取进入时间
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = sf.parse(entranceDateTime);
               // 当前时间与其他时间相差的毫秒数
                long diff = date.getTime() - time.getTime();
                long hours = (diff) / (1000 * 60 * 60);
                long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
                if(hours > tempo ){
                    //查询的时间大于设置时间，为超时
                    //记录，存入数据库
                }
            }
        }

    }



    public static void main(String[] args) throws ParseException {
//        Date date = new Date();//当前时间
//        String otherTime = "2022-08-08 14:00:00";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date time = sf.parse(otherTime);
////      当前时间与其他时间相差的毫秒数
//        long diff = date.getTime() - time.getTime();
////        long min = diff / 60 / 1000;
////        long hours = diff / 60 / 60 / 1000;
//        long hours = (diff) / (1000 * 60 * 60);
//        long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
//        System.out.println("相差多少分钟" + minutes);
//        System.out.println("相差多少小时" + hours);

    }


}
