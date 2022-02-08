package com.example.dechuan.mapper.first.usermanage;

import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eden
 * @date 2022/2/8
 * @menu
 */
@Mapper
@Repository
public interface UserLoginMapper {

    /**
     * 查询用户是否存在
     * @param user
     * @return
     */

    List<User> doGetUserSelect(User user);

    List<Role> doGetRole(Integer userKy);
}
