package com.example.dechuan.controller.job;

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
import com.example.dechuan.model.ocrlrp.BaseDetection;
import com.example.dechuan.model.ocrlrp.Object;
import com.example.dechuan.model.ocrlrp.ObjectType;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * yolov5 车牌四个点对象检测
 * 
 * @author xiaoming
 */
public class YoloV5CarPlayPointsDetection extends BaseDetection {
	private static String model_path = "lpr/cayplaypoints.torchscript.pt";
	private static String name_path = "lpr/cayplaypoints.names";
	private static String car_path = "lpr/car.jpg";

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
				.optModelUrls(model_path).optModelName(model_path).optTranslator(translator).optEngine("PyTorch")
				.build();

		try {
			model = ModelZoo.loadModel(criteria);
			System.out.println("Car play points detection:" + model);
			
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
	 * 接收640*640的图片
	 * 
	 * @param img
	 * @param isDraw
	 * @return
	 */
	public static List<Object> detection(Mat img, boolean isDraw, double thresh) {
		List<Object> obs = new ArrayList<Object>();
		if (img == null || img.empty()) {
			return obs;
		}

		Mat src = new Mat();
		Size size = new Size(640, 640);
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

		// 转换Image
		Image djl_img = ImageFactory.getInstance().fromImage(bimg);

		// 构建预测者
		Predictor<Image, DetectedObjects> predictor = model.newPredictor();

		try {
			// 检测出全部对象
			DetectedObjects objects = predictor.predict(djl_img);

			for (DetectedObject obj : objects.<DetectedObject>items()) {
				if (thresh < obj.getProbability()) {
					BoundingBox bbox = obj.getBoundingBox();
					Rectangle rectangle = bbox.getBounds();

					// coco对象
					ObjectType type = null;
					try {
						type = ObjectType.valueOf(obj.getClassName().trim().replace(" ", "").toUpperCase());
					} catch (Exception e) {
					}

					// 只识车牌四个点
//					System.out.println(type + ":" + obj.getProbability());
					if (ObjectType.CARPLAYPOINT == type) {
						Object o = new Object();
						o.setType(type);
						o.setProbability((float) obj.getProbability());
						o.setH((int) (rectangle.getHeight() * img.arrayHeight() / 640));
						o.setW((int) (rectangle.getWidth() * img.arrayWidth() / 640));
						o.setX((int) (rectangle.getX() * img.arrayWidth() / 640));// 左上角的点
						o.setY((int) (rectangle.getY() * img.arrayHeight() / 640));// 左上角的点


						if (isDraw) {
							// 在原图上画box
							String text = String.format("%s: %.2f", type.name(), obj.getProbability());

							Rect rect = new Rect();
							rect.x(o.getX());
							rect.y(o.getY());
							rect.width(o.getW());
							rect.height(o.getH());
							// 画框
							rectangle(img, rect, new Scalar(0, 255, 0, 0), 2, LINE_8, 0);// print blue rectangle
							// 画名字
							Point pt = new Point(rect.x(), rect.y());
							putText(img, text, pt, FONT_HERSHEY_PLAIN, 2, new Scalar(0, 0, 255, 0));

							pt.close();
							rect.close();
						}

						// 保存对象
						obs.add(o);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		src.close();
		predictor.close();
		return obs;
	}

}
