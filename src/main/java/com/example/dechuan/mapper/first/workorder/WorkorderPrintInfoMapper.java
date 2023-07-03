package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.WorkorderPrintInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkorderPrintInfoMapper {

    WorkorderPrintInfo getPrintInfo(Integer woKy);


    int updatePrintInfo(WorkorderPrintInfo model);


    int insertPrintInfo(WorkorderPrintInfo model);

}