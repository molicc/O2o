package com.imooc.o2o.service.impl;
/**
 * Created by Administrator on 2018/12/19.
 *
 * @author Administrator
 */

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 *@className CacheServiceImpl
 *@description TODO
 *@autor Administrator
 *@date 2018/12/19 20:13
 **/
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
        for (String key: keySet){
            jedisKeys.del(key);
        }
    }
}
