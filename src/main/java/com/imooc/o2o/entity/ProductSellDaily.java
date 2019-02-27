package com.imooc.o2o.entity;
/**
 * Created by Administrator on 2018/12/22.
 *
 * @author Administrator
 */

import java.util.Date;

/**
 *
 *@className ProductSellDaily
 *@description TODO
 *@autor Administrator
 *@date 2018/12/22 21:59
 **/
public class ProductSellDaily {
    private Date createTime;
    private Integer total;
    private Product product;
    private Shop shop;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
