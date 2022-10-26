package com.example.dechuan.service.impl.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.mapper.first.workorder.HtCarNoMapper;
import com.example.dechuan.model.workorder.HtCarNo;
import com.example.dechuan.model.workorder.WorkOrderManage;
import com.example.dechuan.service.workorder.HtCarNoService;
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
 * @date 2022/10/26 10:27
 */
@Transactional
@Service("HtCarNoService")
public class HtCarNoServiceImpl implements HtCarNoService {

    @Autowired
    HtCarNoMapper htCarNoMapper;

    @Override
    public PageResult doGetHistoryCarNoList(HtCarNo hcn, QueryDt qt) {
        PageHelper.startPage(qt.getPageNum(), qt.getPageSize());
        List<HtCarNo> list = htCarNoMapper.doGetHistoryCarNoList(hcn);
        return PageUtils.getPageResult(qt, new PageInfo<HtCarNo>(list));
    }
}
