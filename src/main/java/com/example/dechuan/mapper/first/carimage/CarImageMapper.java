package com.example.dechuan.mapper.first.carimage;

import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.carimage.TruckinoutImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CarImageMapper {

    int deleteByPrimaryKey(Integer imky);

    int insert(CarImage record);

    int doAddImageUrl(CarImage record);

    CarImage selectByPrimaryKey(Integer imky);

    int updateByPrimaryKeySelective(CarImage record);

    int updateByPrimaryKey(CarImage record);

    List<CarImage> doGetCarImageList(CarImage ci);

    List<TruckinoutImage> doGetWorkOrderStatusList(TruckinoutImage tr);

}