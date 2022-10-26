package com.example.dechuan.mapper.first.workorder;

import com.example.dechuan.model.workorder.HtCarNo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HtCarNoMapper {

    List<HtCarNo> doGetHistoryCarNoList(HtCarNo hcn);

}