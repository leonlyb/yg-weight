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
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

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
    public WorkorderprintInfo printWorkorder(Integer woKy ) throws IOException, InvalidFormatException {

        ///服务站点的根目录
        List<WorkOrderManage> orderList = workOrderMapper.doGetWorkOrderManageTimeStatusList(woKy);
        ///打印的工单
        WorkOrderManage workOrder =  orderList.get(0);

        if( workOrder == null )
        {
            return null;
        }
        ///Resource 完整的目录
        String rootPath = this.getClass().getResource("/").getPath().substring(1);

        ///要保存的 pdf 目录名
        String pdfDir = rootPath + "files/pdf/";

        ///要保存的 文件名称
        String newFileName = "workorder_" + woKy;

        String relaPdfPath = "files/pdf/" +  newFileName + ".pdf";

        String relaImgPath = "files/image/" +  newFileName + ".png";

        String relaExcelPath = "files/Excel/" +  newFileName + ".xlsx";

        ///要保存的 pdf 文件名
        String  pdfPath =  pdfDir +  newFileName + ".pdf";

        ///模板文件
        String tempPath = rootPath +  "files/workorder_temp.xlsx";


        File pdfFile = new File(pdfPath);

        ///文件不存在
        if( !pdfFile.exists() )  {

            File dir = new File(pdfDir);

            if (!dir.exists()) {

                dir.mkdirs();
            }
            JacobExcelUtil.jacobToPdf(tempPath, pdfPath, workOrder);
        }

        ///要保存的 excel 目录名
        String  excelDirPath =  rootPath +  "files/excel/";

        File excelDir = new File(excelDirPath);

        if (!excelDir.exists()) {

            excelDir.mkdirs();
        }

        ///要保存的 excel文件名
        String  excelPath =  excelDirPath +  newFileName + ".xlsx";

        File excelFile = new File(excelPath);

        ///要保存的 img 目录名
        String  imageDir =  rootPath +  "files/image/";

        File imageDirFile = new File(imageDir);

        if (!imageDirFile.exists()) {

            imageDirFile.mkdirs();
        }

        ///要保存的 image 文件名
        String  imagePath =  imageDir +  newFileName + ".png";

        File imageFile = new File(imagePath);

        ///文件不存在
        if( !excelFile.exists() )  {

            PoiUtil.writeExcel(tempPath, excelPath,   workOrder);
        }

        ///图片文件是否存在
        if( !imageFile.exists() )  {
            PoiUtil.createImage( pdfPath, imagePath );
        }

        WorkorderprintInfo printInfo  = printInfoMapper.getPrintInfo(woKy);

        Date date = new Date();// 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

        if(printInfo == null)
        {
            printInfo = new WorkorderprintInfo();

            printInfo.setWoKy(woKy);

            printInfo.setPrintCount(1);

            printInfo.setPrintTime( sdf.format(date) );

            printInfo.setFilePath( relaPdfPath );

            printInfo.setImgPath( relaImgPath );

            printInfo.setExcelPath( relaExcelPath );

            printInfoMapper.insertPrintInfo(printInfo);
        }
        else
        {
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

            printInfo.setPrintTime( sdf.format(date) );

            printInfoMapper.updatePrintInfo(printInfo);
        }
        return  printInfo;
    }

//    boolean saveImg( WritableWorkbook  workbook, File file)
//    {
//        // 2. 读取Excel转为BufferedImage
////        BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
//        WritableImage wi = new WritableImage(0, 0, img);
////
////
//        Sheet sheet = workbook.getSheet(0);
//
//        // 创建BufferedImage
//        BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
//
//// 绘制Excel到图片
//        jxl.Demo.showSheet(sheet, img);
//
//// 保存图片
//        ImageIO.write(img, "png", file);
//    }

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
