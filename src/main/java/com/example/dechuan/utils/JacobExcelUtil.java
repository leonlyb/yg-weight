package com.example.dechuan.utils;

import com.example.dechuan.model.workorder.WorkOrderManage;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobExcelUtil {

    /**
     * 使用jacob实现excel转PDF
     * @param tempPath 导入Excel文件路径
     * @param pdfPath 导出PDF文件路径
     */
    public static void jacobExcelToPDF(String tempPath, String pdfPath) {

        ActiveXComponent ax = null;

        Dispatch excel = null;

        try {
            ComThread.InitSTA();

            ax = new ActiveXComponent("Excel.Application");

            ax.setProperty("Visible", new Variant(false));
            //禁用宏
            ax.setProperty("AutomationSecurity", new Variant(3));

            Dispatch excels = ax.getProperty("Workbooks").toDispatch();

            Object[] obj = {
                    tempPath,
                    new Variant(false),
                    new Variant(false)
            };
            excel = Dispatch.invoke(excels, "Open", Dispatch.Method, obj, new int[9]).toDispatch();

            //转换格式
            Object[] obj2 = {
                    //PDF格式等于0
                    new Variant(0),
                    pdfPath,
                    //0=标准（生成的PDF图片不会模糊），1=最小的文件
                    new Variant(0)
            };
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, obj2, new int[1]);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (excel != null) {
                Dispatch.call(excel, "Close", new Variant(false));
            }
            if (ax != null) {
                ax.invoke("Quit", new Variant[]{});
                ax = null;
            }
            ComThread.Release();
        }
    }

    /**
     *  Leon 20230714
     * @param tempPath  excel模板文件
     * @param pdfPath 生成的pdf文件
     * @param workOrder 打印的工单信息
     * @return  是否成功
     */
    public static boolean jacobToPdf(String tempPath,  String pdfPath, WorkOrderManage workOrder) {

        ActiveXComponent app = null;

        Dispatch workbook = null;

        boolean result = false;
        try {
            ComThread.InitSTA();

            app = new ActiveXComponent("Excel.Application");
            // 设置是否显示警告信息
            app.setProperty("Visible", new Variant(false));

            //禁用宏
            app.setProperty("AutomationSecurity", new Variant(3));

            // 打开Excel文件
            Dispatch workbooks = app.getProperty("Workbooks").toDispatch();

//            workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method,
//                    new Object[]{ tempPath , new Variant(false), new Variant(false)},
//                    new int[9]).toDispatch();

            workbook = Dispatch.call(workbooks, "Open",  tempPath).toDispatch();
            // 获取第一个工作簿
//            Dispatch sheets = Dispatch.get(workbook, "Sheets").toDispatch();
//            Dispatch sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get, new Object[]{ Integer.valueOf(1) }, new int[1])
//                    .toDispatch();

            Dispatch sheet = Dispatch.call(workbook, "ActiveSheet").toDispatch();

            setSheetValue(sheet, workOrder);

            //转换PDF格式
            Object[] obj2 = {
                    //PDF格式等于0
                    new Variant(0),
                    pdfPath,
                    //0=标准（生成的PDF图片不会模糊），1=最小的文件
                    new Variant(0)
            };
            Dispatch.invoke(workbook, "ExportAsFixedFormat", Dispatch.Method, obj2, new int[1]);

            result = true;

        } catch (Exception e) {

            e.printStackTrace();
            result = false;
        } finally {
            // 关闭Excel文件
            Dispatch.call(workbook, "Close", new Variant(false));
            // 退出Excel程序
            app.invoke("Quit", new Variant[]{});

            ComThread.Release();

            return  result;
        }
    }

    public static void setSheetValue(Dispatch sheet , WorkOrderManage workOrder)
    {
        ///打印编号 货单号
        setCellValue(sheet,   6,10, workOrder.getTracingNo() );
        ///车牌号
        setCellValue(sheet, 7,3, workOrder.getCarNo() );
        ///货名
        setCellValue(sheet, 8,3, workOrder.getDriverInfo() );
        ///规格
//       setCellValue(sheet, 9,3, workOrder.getDriverInfo() + ""  );
        ///毛重（kg）
        setCellValue(sheet, 10,3, workOrder.getEntranceWeight()  );
        ///进厂时间
        setCellValue(sheet, 11,3, workOrder.getEntranceDateTime() );

        ///皮重(kg)
        setCellValue(sheet, 10,7, workOrder.getExitWeight()  );
        ///出厂时间
        setCellValue(sheet, 11,7, workOrder.getExitDateTime()  );
        ///净重(kg)
        setCellValue(sheet, 10,11, workOrder.getNetWeight()  );
        ///司磅员
        setCellValue(sheet, 11,11, workOrder.getWeighmanName()  );
    }

    public static void setCellValue(Dispatch sheet , int rowIndex, int colIndex, Object val)
    {
        String content = (val != null) ? val.toString() :  "";

        // 获取某一行某一列的单元格
        Dispatch cell = Dispatch.invoke(sheet, "Cells", Dispatch.Get, new Object[]{ Integer.valueOf( rowIndex), Integer.valueOf( colIndex ) },
                new int[1]).toDispatch();
      /*
        // 获取单元格的值
        String value = Dispatch.get(cell, "Value").toString();
        System.out.println(value);
        */
        // 设置单元格的值
        Dispatch.put(cell, "Value",  content);
    }
}
