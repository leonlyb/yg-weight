package com.example.dechuan.controller.job;

import com.example.dechuan.model.ocrlrp.*;
import com.example.dechuan.model.ocrlrp.Object;
import com.example.dechuan.service.ocrlpr.CarIdService;
import com.example.dechuan.service.ocrlpr.CarService;
import com.example.dechuan.service.ocrlpr.impl.OpenLprCarIdServiceImpl;
import com.example.dechuan.service.ocrlpr.impl.YoloV5CarDetectionServiceImpl;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point2f;
import org.bytedeco.opencv.opencv_core.Size;

import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgproc.getRectSubPix;

/**
 * 车牌识别
 * @author xiaoming
 */
public class LPR extends Base {
	
	
	/**
	 * 车辆检测
	 */
	private CarService carService = new YoloV5CarDetectionServiceImpl();
	
	/**
	 * 车牌检测
	 */
	private CarIdService carIdService = new OpenLprCarIdServiceImpl();
	
	
	public LPR() {
		Base.debugPath = null;
		Base.isDebug = false;
	}
	
	public LPR(boolean isDebug, String debugPath) {
		Base.debugPath = debugPath;
		Base.isDebug = isDebug;
	}
	
	/**
	 * 车牌识别，一张图片可以同时识别出多张车牌号
	 * @param src
	 * @return
	 */
	public List<PlateId> find(Mat src) {
		List<PlateId> ids = new ArrayList();
		
		if(src.empty()) {
			if(isDebug) {
				System.out.println("输入NULL");
			}
			return ids;
		}
		
		//车辆检测
		List<Object> cars = carService.carsDetection(src);
		if(cars != null && cars.size() > 0) {
			for(Object car : cars) {
				//检测出车牌区域集合
				List<Mat> idMats = carIdService.getCarIdByMat(car.getMat());
				
				//根据矫正后的车牌截图识别出出牌号
				List<PlateId> tem_ids = dnnFindId(idMats);
				if(tem_ids != null && tem_ids.size() > 0) {
					ids.addAll(tem_ids);
				}
				
//				for(Mat mat:idMats) {
//					mat.close();
//				}
				
				//销毁车辆截图
				car.close();
			}
		}
		
		
		return ids;
	}

	/**
	 * 字符分割，然后字符分类
	 * @param idMats
	 * @return
	 */
	private List<PlateId> dnnFindId(List<Mat> idMats) {
		List<PlateId> tem_ids = new ArrayList<PlateId>();
		for(Mat idMat:idMats) {
			//字符分割
			long start = System.currentTimeMillis();
			List<ObjRect> rects = carIdService.getCarIdChars(idMat);
			if(rects.size() >= 7) {
				//rect根据minX从小到大排序
				rects.sort(null);
				
				if(rects.size() > 7) {
					//最左边的rect宽度不小于15，高度不能小于20, 最左边字符布恩那个低于0.6
					ObjRect leftR = rects.get(0);
					if(((leftR.getMaxX() - leftR.getMinX()) <= 15) || ((leftR.getMaxY() - leftR.getMinY()) <= 20) || leftR.getVal() < 0.6) {
						rects.remove(0);
					}
				}
				
				//最右边的字符相似度不能低于0.85
				if(rects.size() > 7){
					if(rects.get(rects.size() - 1).getVal() < 0.85){
						rects.remove(rects.size() - 1);
					}
				}
				
				
				PlateId plateId = new PlateId();
				plateId.setRect(idMat);
				
				//字符截取
				for(ObjRect idR:rects) {
					//num截图
	                Mat numM = new Mat();
	                Size  si = new Size(idR.getMaxX() - idR.getMinX(), idR.getMaxY() - idR.getMinY());
	                Point2f p = new Point2f(((idR.getMinX() + idR.getMaxX())/2) + 1, ((idR.getMinY() + idR.getMaxY())/2) + 1);
					getRectSubPix(idMat, si, p, numM);
					
					
					//字符分类
				    Num num = TensorflowDnnNumClassification.classification(numM);
				    plateId.addNumber(num.getId(), num.getProbabilitie());
				}
				
				//最左边字符必须是中文，且中文紧跟着一定是字母
				cleanIDs(plateId);
				
				
				//保存车牌识别结果
				tem_ids.add(plateId);
			}
		}
		
		
		return tem_ids;
	}
	
	/**
	 * 最左边字符必须是中文，且中文紧跟着一定是字母
	 * @param plateId
	 */
	private void cleanIDs(PlateId plateId) {
		//最左边必须是中文
		String new_id = "";
		for(int i = 0; i < plateId.getId().length(); i++) {
			String num = plateId.getId().charAt(i) + "";
			
			if(getLabelByIdNum(num) > 33) {
				//第一个中文字符
				new_id = num;
				
				//第二个必须是字母
				for(int j = i + 1; j < plateId.getId().length(); j++) {
					String num2 = plateId.getId().charAt(j) + "";
					int lab = getLabelByIdNum(num2);
					if(lab < 34 && lab > 9) {
						new_id = new_id + num2;
						
						//把j后面的字符追加到new_id上
						new_id = new_id + plateId.getId().substring(j + 1);
						break;
					}
				}
				
				break;
			}
		}
		
		//将新的new_id覆盖给plateId
		if(new_id.length() >= 7) {
			plateId.setId(new_id);
		}else {
			plateId.setId("");
			plateId.setProbabilitie(0);
		}
		
	}
}
