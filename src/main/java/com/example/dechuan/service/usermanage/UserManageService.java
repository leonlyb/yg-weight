package com.example.dechuan.service.usermanage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.model.usermanage.UserRole;

/**
 * @author eden
 * @date 2022/1/30
 * @menu
 */
public interface UserManageService {
    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param um 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult doGetUserManageList(UserManage um, QueryDt qt);

    int doGetAddUserManage(UserManage um);

    int doGetEditUserManage(UserManage userManage);

    int doDeleteUserRole(Integer userKy);

    int doAddUserRole(UserRole userRole);

    int doDeleteUserManage(Integer userKy);
}
