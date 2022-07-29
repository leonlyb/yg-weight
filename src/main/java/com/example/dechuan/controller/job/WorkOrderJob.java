package com.example.dechuan.controller.job;

import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.carimage.ConfigurationInformation;
import com.example.dechuan.model.ocrlrp.PlateId;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.carimage.CarImageService;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.DateUtils;
import org.bytedeco.opencv.opencv_core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/7/27 17:04
 */

@Component
public class WorkOrderJob {
    private static final Logger log = LoggerFactory.getLogger(WorkOrderJob.class);

    @Autowired
     WorkOrderManageService workOrderManageService;
     @Autowired
     CarImageService carImageService;

    /**
     * 每五秒轮询一次
     */
//     @Scheduled(cron="0/5 * * * * ?") //每个5秒执行一次
    public  void workorderjob() {
//         String carname =null,nocarname=null;
        //循环文件夹。获取图片
        WorkOrderManage wom = new WorkOrderManage();
         CarImage ci = new CarImage();
        try{
            File file = new File(ConfigurationInformation.begin_path);
            File[] listFiles = file.listFiles();
            if(listFiles.length <= 0){
                log.info(ConfigurationInformation.FILE_IS_NULL);
            } else {
            //文件夹下有文件
                File endfile = new File(ConfigurationInformation.end_path);
                if(!endfile .isDirectory()){
                    endfile .mkdirs();
                }
           //用数组接收
            File fa[] = file.listFiles();
            for (int i = 0; i < fa.length; i++) {//循环遍历
                File fs = fa[i];//获取数组中的第i个
                if (fs.isDirectory()) {
                    log.info(fs.getName() + " [目录]");//如果是目录就输出
                } else {
                    //图片名字
                    File startFile = new File(ConfigurationInformation.begin_path+"\\"+fs.getName());
                    if(fs.getName().substring(0, 2).equals(ConfigurationInformation.CAR_NO)){
                        ci.setCarnoimage("http://"+WorkOrderJob.getHostIp()+ConfigurationInformation.IMAGE_URL+"carno" + fs.getName().substring(2));
                        if (startFile.renameTo(new File(ConfigurationInformation.end_path+"\\carno"+fs.getName().substring(2)))) {
                                log.info("文件移动成功！文件名：{"+fs.getName()+"} 目标路径：{"+ConfigurationInformation.end_path+"}");
                            } else {
                                log.info("文件移动失败！文件名：{"+fs.getName()+"} ");
                        }
                    }else{
                        //无车牌图片
                        ci.setVormalvehicleimage("http://"+WorkOrderJob.getHostIp()+ConfigurationInformation.IMAGE_URL + startFile.getName());
                        startFile.renameTo(new File(ConfigurationInformation.end_path+"/"+startFile.getName()));
                        //根据图片新路径，获取车牌号生成工作单
                        LPR lpr = new LPR(false, "");
                        Mat src = imread(ConfigurationInformation.end_path+"\\"+startFile.getName());
                        List<PlateId> ids =lpr.find(src);
                        wom.setEntranceDateTime(DateUtils.getCurrentDate());
                        if (ids != null && ids.size() > 0) {
                            //识别成功，生成工单
                                String car_id = ids.get(0).getId();
                               log.info("id=" + car_id + "  val=" + ids.get(0).getProbabilitie());
                               wom.setCarNo(car_id);
                               workOrderManageService.doAddWorkOrderManage(wom);
                               ci.setWoky(wom.getWoKy());
                        }else{
                            //识别未成功，生成工单
                            wom.setCarNo("未识别车牌");
                            workOrderManageService.doAddWorkOrderManage(wom);
                            ci.setWoky(wom.getWoKy());
                        }
                    }

                }
            }
                //存储图片路径
                carImageService.doAddImageUrl(ci);
        }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private static String getHostIp(){
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                // 去除回环接口，子接口，未运行和接口
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                if (!netInterface.getDisplayName().contains("Intel") && !netInterface.getDisplayName().contains("Realtek")) {
                    continue;
                }
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null) {
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
                break;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
//            String path = "D:\\java\\worktest\\"+DateUtils.getyymmdd()+"\\";
//            String picName = "车牌20220719210822383.jpg";
//        picName.substring(2);
//        System.out.println(picName.substring(2));
        WorkOrderJob.getHostIp();
        System.out.println(WorkOrderJob.getHostIp());
//            String newName = "carno20220719210822383.jpg";
//            File file=new File(path + picName); //指定文件名及路径
//
//            if (file.renameTo(new File(path+newName))) {
//                System.out.println("修改成功!");
//            }
//            else{
//                System.out.println("修改失败");
//            }

    }



}
