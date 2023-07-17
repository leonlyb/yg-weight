package com.example.dechuan.controller.workorder;

import com.example.dechuan.globalconfig.ResultBody;

import com.example.dechuan.model.workorder.WorkorderprintInfo;
import com.example.dechuan.service.workorder.WorkorderPrintInfoService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Leon
 * @description: 打印工单 api
 * @menu
 * @date 2023/06/15
 */
@RestController
@RequestMapping(value = "/work")
public class WorkorderPrintInfoController {

    private static final Logger log = LoggerFactory.getLogger(WorkorderPrintInfoController.class);

    @Autowired
    private WorkorderPrintInfoService WorkorderPrintInfoService;

    @RequestMapping("/print")
    @ResponseBody
    public ResultBody printWorkorder(Integer woKy ) throws IOException, InvalidFormatException {

        WorkorderprintInfo printInfo = WorkorderPrintInfoService.printWorkorder(woKy );

        return ResultBody.success( printInfo );
    }

}
