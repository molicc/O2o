package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.AdminShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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


    @ResponseBody
    @RequestMapping(value = "/getshoplistignorestatus",method = RequestMethod.GET)
    private Map<String,Object> getShopListIgnoreStatus(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>() ;
        //封装了shop和shopCount
        ShopExecution shopExecution = null;
        //List<Shop> shopStatusList = new ArrayList<Shop>() ;
        //获取分页参数,
        int pageIndex = Integer.parseInt(HttpServletRequestUtil.getString(request,"pageIndex")) ;
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        try {
             shopExecution = adminShopService.getStatusShopList(pageIndex,pageSize);
        }catch (Exception e){
             modelMap.put("success",false);
             modelMap.put("errMsg",e.getMessage());
        }
        //如果店铺不为空，则返回
        if(shopExecution != null){
            modelMap.put("shopExecution",shopExecution);
            modelMap.put("success",true);
        }
        return modelMap ;
    }

    @RequestMapping(value = "/modifyshopstate",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> modifyShopState(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String,Object>();
        long shopId = Long.parseLong(request.getParameter("shopId"));
        int enableStatus = Integer.parseInt(request.getParameter("enableStatus"));
        boolean result = false ;
        try {
            result = adminShopService.modifyShopState(enableStatus,shopId);
        }catch (Exception e){
            modelMap.put("success",true);
            modelMap.put("errMsg",e.getMessage());
        }
        if(result==true){
            modelMap.put("success",true);
        }else {
            modelMap.put("success",true);
            modelMap.put("errMsg","提交失败");
        }
        return modelMap ;
    }


}
