package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/10.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @className ShopCategoryService
 * @description TODO
 * @autor Administrator
 * @date 2018/12/10 15:54
 **/
public interface ShopCategoryService {
    String SCLISTKEY = "shopcategorylsit";

    /**
     * 根据查询条件获取列表
     *
     * @param shopCategory
     * @return
     */
    List<ShopCategory> getShopCategory(ShopCategory shopCategory);
}
