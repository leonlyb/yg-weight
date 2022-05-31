package com.example.dechuan.service.usermanage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.RoleManage;
import com.example.dechuan.model.usermanage.UserManage;

/**
 * @author eden
 * @date 2022/5/31
 * @menu
 */
public interface RoleManageService {
    PageResult doGetRoleManageList(RoleManage roleManage, QueryDt qt);

    int doAddRoleManageList(RoleManage rm);

    int doUpdateRoleManageList(RoleManage rm);

    int doDeleteRoleManageList(Integer roleKy);
}
