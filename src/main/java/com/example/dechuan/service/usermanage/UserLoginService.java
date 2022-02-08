package com.example.dechuan.service.usermanage;

import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.User;

import java.util.List;

/**
 * @author eden
 * @date 2022/2/8
 * @menu
 */
public interface UserLoginService {
    List<User> doGetUserSelect(User user);

    List<Role> doGetRole(Integer userKy);
}
