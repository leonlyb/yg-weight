package com.example.dechuan.service.ocrlpr.impl;

import cn.daming.java.lpr.cars.YoloV5CarDetection;
import cn.daming.java.lpr.djl.carid.CarService;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

public class YoloV5CarDetectionServiceImpl implements CarService {

	@Override
	public List<Object> carsDetection(Mat src) {
		//定位车辆
		return YoloV5CarDetection.detection(src, false, 0.5);
	}

}
