package com.imooc.o2o.service.impl;
/**
 * Created by Administrator on 2018/12/10.
 *
 * @author Administrator
 */

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.exceptions.ShopcategoryOperationException;
import com.imooc.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className ShopCategoryServiceImpl
 * @description TODO
 * @autor Administrator
 * @date 2018/12/10 15:55
 **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;

    private static Logger logger= LoggerFactory.getLogger(ShopCategoryServiceImpl.class);
    @Override
    public List<ShopCategory> getShopCategory(ShopCategory shopCategoryCondition)  {
        String key =SCLISTKEY;
        List<ShopCategory> shopCategoryList;
        ObjectMapper mapper = new ObjectMapper();
        if (shopCategoryCondition ==null){
            //若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
            key=key+"_allfirstlevel";
        }else if (shopCategoryCondition !=null&& shopCategoryCondition.getParent()!=null&& shopCategoryCondition.getParent().getShopCategoryId()!=null){
            //若parentId为菲康，则列出改parentId下所有的子类
            key=key+"_parent"+ shopCategoryCondition.getParent().getShopCategoryId();
        }else if (shopCategoryCondition!=null){
            //列出所有子类别
            key=key+"_allsecondlevel";
        }
        String jsonString;
        if (!jedisUtilKeys.exists(key)){
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            try {
                jsonString=mapper.writeValueAsString(shopCategoryList);
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopcategoryOperationException(e.getMessage());
            }
        }else {
            jsonString=jedisUtilStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                shopCategoryList=mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopcategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
