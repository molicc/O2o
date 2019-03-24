package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create by ft on 2019-03-21
 */
public interface AdminShopDao {
    //根据状态查询店铺信息，如果传入state=1，则查出未上架和不可用的店铺的店铺
    //state=0 ，查出未审核通过的店铺
    //查出=----店主名(name)、街道名(area_name)、店铺类别名(shop_category_name)、
    // 店铺名（shop_name）、店铺说明(shop_deac)、phone、
    //priority、create_time、last_edit_time、advice、enable_status
    //todo 设置查询返回实体
    List<Shop> queryShopListByState(@Param("status")long status,
                                    @Param("rowIndex")int rowIndex,
                                    @Param("pageSize")int pageSize);
    Integer queryCountByState(@Param("status")long status);
}
