package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminShopServiceTest extends BaseTest {
    @Autowired
    private AdminShopService adminShopService ;

    @Test
    public void testAdminShopService(){
        ShopExecution shopExecution = adminShopService.getStatusShopList(1,10);
        Assert.assertEquals(2,shopExecution.getShopList().size());
        //测试是否是正确的数据
        System.out.println(shopExecution.getShopList().get(0).toString());
    }
}
