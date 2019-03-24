package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.AdminShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据分页参数获取店铺列表
 * 0:审核中
 * 1:审核通过
 * -1:审核不通过
 */
@Controller
@RequestMapping("/superadmin")
public class AdminShopListController {
    @Autowired
    private AdminShopService adminShopService ;

    @RequestMapping(value = "/shoplistbystatus",method = RequestMethod.GET)
    private Map<String,Object> getAdminShopListByStatus(){
        Map<String,Object> modelMap = new HashMap<String,Object>() ;
        List<Shop> shopStatusList = new ArrayList<Shop>() ;
        //todo:优先开发前端？还是先开发Controller
        return modelMap ;
    }

}
