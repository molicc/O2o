package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *@className ProductDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/14 12:58
 **/
public interface ProductDao {

    /**
     * 添加商品product
     * @param product 插入的实体product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 通过productId获取商品的信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 更新商品的信息product
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /**
     * 查询商品列表并分页，可输入的条件：商品名（模糊），商品状态，
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition")Product productCondition,@Param("rowIndex")int rowIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询对应的商品总数
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品类别之前，将商品类别id置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);


}
