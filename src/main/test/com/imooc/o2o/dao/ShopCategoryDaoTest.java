package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    //测试查询
    @Test
    public void testQueryShopCategory() {
        ShopCategory shopCategory = new ShopCategory() ;
        shopCategory.setShopCategoryId((long) 1);

        List<ShopCategory> shopCategoryList
                = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println("查询到数据（条）：" + shopCategoryList.size());
        //assertEquals(1, shopCategoryList.size());
    }

}
