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
import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public AreaExecution queryAreaById(long areaId) {
        //参数是否非法
        if(areaId>0){
            //对查询操作检查异常
            try {
                Area area = areaDao.queryAreaById(areaId);
                if(area != null){
                    return new AreaExecution(AreaStateEnum.SUCCESS,area);
                }
            }catch (Exception e){
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }
        return new AreaExecution(AreaStateEnum.NULL_AREA_ID);
    }

    @Override
    @Transactional
    public AreaExecution modifyArea(Area area) {
        if(area != null){
            //area不为空，则进行插入操作,捕获异常,2、查询出刚才插入的数据，并返回
            try {
                //产生异常，则捕获
                int effectNum = areaDao.modifyArea(area);
                Area areaResult = areaDao.queryAreaById(area.getAreaId());
                //删除redis的key
                jedisUtilKeys.del(AREALISTKEY);
                if(effectNum == 1&&areaResult!=null){
                    //插入正常，返回数据
                    return new AreaExecution(AreaStateEnum.SUCCESS,areaResult);
                }else {
                    //否则返回状态为插入失败的信息
                    return new AreaExecution(AreaStateEnum.INSERT_ERROR);
                }
            }catch (Exception e){
                //异常产生返回系统错误
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }
        //area为空，返回失败的AreaStateEnum构造器
        return new AreaExecution(AreaStateEnum.NULL_AREA);
    }

    @Override
    public AreaExecution addArea(Area area) {
        //判断area信息是否为空
        if(area != null){
            try {
                //执行插入操作
                int effectNum = areaDao.insertArea(area);
                //更新key
                jedisUtilKeys.del(AREALISTKEY);
                if(effectNum == 1){
                    //成功的返回结果
                    return new AreaExecution(AreaStateEnum.SUCCESS,area);
                }else {
                    return new AreaExecution(AreaStateEnum.INSERT_ERROR);
                }
            }catch (Exception e){
                //捕获异常
                return new AreaExecution(AreaStateEnum.INNER_ERROR);
            }
        }
        //area信息为空，则返回相应的错误信息
        return new AreaExecution(AreaStateEnum.NULL_AREA);
    }
}
