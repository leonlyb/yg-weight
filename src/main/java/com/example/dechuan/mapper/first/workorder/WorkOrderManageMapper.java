package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.WorkOrderManage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkOrderManageMapper {

    List<WorkOrderManage> doGetWorkOrderManageList(WorkOrderManage wom);

    int doAddWorkOrderManage(WorkOrderManage wom);

    int doEditWorkOrderManage(WorkOrderManage wom);

    int doDeleteWorkOrderManage(Integer woKy);

    List<WorkOrderManage> doGetWorkOrderManageTimeStatusList(Integer woKy);

    List<WorkOrderManage> doGetWorkOrderManageCheckList(WorkOrderManage workOrderManage);
}