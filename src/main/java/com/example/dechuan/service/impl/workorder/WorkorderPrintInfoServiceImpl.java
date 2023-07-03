package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.workorder.HtCarNoMapper;
import com.example.dechuan.mapper.first.workorder.WorkOrderManageMapper;
import com.example.dechuan.mapper.first.workorder.WorkorderPrintInfoMapper;
import com.example.dechuan.model.workorder.HtCarNo;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkorderPrintInfo;
import com.example.dechuan.service.workorder.HtCarNoService;
import com.example.dechuan.service.workorder.WorkorderPrintInfoService;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;

/**
 * @author Leon
 * @description:
 * @menu
 * @date
 */
@Transactional
@Service("WorkorderPrintInfoService")
public class WorkorderPrintInfoServiceImpl implements WorkorderPrintInfoService {

    @Autowired
    WorkOrderManageMapper workOrderMapper;

    @Autowired
    WorkorderPrintInfoMapper printInfoMapper;

    @Autowired
    ServletContext servletContext;

    @Override
    public WorkorderPrintInfo printWorkorder(Integer woKy ) {

        WorkOrderManage workOrderParame = new WorkOrderManage();

        workOrderParame.setWoKy(woKy);

        List<WorkOrderManage> workOrderManageList = workOrderMapper.doGetWorkOrderManageList(workOrderParame);

        if( workOrderManageList == null || workOrderManageList.size() < 1 )
        {
            return null;
        }
        ///工单
        WorkOrderManage workOrder = workOrderManageList.get(0);

        //创建一个Workbook实例并加载Excel文件
        Workbook workbook = new Workbook();

        ///服务站点的根目录
        String rootPath = servletContext.getRealPath("/");

        ///模板文件
        String tempFile = "templates/wororder_template.xlsx";

        //要保存的 图片文件名
        String relaImgPath = "templates/bmp/workorder_" + woKy+ ".bmp";
        ///要保存的 pdf 文件名
        String relaPdfPath = "templates/pdf/workorder_" + woKy+ ".pdf";

        String fullPdfPath = rootPath + relaPdfPath;

        File pdfFile = new File(fullPdfPath);

        ///文件不存在
        if( !pdfFile.exists() )  {
            ///加载模板
            workbook.loadFromFile( rootPath +  tempFile );

            Worksheet worksheet = workbook.getWorksheets().get(0);

            ///打印编号 货单号
            worksheet.setCellValue(6,10, workOrder.getTracingNo() +"" );
            ///车牌号
            worksheet.setCellValue(3,7, workOrder.getCarNo() );
            ///货名
            worksheet.setCellValue(3,8, workOrder.getDriverInfo() );
            ///规格
//            worksheet.setCellValue(3,9, workOrder.getDriverInfo() + ""  );
            ///毛重（kg）
            worksheet.setCellValue(3,10, workOrder.getEntranceWeight() + ""  );
            ///进厂时间
            worksheet.setCellValue(3,11, workOrder.getEntranceDateTime()  );

            ///皮重(kg)
            worksheet.setCellValue(10,7, workOrder.getExitWeight() + ""  );
            ///出厂时间
            worksheet.setCellValue(11,7, workOrder.getExitDateTime()  );
            ///净重(kg)
            worksheet.setCellValue(10,9, workOrder.getNetWeight() + ""  );
            ///司磅员
            worksheet.setCellValue(11,9, workOrder.getWeighmanName()  );

            workbook.setActiveSheetIndex(0);

            //设置转换后的PDF页面高宽适应工作表的内容大小
            workbook.getConverterSetting().setSheetFitToPage(true);

            //将生成的文档保存到指定路径
            workbook.saveToFile( fullPdfPath, FileFormat.PDF);

            ///完整的图片路径
            String fullImgPath = rootPath + relaImgPath;
            ///图片文件
            File imgFile = new File(fullImgPath);
            /// 没有图片则保存图片
            if( !imgFile.exists()){
                workbook.saveToFile( fullImgPath, FileFormat.Bitmap);
            }
        }
        WorkorderPrintInfo printInfo  = printInfoMapper.getPrintInfo(woKy);

        Date date = new Date();// 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

        if(printInfo == null)
        {
            printInfo = new WorkorderPrintInfo();

            printInfo.setWoKy(woKy);

            printInfo.setPrintCount(1);
            printInfo.setPrintTime( sdf.format(date) );
            printInfo.setFilePath( relaPdfPath );
            printInfo.setImgPath( relaImgPath );
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

    @Override
    public int updatePrintInfo( WorkorderPrintInfo printInfo )
    {
        return  printInfoMapper.updatePrintInfo(printInfo);
    }

    @Override
    public int insertPrintInfo( WorkorderPrintInfo printInfo )
    {
        return  printInfoMapper.insertPrintInfo(printInfo);
    }

}
