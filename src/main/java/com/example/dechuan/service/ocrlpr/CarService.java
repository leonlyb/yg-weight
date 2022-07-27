package com.example.dechuan.service.ocrlpr;

import com.example.dechuan.model.ocrlrp.Object;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

/**
 * 车牌定位接口
 * @author xiaoming
 *
 */
public interface CarService {
	
	/**
	 * 在Mat上检测出车辆截图
	 * @param src
	 * @return
	 */
	public List<Object> carsDetection(Mat src);
	
	
}
