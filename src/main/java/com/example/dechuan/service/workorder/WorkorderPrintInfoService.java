package com.example.dechuan.service.workorder;

import com.example.dechuan.model.workorder.WorkorderprintInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


import java.io.IOException;

/**
 * @author Leon
 * @date 2023/6/16
 * @menu
 */
public interface WorkorderPrintInfoService {

       WorkorderprintInfo printWorkorder(Integer woKy ) throws IOException, InvalidFormatException;

       int updatePrintInfo( WorkorderprintInfo printInfo );

       int insertPrintInfo( WorkorderprintInfo printInfo );
}
