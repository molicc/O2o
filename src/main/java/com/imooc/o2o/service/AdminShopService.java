package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;

public interface AdminShopService {
    /**
     * create by ft on 2019-03-22
     * 根据商店的状态返回不同的商店列表
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getStatusShopList(long status, int pageIndex, int pageSize);
}
