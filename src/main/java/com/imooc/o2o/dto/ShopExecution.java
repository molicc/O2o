package com.imooc.o2o.dto;
/**
 * Created by Administrator on 2018/12/9.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 *
 *@className ShopExecution
 *@description TODO
 *@autor Administrator
 *@date 2018/12/9 14:34
 **/
public class ShopExecution {
    /**
     * 结果状态
     */
    private int state;
    /**
     * 状态标识
     */
    private String stateInfo;
    /**
     * 店铺数量
     */
    private int count;
    /**
     * 操作的shop
     */
    private Shop shop;
    /**
     * shop列表
     */
    private List<Shop> shopList;

    public ShopExecution() {
    }

    /**
     * 店铺操作失败时，使用的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功时，使用的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
        this.shop = shop;
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }
    /**
     * 店铺操作成功时，使用的构造器
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
        this.shopList = shopList;
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
