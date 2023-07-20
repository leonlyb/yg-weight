package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.mapper.first.workorder.WorkorderPrintInfoMapper;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkorderprintInfo;
import com.example.dechuan.service.workorder.WorkorderPrintInfoService;
//import com.spire.xls.FileFormat;
//import com.spire.xls.Workbook;
//import com.spire.xls.Worksheet;

//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.WorkbookSettings;
//import jxl.read.biff.BiffException;
//import jxl.write.*;
import com.example.dechuan.utils.JacobExcelUtil;
import com.example.dechuan.utils.PoiUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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

    @Override
    public WorkorderprintInfo printWorkorder( Integer woKy ) throws IOException, InvalidFormatException {

        ///服务站点的根目录
        List<WorkOrderManage> orderList = workOrderMapper.doGetWorkOrderManageTimeStatusList(woKy);
        ///打印的工单
        WorkOrderManage workOrder =  orderList.get(0);

        if( workOrder == null || workOrder.getProductListStatus() == 1 )
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

        printInfo.setRemark( printInfo.getRemark() +";" +  nowDate);


        /// 0720 lll

        int row =  printInfoMapper.updatePrintInfo(printInfo);

        if( row > 0  && workOrder.getProductListStatus() != 1 ){

            WorkOrderManage newWorkorder = new WorkOrderManage();
            newWorkorder.setWoKy(woKy);
            newWorkorder.setProductListStatus(1);
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
        ///Resource 完整的目录
        String rootPath = this.getClass().getResource("/static/").getPath().substring(1);

        this.createDir( rootPath );

        ///要保存的 文件名称
        String newFileName = "workorder_" + woKy;

        String relaPdfPath = "files/pdf/" +  newFileName + ".pdf";

        String relaImgPath = "files/image/" +  newFileName + ".png";

        String relaExcelPath = "files/Excel/" +  newFileName + ".xlsx";

        ///模板文件
        String tempPath = rootPath +  "files/workorder_temp.xlsx";


        ///要保存的 excel文件名
        String  excelPath =  rootPath + relaExcelPath;

        ///excel 文件
        File excelFile = new File(excelPath);
        ///文件不存在
        if( !excelFile.exists() )  {
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
            //JacobExcelUtil.jacobToPdf(tempPath, pdfPath, workOrder);
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

            printInfo.setFilePath( relaPdfPath );

            printInfo.setImgPath( relaImgPath );

            printInfo.setExcelPath( relaExcelPath );

            int row = printInfoMapper.insertPrintInfo(printInfo);
        }
        return  printInfo;
    }

    void createDir( String rootPath ){

        File targetDir = new File(rootPath + "files/pdf/");

        if (!targetDir.exists()) {

            targetDir.mkdirs();
        }
        targetDir = new File(rootPath + "files/image/");

        if (!targetDir.exists()) {

            targetDir.mkdirs();
        }

        targetDir = new File(rootPath + "files/excel/");

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
