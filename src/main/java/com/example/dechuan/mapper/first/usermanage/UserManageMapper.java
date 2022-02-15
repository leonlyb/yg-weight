package com.example.dechuan.mapper.first.usermanage;

import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.model.usermanage.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserManageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbeh_dc_user
     *
     * @mbg.generated
     */
    int doDeleteUserManage(Integer userKy);

    int doGetAddUserManage(UserManage userManage);

    List<UserManage> listuser(UserManage userManage);

    int doAdduserRole(UserRole userRole);

    int doGetEditUserManage(UserManage userManage);

    int doDeleteUserRole(Integer userKy);

}