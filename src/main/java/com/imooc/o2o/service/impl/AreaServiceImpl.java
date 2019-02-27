package com.imooc.o2o.service.impl;
/**
 * Created by Administrator on 2018/12/7.
 *
 * @author Administrator
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className AreaServiceImpl
 * @description TODO
 * @autor Administrator
 * @date 2018/12/7 19:29
 **/
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisUtilKeys;
    @Autowired
    private JedisUtil.Strings jedisUtilStrings;


    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList() {
        String key = AREALISTKEY;
        String jsonString;
        List<Area> areaList;
        ObjectMapper mapper = new ObjectMapper();


        if (!jedisUtilKeys.exists(key)) {
            //如果Redis中没有该key

            //从数据库查出来
            areaList = areaDao.queryArea();

            try {
                //转换成String
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            //保存至redis
            jedisUtilStrings.set(key, jsonString);
        } else {
            //如果Redis存在相关数据

            //取出对应的value
            jsonString = jedisUtilStrings.get(key);
            //拼接ArrayList<Area>类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                //将string转为对应类型
                areaList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
        }

        //返回
        return areaList;
        // return  areaDao.queryArea();
    }
}
