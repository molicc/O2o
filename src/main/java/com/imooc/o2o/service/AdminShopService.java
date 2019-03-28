package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;

public interface AdminShopService {
    /**
     * create by ft on 2019-03-22
     * 根据商店的状态返回不同的商店列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getStatusShopList(int pageIndex, int pageSize);

    /**
     * 修改店铺的审核状态
     * @param enableStatus
     * @param shopId
     * @return
     */
    Boolean modifyShopState(int enableStatus,long shopId);

}
