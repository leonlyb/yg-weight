package com.example.dechuan.service.impl.usermanage;

import com.example.dechuan.mapper.first.usermanage.UserLoginMapper;
import com.example.dechuan.model.usermanage.Role;
import com.example.dechuan.model.usermanage.User;
import com.example.dechuan.service.usermanage.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/2/8 14:31
 */
@Transactional
@Service("UserLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public List<User> doGetUserSelect(User user) {
        return userLoginMapper.doGetUserSelect(user);
    }

    @Override
    public List<Role> doGetRole(Integer userKy) {
        return userLoginMapper.doGetRole(userKy);
    }
}
