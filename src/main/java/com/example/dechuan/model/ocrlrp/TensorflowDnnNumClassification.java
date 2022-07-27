/*
 * Copyright (c) 2020 Shenzhen JiMing Technology Co., Ltd.
 * All Rights Reserved.
 */
package com.example.dechuan.model.ocrlrp;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.opencv.global.opencv_dnn;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_dnn.Net;

import java.util.Hashtable;
import java.util.Map;

import static org.bytedeco.opencv.global.opencv_core.CV_32F;
import static org.bytedeco.opencv.global.opencv_core.minMaxLoc;

/**
 * 字符分类，支持批量
 * 
 * @author xiaoming
 *
 */
public class TensorflowDnnNumClassification extends BaseDetection {
	private static String model = "car_id_num.pb";
	private static Map<String, Net> dnn_net_pool = new Hashtable<String, Net>();

	private synchronized static Net getNet(String name) {
		
		Net dnnNet = dnn_net_pool.get(name);
		if (dnnNet == null) {
			/**
			 * 设置模型文件
			 */
			setModelFile(model);
			
			dnnNet = opencv_dnn.readNetFromTensorflow(model);
			if (dnnNet.empty()) {
				System.out.println("Reading DnnNet error by model=" + model);
			}

			dnn_net_pool.put(name, dnnNet);
			dnnNet.setPreferableBackend(opencv_dnn.DNN_BACKEND_OPENCV);
			dnnNet.setPreferableTarget(opencv_dnn.DNN_TARGET_CPU);// 使用cpu
			
			/**
	         * 删除临时模型文件
	         */
	        removeModelFile(model);
		}

		return dnnNet;
	}

	public static Num classification(Mat idMat) {
		Net dnnNet = getNet(Thread.currentThread().getName());

		Mat blob = opencv_dnn.blobFromImage(idMat, 1.0 / 255D, new Size(60, 60), new Scalar(0, 0, 0, 0), false, false,
				CV_32F);

		// 批量分类
		dnnNet.setInput(blob);
		Mat detection = dnnNet.forward();


		// 解析结果
		DoublePointer minVal = new DoublePointer(0);
		DoublePointer maxVal = new DoublePointer(0);
		Point minLoc = new Point(70);
		Point maxLoc = new Point(70);
		minMaxLoc(detection, minVal, maxVal, minLoc, maxLoc, null);

		// 转换成字符
		String id = chars[maxLoc.get()] + "";

		Num num = new Num();
		num.setId(id);
		num.setProbabilitie(maxVal.get());

		if (isDebug) {
//			System.out.print("   num=" + id + " val=" + maxVal.get());
		}

		// 释放资源
		detection.close();
		minVal.close();
		maxVal.close();
		minLoc.close();
		maxLoc.close();
		idMat.close();

		return num;
	}

}
