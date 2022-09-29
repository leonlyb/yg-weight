package com.example.dechuan.service.impl.carimage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.carimage.CarImageMapper;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.carimage.CarImageService;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public PageResult doGetCarImageList(CarImage ci, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<CarImage> list = carImageMapper.doGetCarImageList(ci);
        return PageUtils.getPageResult(qt, new PageInfo<CarImage>(list));
    }

    @Override
    public int doAddImageUrl(CarImage ci) {
        return carImageMapper.doAddImageUrl(ci);
    }

    @Override
    public int updateByPrimaryKey(CarImage ci) {
        return carImageMapper.updateByPrimaryKey(ci);
    }
}
