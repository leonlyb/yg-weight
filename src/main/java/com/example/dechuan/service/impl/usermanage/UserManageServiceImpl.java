package com.example.dechuan.service.impl.usermanage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.usermanage.UserLoginMapper;
import com.example.dechuan.mapper.first.usermanage.UserManageMapper;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.model.usermanage.UserRole;
import com.example.dechuan.service.usermanage.UserManageService;
import com.example.dechuan.utils.DateUtils;
import com.example.dechuan.utils.Md5Utils;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/1/30 10:13
 */
@Service("UserManageService")
@Transactional
public class UserManageServiceImpl implements UserManageService {
    private static final Logger log = LoggerFactory.getLogger(UserManageServiceImpl.class);

    @Autowired
    private UserManageMapper userManageMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public PageResult doGetUserManageList(UserManage userManage, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<UserManage> tep = new ArrayList<>();
        List<UserManage> list = userManageMapper.listuser(userManage);
        for (UserManage manage : list) {
            List<Role> listRole = userLoginMapper.doGetRole(manage.getUserKy());
            manage.setRoleList(listRole);
            tep.add(manage);
        }
        return PageUtils.getPageResult(qt, new PageInfo<UserManage>(tep));
    }

    @Override
    public int doGetAddUserManage(UserManage userManage) {
        userManage.setInsertTime(DateUtils.getCurrentDate());
        userManage.setUserCode(DateUtils.getStringAllDate());
        userManage.setPassword(Md5Utils.md5(userManage.getPassword()));
        userManageMapper.doGetAddUserManage(userManage);
        //获取userKy
        for (UserRole userRole : userManage.getRoleKyList()) {
            userRole.setUserKy(userManage.getUserKy());
            userManageMapper.doAdduserRole(userRole);
        }
        return 1;
    }

    @Override
    public int doGetEditUserManage(UserManage userManage) {
        userManage.setUpdateTime(DateUtils.getCurrentDate());
        userManage.setPassword(Md5Utils.md5(userManage.getPassword()));
        userManageMapper.doGetEditUserManage(userManage);
        return 0;
    }

    @Override
    public int doDeleteUserRole(Integer userKy) {
        return userManageMapper.doDeleteUserRole(userKy);
    }

    @Override
    public int doAddUserRole(UserRole userRole) {
        return  userManageMapper.doAdduserRole(userRole);
    }

    @Override
    public int doDeleteUserManage(Integer userKy) {
        return userManageMapper.doDeleteUserManage(userKy);
    }

}
