package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.WorkorderprintInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkorderPrintInfoMapper {

    WorkorderprintInfo getPrintInfo(Integer woKy);


    int updatePrintInfo(WorkorderprintInfo model);


    int insertPrintInfo(WorkorderprintInfo model);

}