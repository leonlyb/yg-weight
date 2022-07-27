package com.example.dechuan.model.ocrlrp;

import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.DetectedObjects.DetectedObject;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.modality.cv.translator.YoloV5Translator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Translator;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * 车牌字符数据yolov5 对象检测
 * @author xiaoming
 */
public class CaridDetection extends BaseDetection {
	private static String model_path = "lpr/carid.torchscript.pt";
	private static String name_path = "lpr/carid.names";
	private static String car_path = "lpr/id.jpg";

	private static ZooModel<Image, DetectedObjects> model = null;

	static {
		/**
		 * 设置模型文件
		 */
		setModelFile(model_path);
		setModelFile(name_path);
		setModelFile(car_path);
		
		Translator<Image, DetectedObjects> translator = YoloV5Translator.builder().optSynsetArtifactName(name_path)
				.build();
		Criteria<Image, DetectedObjects> criteria = Criteria.builder().setTypes(Image.class, DetectedObjects.class)
				.optModelUrls(model_path).optModelName(model_path).optTranslator(translator)
				.optEngine("PyTorch").build();

		try {
			model = ModelZoo.loadModel(criteria);
			System.out.println("Car id num detection:" + model);
			
			//构建预测者
			Predictor<Image, DetectedObjects> predictor = model.newPredictor();
			predictor.predict(ImageFactory.getInstance().fromFile(new File(car_path).toPath()));
			predictor.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		/**
         * 删除临时模型文件
         */
        removeModelFile(model_path);
        removeModelFile(name_path);
        removeModelFile(car_path);
	}
	
	/**
	 * 检测车牌照片上的字符区域列表
	 * @param id
	 * @return
	 */
	public static List<ObjRect> detectRects(Mat id, double thresh){
		List<ObjRect> rects = new ArrayList<ObjRect>();
		if(id == null || id.empty()) {
			return rects;
		}
		
		Mat src = new Mat();
		Size size = new Size(640, 192);
		resize(id, src, size);
		size.close();
		
		// 转换成BufferedImage
		OpenCVFrameConverter.ToMat matConv = new OpenCVFrameConverter.ToMat();
		Java2DFrameConverter biConv = new Java2DFrameConverter();
		Frame frame = matConv.convert(src);
		BufferedImage bimg = biConv.getBufferedImage(frame);

		// 释放
		frame.close();
		biConv.close();
		matConv.close();
		
		//转换Image
		Image djl_img = ImageFactory.getInstance().fromImage(bimg);
		
		//构建预测者
		Predictor<Image, DetectedObjects> predictor = model.newPredictor();
		
		try {
			//检测出全部对象
			DetectedObjects objects = predictor.predict(djl_img);
		    
			
			for (DetectedObject obj : objects.<DetectedObject>items()) {
				if (thresh <= obj.getProbability()) {
					BoundingBox bbox = obj.getBoundingBox();
					Rectangle rectangle = bbox.getBounds();
					
					//车牌字符
					String name = obj.getClassName().trim().replace(" ", "").toUpperCase();
					
					//只识别字符
					if("CARID".equals(name)) {
						float probability = (float)obj.getProbability();
						int h = (int) (rectangle.getHeight() * id.arrayHeight() / 192D) + 1;
						int w = (int) (rectangle.getWidth() * id.arrayWidth() / 640D) + 1;
						int x = (int) (rectangle.getX() * id.arrayWidth() / 640D) + 1;//左上角的点
						int y = (int) (rectangle.getY() * id.arrayHeight() / 192D) + 1;//左上角的点
						
						ObjRect rect = new ObjRect();
						rect.setMinX(x);
						rect.setMinY(y);
						rect.setMaxX(x + w);
						rect.setMaxY(y + h);
						rect.setVal(probability);
						//保存rect
						rects.add(rect);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		src.close();
		predictor.close();
		return rects;
	}
	
	/**
	 * 车牌字符数据集对象识别 接收564*144的图片
	 * @param img
	 * @param isDraw
	 * @return
	 */
	public static List<Mat> detection(Mat img, boolean isDraw, double thresh) {
		List<Mat> ids = new ArrayList<Mat>();
		if(img == null || img.empty()) {
			return ids;
		}
		
		Mat src = new Mat();
		Size size = new Size(640, 192);
		resize(img, src, size);
		size.close();
		
		// 转换成BufferedImage
		OpenCVFrameConverter.ToMat matConv = new OpenCVFrameConverter.ToMat();
		Java2DFrameConverter biConv = new Java2DFrameConverter();
		Frame frame = matConv.convert(src);
		BufferedImage bimg = biConv.getBufferedImage(frame);

		// 释放
		frame.close();
		biConv.close();
		matConv.close();
		
		//转换Image
		Image djl_img = ImageFactory.getInstance().fromImage(bimg);
		
		//构建预测者
		Predictor<Image, DetectedObjects> predictor = model.newPredictor();
		
		try {
			//检测出全部对象
			DetectedObjects objects = predictor.predict(djl_img);
		    
			
			for (DetectedObject obj : objects.<DetectedObject>items()) {
				if (thresh < obj.getProbability()) {
					BoundingBox bbox = obj.getBoundingBox();
					Rectangle rectangle = bbox.getBounds();
					
					//车牌字符
					String name = obj.getClassName().trim().replace(" ", "").toUpperCase();
					
					//只识车牌字符
					if("CARID".equals(name)) {
						
						float probability = (float)obj.getProbability();
						int h = (int) (rectangle.getHeight() * img.arrayHeight() / 192D) + 1;
						int w = (int) (rectangle.getWidth() * img.arrayWidth() / 640D) + 1;
						int x = (int) (rectangle.getX() * img.arrayWidth() / 640D) + 1;//左上角的点
						int y = (int) (rectangle.getY() * img.arrayHeight() / 192D) + 1;//左上角的点
						System.out.println(probability);
						
						
						//截取对象
//		                Mat mat = new Mat();
//		                Size  si = new Size(o.getW(), o.getH());
//		                Point2f p = new Point2f(o.getX() + o.getW()/2, o.getY() + o.getH()/2);
//						getRectSubPix(img, si, p, mat);
//						o.setMat(mat);
//						o.setData(Comparison.getData(mat));
						
//						p.close();
//						si.close();
						
						if(isDraw) {
							//在原图上画box
							Rect rect = new Rect();
							rect.x(x);
							rect.y(y);
							rect.width(w);
							rect.height(h);
							// 画框
							rectangle(img, rect, new Scalar(0, 255, 0, 0), 1, LINE_8, 0);// print blue rectangle
							
							
							rect.close();
						}
						
						//保存对象
//						ids.add(o);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		src.close();
		predictor.close();
		return ids;
	}
	
}
