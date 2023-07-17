package com.example.dechuan.utils;

import com.example.dechuan.model.workorder.WorkOrderManage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * org.apache.poi 操作 excel 文档
 */
public class PoiUtil {

    /**
     * 读取 excel 模板，修改工单数据另存为 新的 excel 文档
     * @param tempPath
     * @param excelPath
     * @param workOrder
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void  writeExcel(String tempPath, String excelPath, WorkOrderManage workOrder  ) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(new File( tempPath));

        Sheet sheet = workbook.getSheetAt(0);

        ///打印编号 货单号
        setCellValue(sheet,   5,9, workOrder.getTracingNo() );
        ///车牌号
        setCellValue(sheet, 6,2, workOrder.getCarNo() );
        ///货名
        setCellValue(sheet, 7,2, workOrder.getDriverInfo() );
        ///规格
//       setCellValue(sheet, 9,3, workOrder.getDriverInfo() + ""  );
        ///毛重（kg）
        setCellValue(sheet, 9,2, workOrder.getEntranceWeight() );
        ///进厂时间
        setCellValue(sheet, 10,2, workOrder.getEntranceDateTime() );

        ///皮重(kg)
        setCellValue(sheet, 9,6, workOrder.getExitWeight() );
        ///出厂时间
        setCellValue(sheet, 10,6, workOrder.getExitDateTime() );
        ///净重(kg)
        setCellValue(sheet, 9,10, workOrder.getNetWeight() );
        ///司磅员
        setCellValue(sheet, 10,10, workOrder.getWeighmanName() );

//        Row row = sheet.getRow(0);
//        Cell cell = row.getCell(1);
//        cell.setCellValue("new value");

        FileOutputStream fileOut = new FileOutputStream(excelPath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        /*
        FileOutputStream out = new FileOutputStream( imagePath );
        workbook.write(out);
        out.close();
        workbook.close();
        */
    }

    /**
     * 读取 pdf 文档，另存为图片文档
     * @param pdfPath
     * @param imagePath
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void createImage( String pdfPath, String imagePath) throws IOException, InvalidFormatException {

        File pdfFile = new File( pdfPath);
        
        PDDocument doc = PDDocument.load(pdfFile);

        PDFRenderer renderer = new PDFRenderer(doc);

        BufferedImage image = renderer.renderImageWithDPI(0, 300);

        ImageIO.write(image, "PNG", new File( imagePath ));
        
//多张图片
//        for(int i=0; i<doc.getNumberOfPages(); i++) {
//            // 创建 pdf 页面图片
//            BufferedImage image = renderer.renderImageWithDPI(i, 300);
//
//            // 保存图片文件
//            ImageIO.write(image, "PNG", new File("image-"+i+".png"));
//        }
        doc.close();
    }

    public static void setCellValue( Sheet sheet,  int rowIndex, int colIndex, Object val )
    {
        Row row = sheet.getRow(rowIndex);

        Cell cell = row.getCell( colIndex);

        if(cell != null)
        {
            String content = (val != null) ? val.toString() :  "";

            cell.setCellValue( content );
        }
    }
}
