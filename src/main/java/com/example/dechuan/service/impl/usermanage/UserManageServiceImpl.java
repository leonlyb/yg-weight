package com.example.dechuan.service.impl.usermanage;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.usermanage.UserManageMapper;
import com.example.dechuan.model.usermanage.UserManage;
import com.example.dechuan.service.usermanage.UserManageService;
import com.example.dechuan.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/1/30 10:13
 */
@Transactional
@Service("UserManageService")
public class UserManageServiceImpl implements UserManageService {
    private static final Logger log = LoggerFactory.getLogger(UserManageServiceImpl.class);

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public PageResult doGetUserManageList(UserManage userManage, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<UserManage> list = userManageMapper.listuser(userManage);
        return PageUtils.getPageResult(qt, new PageInfo<UserManage>(list));
    }
}
