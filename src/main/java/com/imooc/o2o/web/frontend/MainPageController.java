package com.imooc.o2o.web.frontend;
/**
 * Created by Administrator on 2018/12/16.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className MainPageController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/16 21:27
 **/
@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    /**
     * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表以及头条列表
     *
     * @return modelMap
     */
    @ResponseBody
    @RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
    private Map<String, Object> listMainPageInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            //获取一级店铺类别列表
            List<ShopCategory> shopCategory = shopCategoryService.getShopCategory(null);
            modelMap.put("shopCategoryList",shopCategory);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        try {
            //获取头条
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.getHeadLineList(headLine);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}
