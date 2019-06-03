package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;


import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * 测试Shopservice类
 */
public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService ;

    @Test
    public void testGetShopList(){
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        shop.setOwner(personInfo);
        ShopExecution shopExecution = shopService.getShopList(shop,0,2) ;
        System.out.println("店铺本页列表数: "+shopExecution.getShopList().size());
        System.out.println("拥有的店铺总数: "+shopExecution.getCount());
    }

    @Test
    @Ignore
    public void testModelfyShop() throws ShopOperationException,FileNotFoundException{
        Shop shop = new Shop();
        shop.setShopId(27L);
        shop.setShopName("涛哥饮品店");
        File shopImg = new File("F:/IDEA/image/timg5.jpg");
        InputStream inputStream = new FileInputStream(shopImg) ;
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(),inputStream);
        ShopExecution shopExecution = shopService.modifyShop(shop,thumbnail);
        System.out.println(shopExecution.getShop().toString());
    }
    @Test
    @Ignore
    public void testaddShop() throws FileNotFoundException {
        PersonInfo owner = new PersonInfo() ;
        owner.setUserId(1L);

        ShopCategory shopCategory =new ShopCategory() ;
        shopCategory.setShopCategoryId(1L);

        Area area = new Area() ;
        area.setAreaId(8);

        Shop shop = new Shop() ;
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("Test3");
        shop.setShopAddr("新都区3");
        shop.setPhone("18228980523");
        shop.setAdvice("审核中");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setCreateTime(new Date());
        File shopImg = new File("F:/IDEA/image/timg6.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(),inputStream);
        ShopExecution shopExecution = shopService.addShop(shop,thumbnail);
        assertEquals(ShopStateEnum.CHECK,shopExecution.getState());
    }
}
