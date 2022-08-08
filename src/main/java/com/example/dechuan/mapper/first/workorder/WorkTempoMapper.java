package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.model.workorder.WorkTempoLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkTempoMapper {


    List<WorkTempo> listWorkTempo(WorkTempo wt);


    int doEditWorkOrderTime(WorkTempo wt);

}