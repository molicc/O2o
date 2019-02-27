package com.imooc.o2o.interceptor.shopadmin;
/**
 * Created by Administrator on 2018/12/22.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 *@className ShopPermissionInterceptor
 *@description TODO
 *@autor Administrator
 *@date 2018/12/22 15:47
 **/
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    /**
     * 操作权限的拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //getshopmanagementinfo
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //registershop
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        if (currentShop!=null&&shopList!=null){
            //遍历可操作的店铺列表
            for (Shop shop:shopList
                 ) {
                //如果当前店铺在可操作的列表则返回true
                if (shop.getShopId().equals(currentShop.getShopId())) {
                    return  true;
                }
            }
        }
        return false;
    }
}
