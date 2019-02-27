package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/10.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *@className ShopCategoryDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/10 15:18
 **/
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
