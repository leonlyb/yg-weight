package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.workorder.WorkTempoMapper;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.service.workorder.WorkOrderTimeService;
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
 * @date 2022/8/5 14:58
 */
@Transactional
@Service("WorkOrderTimeService")
public class WorkOrderTimeServiceImpl implements WorkOrderTimeService {

    @Autowired
    WorkTempoMapper workTempoMapper;

    @Override
    public PageResult doGetWorkOrderTimeList(WorkTempo wt, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<WorkTempo> list = workTempoMapper.listWorkTempo(wt);
        return PageUtils.getPageResult(qt, new PageInfo<WorkTempo>(list));
    }
}