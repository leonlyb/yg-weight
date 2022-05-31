package com.example.dechuan.service.impl.usermanage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.usermanage.RoleManageMapper;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.RoleManage;
import com.example.dechuan.service.usermanage.RoleManageService;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/5/31 18:23
 */
@Transactional
@Service("RoleManageService")
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    private RoleManageMapper roleManageMapper;

    @Override
    public PageResult doGetRoleManageList(RoleManage roleManage, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<RoleManage> listRole = roleManageMapper.doGetRole(roleManage);
        return PageUtils.getPageResult(qt, new PageInfo<RoleManage>(listRole));
    }

    @Override
    public int doAddRoleManageList(RoleManage rm) {
        return roleManageMapper.doAddRoleManage(rm);
    }

    @Override
    public int doUpdateRoleManageList(RoleManage rm) {
        return roleManageMapper.doUpdateRoleManage(rm);
    }

    @Override
    public int doDeleteRoleManageList(Integer roleKy) {
        return roleManageMapper.doDeleteRoleManage(roleKy);
    }
}
