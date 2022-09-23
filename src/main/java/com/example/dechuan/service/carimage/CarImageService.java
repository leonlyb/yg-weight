package com.example.dechuan.service.carimage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.carimage.CarImage;

import java.util.List;

/**
 * @author eden
 * @date 2022/7/29
 * @menu
 */
public interface CarImageService {

    int doAddImageUrl(CarImage ci);

    PageResult doGetCarImageList(CarImage ci, QueryDt qt);
}
