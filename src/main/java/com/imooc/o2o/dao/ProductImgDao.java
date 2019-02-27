package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

/**
 *
 *@className ProductImgDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/14 12:59
 **/
public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    List<ProductImg> queryProductByProductId(long productId);
}
