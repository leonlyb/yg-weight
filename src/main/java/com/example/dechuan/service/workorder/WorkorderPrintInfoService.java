package com.example.dechuan.service.workorder;

import com.example.dechuan.model.workorder.WorkorderprintInfo;

/**
 * @author Leon
 * @date 2023/6/16
 * @menu
 */
public interface WorkorderPrintInfoService {

       WorkorderprintInfo printWorkorder(Integer woKy );

       int updatePrintInfo( WorkorderprintInfo printInfo );

       int insertPrintInfo( WorkorderprintInfo printInfo );
}
