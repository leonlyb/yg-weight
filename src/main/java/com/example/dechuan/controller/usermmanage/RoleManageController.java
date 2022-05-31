package com.example.dechuan.controller.usermmanage;

import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.globalconfig.ResultBody;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.RoleManage;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.service.usermanage.RoleManageService;
import com.example.dechuan.service.usermanage.UserManageService;
import com.example.dechuan.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @description:
 * @menu 角色管理
 * @date 2022/5/31 17:08
 */
@RestController
@RequestMapping(value = "/rolemanage")
public class RoleManageController {

    @Autowired
    private RoleManageService roleManageService;

    /**
     * @Author eden
     * @Description 角色查询
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list(RoleManage rm, QueryDt qt) {
        return ResultBody.success(roleManageService.doGetRoleManageList(rm,qt));
    }

    /**
     * @Author eden
     * @Description 角色新增
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultBody add(RoleManage rm) {
        //获取当前时间
        rm.setInsetTime(DateUtils.getCurrentDate());
        return ResultBody.success(roleManageService.doAddRoleManageList(rm));
    }
    /**
     * @Author eden
     * @Description 角色修改
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResultBody edit(RoleManage rm) {
        return ResultBody.success(roleManageService.doUpdateRoleManageList(rm));
    }

    /**
     * @Author eden
     * @Description 角色删除
     * @Date  2021/01/31 14:18
     * @status doney
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultBody delete(Integer roleKy) {
        return ResultBody.success(roleManageService.doDeleteRoleManageList(roleKy));
    }



}
