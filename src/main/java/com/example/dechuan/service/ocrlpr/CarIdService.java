package com.example.dechuan.service.ocrlpr;

import com.example.dechuan.model.ocrlrp.ObjRect;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.List;

/**
 * 车牌相关接口
 * 车牌定位
 * @author xiaoming
 *
 */
public interface CarIdService {
	

	/**
	 * 从Mat上识别出矫正后的车牌截图
	 * @param src
	 * @return
	 */
	public List<Mat> getCarIdByMat(Mat src);
	
	/**
	 * 根据车牌截图检测出上面的车牌字符
	 * @param idMat
	 * @return
	 */
	public List<ObjRect> getCarIdChars(Mat idMat);
}
