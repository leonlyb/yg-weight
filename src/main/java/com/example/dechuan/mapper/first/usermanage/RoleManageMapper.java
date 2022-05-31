package com.example.dechuan.mapper.first.usermanage;

import com.example.dechuan.model.usermanage.RoleManage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleManageMapper {


    List<RoleManage> doGetRole(RoleManage roleManage);

    int doAddRoleManage(RoleManage rm);

    int doUpdateRoleManage(RoleManage rm);

    int doDeleteRoleManage(Integer roleKy);
}