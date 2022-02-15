package com.example.dechuan.controller.usermmanage;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.model.usermanage.UserMessage;
import com.example.dechuan.model.usermanage.UserRole;
import com.example.dechuan.service.usermanage.UserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

/**
     * @Author eden
     * @Description 用户新增
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultBody add(@RequestBody UserManage userManage) {
        //用户名密码不能为空
        if(userManage.getUserName().equals("") || userManage.getPassword().equals("") ){
            return ResultBody.success(UserMessage.USERNAME_PASSWORD);
        }
        //确认密码后进行效验
        if(userManage.getPassword().matches(UserMessage.REGEX) != true){
            return ResultBody.success(UserMessage.REGEX_PASSWORD);
        }
        return ResultBody.success(userManageService.doGetAddUserManage(userManage));
    }

    /**
     * @Author eden
     * @Description 用户修改
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResultBody edit(@RequestBody UserManage userManage) {
        if(userManage.getUserKy() == null){
            return ResultBody.success("主键不能为空");
        }
        //用户名密码不能为空
        if(userManage.getUserName().equals("") || userManage.getPassword().equals("") ){
            return ResultBody.success(UserMessage.USERNAME_PASSWORD);
        }
        if(!userManage.getPassword().equals(userManage.getConfirmPassword())){
            return ResultBody.success("两次输入的密码不一致");
        }
        if(userManage.getRoleKyList().size() <= 0 || userManage.getRoleKyList() == null){
            return ResultBody.success("请给用户至少分配一个权限");
        }
        //确认密码后进行效验
        if(userManage.getPassword().matches(UserMessage.REGEX) != true){
            return ResultBody.success(UserMessage.REGEX_PASSWORD);
        }
        //更新用户表
        userManageService.doGetEditUserManage(userManage);
        //更新成功后去更新角色关联表/先用userKy去删除然后循环新增
        userManageService.doDeleteUserRole(userManage.getUserKy());
        for (UserRole userRole : userManage.getRoleKyList()) {
            userRole.setUserKy(userManage.getUserKy());
            userManageService.doAddUserRole(userRole);
        }
        return ResultBody.success("更新成功");
    }


    /**
     * @Author eden
     * @Description 用户删除
     * @Date  2022/02/14 14:18
     * @status doney
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public ResultBody delete(@RequestParam Integer userKy) {
        return ResultBody.success(userManageService.doDeleteUserManage(userKy));
    }




}
