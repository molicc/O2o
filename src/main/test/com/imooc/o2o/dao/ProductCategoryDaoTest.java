package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testInsertProductCategoryList() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别qq");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(27L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别ww");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(27L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effectNum);
    }
    @Test
    public void testDeleteProductCategory() throws Exception{
        long shopId = 27L ;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory productCategory: productCategoryList) {
            if("商品类别qq".equals(productCategory.getProductCategoryName())||"商品类别ww".equals(productCategory.getProductCategoryName())){
                System.out.println(productCategory.getProductCategoryId());
                productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(),shopId);

            }
        }

    }

    @Test
    @Ignore
    public void testQeuryByShopId() throws Exception{
        long shopId = 27 ;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为："+productCategoryList.size());
    }
}
