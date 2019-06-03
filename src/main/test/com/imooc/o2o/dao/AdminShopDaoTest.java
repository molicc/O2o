package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Shop;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminShopDaoTest extends BaseTest {
    @Autowired
    AdminShopDao adminShopDao ;

    @Test
    public void testAdminShopDao(){
        List<Shop> statusShopList = adminShopDao.queryShopListByState(0,1);
        Assert.assertEquals(1,statusShopList.size());
        //测试数据是否全部取出正常？发现有的词条取不出数据
        System.out.println(statusShopList.get(0).toString());
    }
    @Test
    public void testModifyShopState(){
        Assert.assertEquals(1,adminShopDao.modifyShopState(1,25));
    }
}
