package com.example.dechuan.test;

import com.example.dechuan.controller.job.LPR;
import com.example.dechuan.model.ocrlrp.PlateId;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.InputStream;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/7/27 10:18
 */
public class LprOcrTest {
    public static void main(String[] args) {
        String model = "lpr/10086.pt";
        InputStream path = Thread.currentThread().getContextClassLoader().getResourceAsStream(model);
        System.out.println(path);

/*        LPR lpr = new LPR(false, "");
        Mat src = imread("D:\\java\\worktest\\20220720\\MZE120.jpg");
        List<PlateId> ids =lpr.find(src);
        if (ids != null && ids.size() > 0) {
            for (int i = 0; i < ids.size(); i++) {
                String car_id = ids.get(i).getId();
                System.out.println("id=" + car_id + "  val=" + ids.get(i).getProbabilitie());
            }
        }*/
    }

}
