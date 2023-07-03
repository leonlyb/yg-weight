package com.example.dechuan.service.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.workorder.WorkTempo;
import com.example.dechuan.model.workorder.WorkTempoLog;
import com.example.dechuan.model.workorder.WorkorderPrintInfo;

import java.util.List;

/**
 * @author Leon
 * @date 2023/6/16
 * @menu
 */
public interface WorkorderPrintInfoService {

       WorkorderPrintInfo printWorkorder(Integer woKy );

       int updatePrintInfo( WorkorderPrintInfo printInfo );

       int insertPrintInfo( WorkorderPrintInfo printInfo );
}
