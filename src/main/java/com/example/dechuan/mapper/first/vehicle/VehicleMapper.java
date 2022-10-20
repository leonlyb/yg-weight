package com.example.dechuan.mapper.first.vehicle;

import com.example.dechuan.model.carimage.TruckinoutImage;
import com.example.dechuan.model.vehicle.Vehicle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface VehicleMapper {

    int deleteByPrimaryKey(Integer vinky);


    int insert(Vehicle record);


    int insertSelective(Vehicle record);

    Vehicle selectByPrimaryKey(Integer vinky);

    int updateByPrimaryKeySelective(Vehicle record);

    int updateByPrimaryKey(Vehicle record);

}