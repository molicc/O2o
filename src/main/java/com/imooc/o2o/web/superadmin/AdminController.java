package com.imooc.o2o.web.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端请求转发
 */
@Controller
@RequestMapping(value = "/superadmin",method = RequestMethod.GET)
public class AdminController {
    /**
     * 转发到管理员首页
     * @return
     */
    @RequestMapping(value = "/topadmin")
    private String topAdmin(){ return "/superadmin/topadmin"; }
    /**
     * 转发到店铺审核详情页
     * @return
     */
    @RequestMapping(value = "/allshoplist")
    private String statusShopList(){ return "/superadmin/allshoplist";}
}
