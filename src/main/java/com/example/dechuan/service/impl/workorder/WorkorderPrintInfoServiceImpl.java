package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.mapper.first.workorder.WorkorderPrintInfoMapper;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkorderprintInfo;
import com.example.dechuan.service.workorder.WorkorderPrintInfoService;

import com.example.dechuan.utils.JacobExcelUtil;
import com.example.dechuan.utils.PoiUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.ServletContext;
import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Leon
 * @description:
 * @menu
 * @date
 */

@Service("WorkorderPrintInfoService")
public class WorkorderPrintInfoServiceImpl implements WorkorderPrintInfoService {

    @Autowired
    WorkOrderManageMapper workOrderMapper;

    @Autowired
    WorkorderPrintInfoMapper printInfoMapper;

    public  String FILE_UPLOAD_DIR;//设置保存pdf、图片等文件的存储路径
    @Value("${file.upload.dir}") //需要spring注入这个value，因此需要创建bean，使用@component注解
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    String pathPatt = "workorders/";

    @Override
    public WorkorderprintInfo printWorkorder( Integer woKy ) throws IOException, InvalidFormatException {

        ///服务站点的根目录
        List<WorkOrderManage> orderList = workOrderMapper.doGetWorkOrderManageTimeStatusList(woKy);
        ///打印的工单
        WorkOrderManage workOrder =  orderList.get(0);

        Integer printStatus = workOrder.getProductListStatus();

        Integer status = Integer.valueOf(1);

        if( workOrder == null ||  printStatus == status )
        {
            return null;
        }
        WorkorderprintInfo printInfo  = printInfoMapper.getPrintInfo(woKy);

        if(printInfo == null)
        {
            printInfo = previewWorkorder(woKy);
        }
        Date date = new Date();// 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

        String nowDate = sdf.format(date);

        Integer pcount = printInfo.getPrintCount();

        if(pcount != null)
        {
            pcount ++;
        }
        else
        {
            pcount = 1;
        }
        printInfo.setPrintCount( pcount );

        printInfo.setPrintTime(  nowDate );

        String remark = printInfo.getRemark();

        if(remark == null){

            remark = nowDate;
        }
        else{
            remark += ";" + nowDate;
        }
        printInfo.setRemark(  remark );

        /// 0720 lll
        int row =  printInfoMapper.updatePrintInfo(printInfo);

        if( row > 0  ){

            WorkOrderManage newWorkorder = new WorkOrderManage();

            newWorkorder.setWoKy(woKy);

            newWorkorder.setProductListStatus(status);

            workOrderMapper.doEditWorkOrderManage(newWorkorder);
        }
        return  printInfo;
    }

    @Override
    public WorkorderprintInfo previewWorkorder( Integer woKy ) throws IOException, InvalidFormatException
    {
        List<WorkOrderManage> orderList = workOrderMapper.doGetWorkOrderManageTimeStatusList(woKy);
        ///打印的工单
        WorkOrderManage workOrder =  orderList.get(0);

        if( workOrder == null )
        {
            return null;
        }

        ///Resource 完整的目录  D:/workorder/
        String rootPath =  FILE_UPLOAD_DIR;

        //getStaticLocations();
        //this.getClass().getResource("/static/").getPath().substring(1);

        Date date = new Date();// 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");// 格式化时间

        String currMonth = sdf.format(date) + "/";

        this.createDir( rootPath , currMonth);

        ///要保存的 文件名称
        String newFileName = "workorder_" + woKy;

        String relaPdfPath =  currMonth   +  newFileName + ".pdf";

        String relaImgPath =  currMonth +  newFileName + ".png";

        String relaExcelPath =  currMonth +  newFileName + ".xlsx";

        ///模板文件
        String tempPath = rootPath +  "workorder_temp.xlsx";

        ///要保存的 excel文件名
        String  excelPath =  rootPath + relaExcelPath;

        ///excel 文件
        File excelFile = new File(excelPath);
        ///文件不存在
        if( !excelFile.exists()  )  {
             //excelFile.delete();
            ///根据写入新文件
            PoiUtil.writeExcel(tempPath, excelPath,  workOrder);
        }

        ///要保存的 pdf 完整文件名
        String  pdfPath =  rootPath +  relaPdfPath;

        File pdfFile = new File(pdfPath);

        ///pdf文件不存在
        if( !pdfFile.exists()  )  {
             ///转换成pdf文件
            JacobExcelUtil.jacobExcelToPDF(excelPath, pdfPath);
        }

        ///要保存的 image 文件名
        String  imagePath =  rootPath + relaImgPath;

        File imageFile = new File(imagePath);

        ///图片文件是否存在
        if( !imageFile.exists() )  {

            PoiUtil.createImage( pdfPath, imagePath );
        }
        WorkorderprintInfo printInfo  = printInfoMapper.getPrintInfo(woKy);

        if(printInfo == null)
        {
            printInfo = new WorkorderprintInfo();

            printInfo.setWoKy(woKy);

            printInfo.setPrintCount(0);

            printInfo.setFilePath( pathPatt +  relaPdfPath );

            printInfo.setImgPath( pathPatt +  relaImgPath );

            printInfo.setExcelPath( pathPatt +  relaExcelPath );

            int row = printInfoMapper.insertPrintInfo(printInfo);
        }
        return  printInfo;
    }

    void createDir( String rootPath, String currMonth ){

        File targetDir = new File(rootPath +  currMonth );

        if (!targetDir.exists()) {

            targetDir.mkdirs();
        }
        targetDir = new File(rootPath +  currMonth );

        if (!targetDir.exists()) {

            targetDir.mkdirs();
        }

        targetDir = new File(rootPath +  currMonth );

        if (!targetDir.exists()) {

            targetDir.mkdirs();
        }
    }

    @Override
    public int updatePrintInfo( WorkorderprintInfo printInfo )
    {
        return  printInfoMapper.updatePrintInfo(printInfo);
    }

    @Override
    public int insertPrintInfo( WorkorderprintInfo printInfo )
    {
        return  printInfoMapper.insertPrintInfo(printInfo);
    }

}
