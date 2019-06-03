package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao ;

    @Test
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        shopCondition.setShopName("涛哥");
//        ShopCategory shopCategoryChild = new ShopCategory();
//        ShopCategory shopCategoryParent = new ShopCategory();
//        shopCategoryParent.setShopCategoryId(3L);
//        shopCategoryChild.setParent(shopCategoryParent);
//        shopCondition.setShopCategory(shopCategoryChild);
        int count = shopDao.queryShopCount(shopCondition);

        List<Shop> shopList = shopDao.queryShopList(shopCondition,0,3);
        System.out.println("店铺总数" + count);
        System.out.println("店铺" + shopList.toString());
    }

    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId = 27 ;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaID  " + shop.getArea().getAreaId());
        System.out.println("areaName  " + shop.getArea().getAreaName());
        System.out.println("categoryId  " + shop.getShopCategory().getShopCategoryId());
        System.out.println("categoryName " + shop.getShopCategory().getShopCategoryName());
    }
    @Test
    @Ignore
    public void testInsertShop(){

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
        shop.setShopName("测试的店铺");
        shop.setShopDesc("Test");
        shop.setShopAddr("新都区");
        shop.setPhone("18228980523");
        shop.setShopImg("test");
        shop.setAdvice("审核中");
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        //System.out.println(shop.getCreateTime());
        int effectNum = shopDao.insertShop(shop) ;
        assertEquals(1,effectNum);
    }
    @Test
    @Ignore
    public void updateShopTest(){

        Shop shop = new Shop() ;
        shop.setShopId(3L);
        shop.setShopDesc("測試更新");
        shop.setShopAddr("测试更新");
        shop.setLastEditTime(new Date());
        int effectNum = shopDao.updateShop(shop);
        assertEquals(1,effectNum);

    }


}

