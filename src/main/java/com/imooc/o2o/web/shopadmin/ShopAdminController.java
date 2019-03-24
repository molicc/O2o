package com.imooc.o2o.web.shopadmin;
/**
 * Created by Administrator on 2018/12/10.
 *
 * @author Administrator
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 访问网页的路由，由于html放在WEB-INF下，无法直接访问，需要路由转发
 *
 * @className ShopAdminController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/10 13:42
 **/
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanage")
    public String productCategoryManage(){
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement")
    public String productManagement(){return "shop/productmanagement";}

}
