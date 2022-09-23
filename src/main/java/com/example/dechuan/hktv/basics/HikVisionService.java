package com.example.dechuan.hktv.basics;


import com.example.dechuan.hktv.ClientDemo.HCNetSDK;
import com.example.dechuan.model.carimage.CarImage;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.utils.DateUtils;
import com.example.dechuan.utils.camera.*;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.dechuan.hktv.ClientDemo.HCNetSDK.COMM_UPLOAD_PLATE_RESULT;

public class HikVisionService {
    private static Logger logger = LoggerFactory.getLogger(HikVisionService.class);
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static String m_sUsername = "admin";//设备用户名
    static String m_sPassword = "mt123456";//设备密码,默认的好像是12345
    static short m_sPort = 8000;//端口号，这是默认的
    public NativeLong lUserID;//用户句柄
    public NativeLong lAlarmHandle;//报警布防句柄
    public int lListenHandle;//报警监听句柄
    public NativeLong RemoteConfig;
    public boolean callback;
    public static int code = 5;


    @Autowired
    WorkOrderManageService workOrderManageService;

    //撤防
    public void CloseAlarmChan(NativeLong  lAlarmHandle) {
        //报警撤防
        if (lAlarmHandle.intValue() > -1) {
            if (hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle)) {
                System.out.println("先撤防成功");
                lAlarmHandle = new NativeLong(-1);
            }
        }
    }

    public  void initMemberFlowUpload(String m_sDeviceIP, int remainMinuteTime) throws InterruptedException {
        // 初始化
        Boolean flag = hCNetSDK.NET_DVR_Init();
        if (flag){
            System.out.println("初始化成功");
        }else{
            System.out.println("初始化失败");
        }
        //设置连接时间与重连时间
        hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hCNetSDK.NET_DVR_SetReconnect(100000, true);
        //设备信息, 输出参数
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        // 注册设备-登录参数，包括设备地址、登录用户、密码等
        m_strLoginInfo.sDeviceAddress = new byte[hCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());
        m_strLoginInfo.sUserName = new byte[hCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());
        m_strLoginInfo.sPassword = new byte[hCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());
        m_strLoginInfo.wPort = m_sPort;
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.write();

        //设备信息, 输出参数
         lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo,m_strDeviceInfo);
