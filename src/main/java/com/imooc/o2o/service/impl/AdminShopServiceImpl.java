package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AdminShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.AdminShopService;
import com.imooc.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class AdminShopServiceImpl implements AdminShopService {
    @Autowired
    private AdminShopDao adminShopDao ;

    /**
     * create by ft on 2019-03-22
     * 根据商店的状态返回不同的商店列表
     * @param status
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getStatusShopList(long status, int pageIndex, int pageSize) {
        //根据pageSize计算出rowIndex
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //采用数组形式存储列表，因为主要是做查询操作，而不作修改
        List<Shop> statusShopList = new ArrayList<Shop>() ;
        statusShopList = adminShopDao.queryShopListByState(status,rowIndex,pageSize);
        //获取店铺总数
        int count = adminShopDao.queryCountByState(status);
        ShopExecution shopExecution = new ShopExecution() ;
        if(statusShopList != null){
            //不为空，则取出了数据，返回给controller
            shopExecution.setShopList(statusShopList);
            shopExecution.setCount(count);
            shopExecution.setState(ShopStateEnum.SUCCESS.getState());
        }else {
            shopExecution.setState(ShopStateEnum.NULL_SHOP.getState()
            );//数据为空，则返回空
        }
        return shopExecution;
    }
}
