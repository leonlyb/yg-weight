package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.workorder.WorkTempoLogMapper;
import com.example.dechuan.mapper.first.workorder.WorkTempoMapper;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.model.workorder.WorkTempoLog;
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
    WorkTempoLogMapper workTempoLogMapper;

    @Autowired
    WorkTempoMapper workTempoMapper;

    @Override
    public PageResult doGetWorkOrderTimeList(WorkTempo wt, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<WorkTempo> list = workTempoMapper.listWorkTempo(wt);
        return PageUtils.getPageResult(qt, new PageInfo<WorkTempo>(list));
    }

    @Override
    public int doGetEditWorkOrderTime(WorkTempo wt) {
        return workTempoMapper.doEditWorkOrderTime(wt);
    }

    @Override
    public List<WorkTempo> doGetWorkOrderJobTimeList() {
        WorkTempo wt = new WorkTempo();
        return workTempoMapper.listWorkTempo(wt);
    }

    @Override
    public PageResult doGetWorkOrderTimeLogList(WorkTempoLog wtl, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<WorkTempoLog> list = workTempoLogMapper.doGetWorkOrderTimeLogList(wtl);
        return PageUtils.getPageResult(qt, new PageInfo<WorkTempoLog>(list));
    }

    @Override
    public int doGetWorkOrderTimeLogAdd(WorkTempoLog wtl) {
        return workTempoLogMapper.doGetWorkOrderTimeLogAdd(wtl);
    }

    @Override
    public List<WorkTempoLog> doGetWorkOrderTimeLogJobList(Integer woKy) {
        WorkTempoLog wtl = new WorkTempoLog();
        wtl.setWoKy(woKy);
        return workTempoLogMapper.doGetWorkOrderTimeLogList(wtl);
    }

    @Override
    public int doGetWorkOrderTimeLogEdit(WorkTempoLog wtl) {
        return workTempoLogMapper.doGetWorkOrderTimeLogEdit(wtl);
    }
}
