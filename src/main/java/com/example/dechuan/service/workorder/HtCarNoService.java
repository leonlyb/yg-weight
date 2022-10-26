package com.example.dechuan.service.workorder;

import com.example.dechuan.globalconfig.PageResult;
import com.example.dechuan.globalconfig.QueryDt;
import com.example.dechuan.model.workorder.HtCarNo;

/**
 * @author eden
 * @date 2022/10/26
 * @menu
 */
public interface HtCarNoService {
    PageResult doGetHistoryCarNoList(HtCarNo hcn, QueryDt qt);
}
