package com.example.dechuan.service.ocrlpr.impl;

import com.example.dechuan.controller.job.YoloV5CarPlayPointsDetection;
import com.example.dechuan.model.ocrlrp.*;
import com.example.dechuan.model.ocrlrp.Object;
import com.example.dechuan.service.ocrlpr.CarIdService;
import com.google.gson.Gson;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point2f;
import org.bytedeco.opencv.opencv_core.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bytedeco.opencv.global.opencv_core.CV_32F;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class OpenLprCarIdServiceImpl  extends Base implements CarIdService {


	
	/**
	 * 根据矫正后的车牌截图识别出出牌号
	 */
	@Override
	public List<Mat> getCarIdByMat(Mat src) {
		
		List<Mat> ids = new ArrayList<>();
		/**
		 * yolo定位车牌四个点
		 */
		List<Object> points = YoloV5CarPlayPointsDetection.detection(src, false, 0.5);
		if(points != null && points.size() == 4) {
			//计算出四个点的位置
			double countX = 0;
			int minX = 10000;
			int minY = 10000;
			int maxX = 0;
			int maxY = 0;
			
			for(Object p:points) {
				int pX = p.getX() + p.getW() / 2;
				int pY = p.getY() + p.getH() / 2;
				countX = countX + pX;
				
				if(pX < minX) {
					minX = pX;
				}
				
				if(pX > maxX) {
					maxX = pX;
				}
				
				if(pY < minY) {
					minY = pY;
				}
				
				if(pY > maxY) {
					maxY = pY;
				}
			}
			double centX = countX / 4;//车牌中间x
			

			//左右区分
			List<Object> leftPoints = new ArrayList<>();
			List<Object> rightPoints = new ArrayList<>();
			for(Object p:points) {
				int pX = p.getX() + p.getW() / 2;
				if(pX < centX) {
					//车牌左边点
					leftPoints.add(p);
				}else {
					//车牌右边点
					rightPoints.add(p);
				}
			}
			
			if(leftPoints.size() == 2 && rightPoints.size() == 2) {
				//左右上下分出四个点的位置
				P left_down_p = new P();
				P left_up_p = new P();
				P right_up_p = new P();
				P right_down_p = new P();
				
				if(leftPoints.get(0).getY() > leftPoints.get(1).getY()) {
					//下边点
					left_down_p.setX(leftPoints.get(0).getX() + leftPoints.get(0).getW() / 2);
					left_down_p.setY(leftPoints.get(0).getY() + leftPoints.get(0).getH() / 2);
					//上变点
					left_up_p.setX(leftPoints.get(1).getX() + leftPoints.get(1).getW() / 2);
					left_up_p.setY(leftPoints.get(1).getY() + leftPoints.get(1).getH() / 2);
				}else {
					//下边点
					left_down_p.setX(leftPoints.get(1).getX() + leftPoints.get(1).getW() / 2);
					left_down_p.setY(leftPoints.get(1).getY() + leftPoints.get(1).getH() / 2);
					//上变点
					left_up_p.setX(leftPoints.get(0).getX() + leftPoints.get(0).getW() / 2);
					left_up_p.setY(leftPoints.get(0).getY() + leftPoints.get(0).getH() / 2);
				}
				
				
				if(rightPoints.get(0).getY() > rightPoints.get(1).getY()) {
					//下边点
					right_down_p.setX(rightPoints.get(0).getX() + rightPoints.get(0).getW() / 2);
					right_down_p.setY(rightPoints.get(0).getY() + rightPoints.get(0).getH() / 2);
					//上变点
					right_up_p.setX(rightPoints.get(1).getX() + rightPoints.get(1).getW() / 2);
					right_up_p.setY(rightPoints.get(1).getY() + rightPoints.get(1).getH() / 2);
				}else {
					//下边点
					right_down_p.setX(rightPoints.get(1).getX() + rightPoints.get(1).getW() / 2);
					right_down_p.setY(rightPoints.get(1).getY() + rightPoints.get(1).getH() / 2);
					//上变点
					right_up_p.setX(rightPoints.get(0).getX() + rightPoints.get(0).getW() / 2);
					right_up_p.setY(rightPoints.get(0).getY() + rightPoints.get(0).getH() / 2);
				}
				
				
				
				/**
				 * 车牌截图
				 */
				Size size = new Size(maxX - minX, maxY - minY);
				Mat idRect = new Mat();
        		getRectSubPix(src, size, new Point2f((maxX + minX) / 2, (maxY + minY) / 2), idRect, src.type());
        		int w_ = minX;
        		int h_ = minY;
				
        		/**
        		 * 开始变换矫正
        		 */
        		//原始四点
        		FloatPointer srcCorners = new FloatPointer(
        				(float)left_down_p.getX() - w_, (float)left_down_p.getY() - h_,
        				(float)left_up_p.getX() - w_, (float)left_up_p.getY() - h_,
        				(float)right_up_p.getX() - w_, (float)right_up_p.getY() - h_,
        				(float)right_down_p.getX() - w_, (float)right_down_p.getY() - h_)
        				;
        		
        		//新四点
                FloatPointer dstCorners = new FloatPointer(
                		(float)0, (float)size.height(),
                		(float)0, (float)0,
                		(float)size.width(), (float)0,
                		(float)size.width(), size.height()
                		);
        		
        		
              //create matrices with width 2 to hold the x,y values, and 4 rows, to hold the 4 different corners.
                Mat srcM = new Mat(new Size(2, 4), CV_32F, srcCorners);
                Mat dstM = new Mat(new Size(2, 4), CV_32F, dstCorners);
                Mat affmat = getPerspectiveTransform(srcM, dstM);
                Mat dst = new Mat(src.size(), src.type());
        		warpPerspective(idRect, dst, affmat, idRect.size());
        		resize(dst, dst, new Size(188, 48));
        		if(isDebug) {
    				imwrite(Base.debugPath + UUID.randomUUID().toString() + "_dst.jpg", dst);
    			}
        		ids.add(dst);
        		
        		//资源回收
        		size.close();
        		idRect.close();
        		srcM.close();
        		dstM.close();
        		affmat.close();
			}
			
		}
		
		
		return ids;
	}


	@Override
	public List<ObjRect> getCarIdChars(Mat idMat) {
		return CaridDetection.detectRects(idMat, 0.5);
	}

}
