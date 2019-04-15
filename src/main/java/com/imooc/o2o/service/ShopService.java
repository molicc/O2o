package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/9.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

import java.io.InputStream;

/**
 *
 *@className ShopService
 *@description TODO
 *@autor Administrator
 *@date 2018/12/9 15:17
 **/
public interface ShopService {
    /**
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return 返回count和list
     */
     ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 注册店铺
     * @param shop
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder);

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括图片的处理
     * @param shop
     * @return
     */
    ShopExecution modifyShop(Shop shop,ImageHolder imageHolder);
}
