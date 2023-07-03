package com.example.dechuan.controller.workorder;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;

import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.model.workorder.WorkorderPrintInfo;
import com.example.dechuan.service.workorder.WorkOrderManageService;
import com.example.dechuan.service.workorder.WorkorderPrintInfoService;
import com.spire.xls.Worksheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

/**
 * @author Leon
 * @description: 打印工单 api
 * @menu
 * @date 2023/06/15
 */
@RestController
@RequestMapping(value = "/workorder")
public class WorkorderPrintInfoController {

    private static final Logger log = LoggerFactory.getLogger(WorkorderPrintInfoController.class);

    @Autowired
    private WorkorderPrintInfoService WorkorderPrintInfoService;


    @RequestMapping("/print")
    @ResponseBody
    public ResultBody printWorkorder(Integer woKy ) {

        WorkorderPrintInfo printInfo = WorkorderPrintInfoService.printWorkorder(woKy );

        return ResultBody.success( printInfo );

//        WorkOrderManage workOrder = WorkOrderManageService.doGetWorkOrderManageTimeStatusList(woKy).get(0);
//        //创建一个Workbook实例并加载Excel文件
//        Workbook workbook = new Workbook();
//
//        String tempPath ="E:\\work\\dechuan\\Java\\Project\\2023\\yg-weight\\src\\main\\resources\\templates\\";
//
//        workbook.loadFromFile( tempPath + "wororder_template.xlsx");
//
//        Worksheet worksheet = workbook.getWorksheets().get(0);
//
//        worksheet.setCellValue(6,10, workOrder.getTracingNo() +"" );
//        worksheet.setCellValue(3,7, workOrder.getCarNo() );
//        worksheet.setCellValue(3,8, workOrder.getDriverInfo() );
//        worksheet.setCellValue(3,9, workOrder.getEntranceWeight() + ""  );
//        worksheet.setCellValue(3,10, workOrder.getEntranceDateTime() + ""  );
//
//        worksheet.setCellValue(10,7, workOrder.getExitWeight() + ""  );
//        worksheet.setCellValue(11,7, workOrder.getExitDateTime() + ""  );
//        worksheet.setCellValue(10,9, workOrder.getNetWeight() + ""  );
//        worksheet.setCellValue(11,9, workOrder.getWeighmanName() + ""  );
//
//        workbook.setActiveSheetIndex(0);
//
//        //设置转换后的PDF页面高宽适应工作表的内容大小
//        workbook.getConverterSetting().setSheetFitToPage(true);
//
//        //将生成的文档保存到指定路径
//        workbook.saveToFile( tempPath + "wororder_template.pdf", FileFormat.PDF);


    }



}
