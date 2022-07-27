package com.example.dechuan.service.ocrlpr.impl;

import com.example.dechuan.controller.job.YoloV5CarDetection;
import com.example.dechuan.model.ocrlrp.Object;
import com.example.dechuan.service.ocrlpr.CarService;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

public class YoloV5CarDetectionServiceImpl implements CarService {

	@Override
	public List<Object> carsDetection(Mat src) {
		//定位车辆
		return YoloV5CarDetection.detection(src, false, 0.5);
	}

}
