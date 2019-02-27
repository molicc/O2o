package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * @className ProductService
 * @description TODO
 * @autor Administrator
 * @date 2018/12/14 14:33
 **/
public interface ProductService {

    ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                List<ImageHolder> productImgList) throws ProductOperationException;

    Product getProductById(long productId);

    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImg);

    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
