package com.example.dechuan.controller.usermmanage;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.service.usermanage.UserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @description:
 * @menu 用户管理
 * @date 2022/1/30 10:02
 */
@RestController
@RequestMapping(value = "/usermanage")
public class UserManageController {

    private static final Logger log = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private UserManageService userManageService;

    /**
     * @Author eden
     * @Description 用户查询
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(UserManage um, QueryDt qt) {
        return ResultBody.success(userManageService.doGetUserManageList(um,qt));
    }

}