//        System.out.println("lUserID.size-->" + lUserID);
        if(lUserID.intValue()< 0){
            System.out.println("hCNetSDK.NET_DVR_Login_V30()"+"\n" +hCNetSDK.NET_DVR_GetErrorMsg(null));
            hCNetSDK.NET_DVR_Cleanup();
            return;
        }
        callback= hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(new HikVisionService().new FMSGCallBack(), null);

        //设置报警回调函数
        if (!callback){
            System.out.println("设置回调函数失败"+hCNetSDK.NET_DVR_GetLastError());
            return;
        }else {
            System.out.println("设置回调函数成功");
        }
        //启用布防
        HCNetSDK.NET_DVR_SETUPALARM_PARAM lpSetupParam = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        lpSetupParam.dwSize = 0;
        lpSetupParam.byLevel = 1;//布防优先级：0- 一等级（高），1- 二等级（中）
        lpSetupParam.byAlarmInfoType = 1;//上传报警信息类型: 0- 老报警信息(NET_DVR_PLATE_RESULT), 1- 新报警信息(NET_ITS_PLATE_RESULT)
         lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID,lpSetupParam);
       // System.out.println(m_sDeviceIP+"的布防句柄是"+lAlarmHandle);
        if (lAlarmHandle.intValue()< 0)
        {
            System.out.println("NET_DVR_SetupAlarmChan_V41 error, %d\n"+hCNetSDK.NET_DVR_GetLastError());
            hCNetSDK.NET_DVR_Logout(lUserID);
            hCNetSDK.NET_DVR_Cleanup();
            return;
        }
        System.out.println("布防成功,开始监测车辆");

        //启动监听----------------------------------------------
        int iListenPort = 8000;
        String m_sListenIP = "172.0.0.1";

        lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(m_sListenIP, (short) iListenPort, new HikVisionService().new FMSGCallBack(), null);
        if(lListenHandle < 0) {
            System.out.println("启动监听失败，错误号:" +  hCNetSDK.NET_DVR_GetLastError());
        }
        else {
            System.out.println("启动监听成功");
        }

    }
    public  class FMSGCallBack implements HCNetSDK.FMSGCallBack{
        @Override
        public void invoke(NativeLong lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            System.out.println("〈－－进入回调,开始识别车牌－－〉");
            try {
                String sAlarmType = new String();
                String[] newRow = new String[3];
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                String[] sIP = new String[2];
                switch (lCommand.intValue()) {

                    case COMM_UPLOAD_PLATE_RESULT://COMM_UPLOAD_PLATE_RESULT:
                        HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                        strPlateResult.write();
                        Pointer pPlateInfo = strPlateResult.getPointer();

                        //pAlarmInfo.getByteArray(0, strPlateResult.size())
                        pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                        strPlateResult.read();
                        try {
                            String srt3=new String(strPlateResult.struPlateInfo.sLicense,"GBK");
                            sAlarmType = sAlarmType + "：交通抓拍上传，车牌："+ srt3;
                        }
                        catch (UnsupportedEncodingException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        newRow[0] = dateFormat.format(new Date());
                        //报警类型
                        newRow[1] = sAlarmType;
                        //报警设备IP地址
                        sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                        newRow[2] = sIP[0];
//                    alarmTableModel.insertRow(0, newRow);
                        logger.info( strPlateResult.byResultType+"<-识别类型 ->"+
                                strPlateResult.dwCarPicLen+"原图<-图片长度-><-近景图->"+strPlateResult.dwPicLen  );
                        break;
                    case 0x3050:	//交通抓拍的终端图片上传
                        HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                        strItsPlateResult.write();
                        Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                        pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                        strItsPlateResult.read();
                        try {
                            byte byDangerousVehicles = strItsPlateResult.byDangerousVehicles;
                            String srt3=new String(strItsPlateResult.struPlateInfo.sLicense,"GBK");
                            sAlarmType ="是否危化品："+byDangerousVehicles+"-->"+ sAlarmType + ",车辆类型："+ CarType.getCarType(strItsPlateResult.byVehicleType+"".trim()) + ",交通抓拍上传，车牌："+ srt3;
                            Map<String,String> paramMap = new HashMap<String,String>();
                            paramMap.put("type", CarType.getCarType(strItsPlateResult.byVehicleType+"".trim()));////车辆类型
                            String filename = "D:\\ITCP Web\\hkimg\\imgUpload\\"+new String(pAlarmer.sDeviceIP).trim()+"\\"+DateUtils.getyymmdd()+"\\";//车牌
                            String carFileName = "D:\\ITCP Web\\hkimg\\carImg\\"+new String(pAlarmer.sDeviceIP).trim()+"\\"+DateUtils.getyymmdd()+"\\";//车辆原始图
                            String imgName =sf.format(new Date())+".jpg";
                            String clImgName ="cl"+imgName;//原始图片
                            String imgPath ="/resource/null/customImages/"+imgName;
                            String clImgPath ="/resource/null/customImages/"+clImgName;
                            System.out.println(srt3.substring(1,srt3.length()).trim());
                            if("黄".equals(srt3.substring(0,1).trim())){
//                            if(byDangerousVehicles==2){
                                String carno = srt3.substring(1, srt3.length()).trim();
                                String ip = new String(pAlarmer.sDeviceIP).trim();
                                paramMap.put("plateNumber", carno);//车牌号
                                paramMap.put("byCountry",srt3.substring(1, 2).trim());//省份
                                paramMap.put("byColor",srt3.substring(0, 1).trim());//车牌颜色
                                paramMap.put("cameraIp",ip);//ip地址
                                paramMap.put("picTime",dateFormat.format(new Date()));//当前时间
                                paramMap.put("wSpeed",String.valueOf(new Random().nextInt(55-5)+5));//速度
                                paramMap.put("byIllegalType", BreakRulesType.getBreakRulesType(strItsPlateResult.wIllegalType));
                                for(int i=0;i<strItsPlateResult.dwPicNum;i++) {
                                    if(strItsPlateResult.struPicInfo[i].dwDataLen>0) {
                                        FileOutputStream fout;
                                        if(strItsPlateResult.struPicInfo[i].byType==0){
                                            File file = new File(filename+imgName);
                                            if (!file.getParentFile().exists()) {
                                                file.getParentFile().mkdirs();
                                            }
                                            fout = new FileOutputStream(filename+imgName);
                                            logger.info("文件路径"+filename+imgName);
                                            //将字节写入文件
                                            long offset = 0;
                                            ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                            byte [] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                            buffers.rewind();
                                            buffers.get(bytes);
                                            fout.write(bytes);
                                            fout.close();
                                        }
                                        if(strItsPlateResult.struPicInfo[i].byType==1){
                                            File file = new File(carFileName+clImgName);
                                            if (!file.getParentFile().exists()) {
                                                file.getParentFile().mkdirs();
                                            }
                                            fout = new FileOutputStream(carFileName+clImgName);
                                            logger.info("文件路径"+carFileName+clImgName);
                                            //将字节写入文件
                                            long offset = 0;
                                            ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                            byte [] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                            buffers.rewind();
                                            buffers.get(bytes);
                                            fout.write(bytes);
                                            fout.close();
                                        }

                                    }
                                }
                                //进厂摄像头
                                if(ip.equals("192.168.1.244")){
//                                    workOrderManageService.doAutomaticWorkorder(carno,clImgName,imgName);
                                    CarNoImage.getcarno(carno,clImgName,imgName);
                                }


                                //上传到服务器
//                                HTTPClientUtils.imgUpload("http://172.172.1.21:8080/api/app/manager/images/uploadImages",filename+imgName);
//                                HTTPClientUtils.imgUpload("http://172.172.1.21:8080/api/app/manager/images/uploadImages",carFileName+clImgName);
                                //保存到数据库
//                                ConnectDatabase.insertClsb(paramMap,imgPath,clImgPath);
                            }
                        }
                        catch (UnsupportedEncodingException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                }
                logger.info("识别信息：---》" +sAlarmType +" ip:"+new String(pAlarmer.sDeviceIP).trim()+"时间:"+dateFormat.format(new Date()));
            }catch (Exception e){
                e.printStackTrace();
                logger.info(e.toString()+"故障");
            }
        }
    }



//    public void getcarno(String carno, String clImgName, String imgName){
//
//    }
}



