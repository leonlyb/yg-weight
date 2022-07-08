package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Autowired
    private WorkOrderManageMapper workOrderManageMapper;

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
}