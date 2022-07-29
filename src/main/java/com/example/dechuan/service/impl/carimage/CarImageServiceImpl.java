package com.example.dechuan.service.impl.carimage;

import com.example.dechuan.mapper.carimage.CarImageMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.service.carimage.CarImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/7/29 14:22
 */
@Service("CarImageService")
@Transactional
public class CarImageServiceImpl implements CarImageService {
    @Autowired
    CarImageMapper carImageMapper;

    @Override
    public int doAddImageUrl(CarImage ci) {
        return carImageMapper.doAddImageUrl(ci);
    }
}
