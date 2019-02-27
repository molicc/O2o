package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/12.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *@className ProductCategoryDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/12 17:09
 **/
public interface ProductCategoryDao {
    /**
     * 通过shopId查询shopCategory
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);


    /**
     * 批量插入
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定类别
     * @param procategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId")long procategoryId,@Param("shopId")long shopId);
}
