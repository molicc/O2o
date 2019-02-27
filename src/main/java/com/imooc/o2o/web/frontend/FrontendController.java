package com.imooc.o2o.web.frontend;
/**
 * Created by Administrator on 2018/12/17.
 *
 * @author Administrator
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @className FrontendController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/17 20:53
 **/
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String showShopList() {
        return "frontend/shoplist";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "/frontend/shopdetail";
    }

    @RequestMapping(value = "productdetail",method = RequestMethod.GET)
    private String productDetail(){
        return"/frontend/productdetail";
    }

}
