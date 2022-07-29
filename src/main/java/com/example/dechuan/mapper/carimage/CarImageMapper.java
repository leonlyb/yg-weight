package com.example.dechuan.mapper.carimage;

import com.example.dechuan.model.carimage.CarImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CarImageMapper {

    int deleteByPrimaryKey(Integer imky);

    int insert(CarImage record);

    int doAddImageUrl(CarImage record);

    CarImage selectByPrimaryKey(Integer imky);

    int updateByPrimaryKeySelective(CarImage record);

    int updateByPrimaryKey(CarImage record);

}