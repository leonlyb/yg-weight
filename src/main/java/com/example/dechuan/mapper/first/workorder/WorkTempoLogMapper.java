package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.WorkTempoLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkTempoLogMapper {


    int insertSelective(WorkTempoLog record);

    int updateByPrimaryKeySelective(WorkTempoLog record);

    List<WorkTempoLog> doGetWorkOrderTimeLogList(WorkTempoLog wtl);
}