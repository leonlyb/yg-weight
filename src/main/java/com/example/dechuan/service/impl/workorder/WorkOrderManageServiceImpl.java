package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.controller.carimage.AsyncImageTask;
import com.example.dechuan.controller.carimage.AsyncImageUrlTask;
import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.hktv.basics.HikVisionService;
import com.example.dechuan.mapper.first.carimage.CarImageMapper;
import com.example.dechuan.mapper.first.vehicle.VehicleMapper;
import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.carimage.truckinoutImage;
import com.example.dechuan.model.vehicle.Vehicle;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.DateUtils;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/7/8 10:07
 */
@Transactional
@Service("WorkOrderManageService")
public class WorkOrderManageServiceImpl implements WorkOrderManageService {

    private static Logger logger = LoggerFactory.getLogger(WorkOrderManageServiceImpl.class);
    @Autowired
    private WorkOrderManageMapper workOrderManageMapper;
    @Autowired
    private AsyncImageUrlTask asyncImageTask;


    @Override
    public PageResult doGetWorkOrderManageList(WorkOrderManage wom, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<WorkOrderManage> list = workOrderManageMapper.doGetWorkOrderManageList(wom);
        return PageUtils.getPageResult(qt, new PageInfo<WorkOrderManage>(list));
    }

    @Override
    public int doAddWorkOrderManage(WorkOrderManage wom) {
        return workOrderManageMapper.doAddWorkOrderManage(wom);
    }

    @Override
    public int doEditWorkOrderManage(WorkOrderManage wom) {
        return workOrderManageMapper.doEditWorkOrderManage(wom);
    }

    @Override
    public int doDeleteWorkOrderManage(Integer woKy) {
        return workOrderManageMapper.doDeleteWorkOrderManage(woKy);
    }

    @Override
    public int doAutomaticWorkorder(String carno, String clImgName, String imgName,int isPass) {
        WorkOrderManage wo = new WorkOrderManage();
        WorkOrderManage workOrderManage = new WorkOrderManage();
        String date =DateUtils.getCurrentDate();
        if(isPass == 1){
            wo.setEntranceWeight(0.0);
        }
        wo.setCarNo(carno);
        wo.setEntranceLoadCellNumber(100000);
        wo.setEntranceDateTime(date);
        wo.setIsPass(isPass);
        wo.setWorkStatus(0);
        workOrderManage.setCarNo(carno);
        workOrderManage.setEntranceDateTime(DateUtils.getAfterDate(date));
        List<WorkOrderManage> workOrderManages = workOrderManageMapper.doGetWorkOrderManageCheckList(workOrderManage);
        if(workOrderManages.size() <= 0){
            logger.debug("2分钟之内数据库中无数据=== 新增");
            int i = workOrderManageMapper.doAddWorkOrderManage(wo);
            if(i == 1){
                //存log记录
                asyncImageTask.insertSelective(carno,wo.getWoKy(),date);
                //存储图片路径
                asyncImageTask.doAddImageUrl(wo.getWoKy(),imgName,clImgName);
            }

        }
        return 1;
    }

    @Override
    public List<WorkOrderManage> doGetWorkOrderManageTimeList(WorkOrderManage wom) {
        return workOrderManageMapper.doGetWorkOrderManageList(wom);
    }

    @Override
    public List<WorkOrderManage> doGetWorkOrderManageTimeStatusList(Integer woKy) {
        return workOrderManageMapper.doGetWorkOrderManageTimeStatusList(woKy);
    }

    @Override
    public List<truckinoutImage> doGetWorkOrderStatusList(String carno) {
        return workOrderManageMapper.doGetWorkOrderStatusList(carno);
    }
}