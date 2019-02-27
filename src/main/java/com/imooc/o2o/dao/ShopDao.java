package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/8.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *@className ShopDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/8 14:00
 **/
public interface ShopDao {
    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域Id,owner
     * @param shop
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shop")Shop shop,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 返回queryShopList总数
     * @param shop
     * @return
     */
    int queryShopCount(@Param("shop")Shop shop);
    /**
     * 通过 shopId 查询店铺信息
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
    /**
     * 新增店铺
     * @param shop
     * @return 1:成功 0:失败
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
