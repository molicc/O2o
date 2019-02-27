package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/19.
 *
 * @author Administrator
 */

/**
 *
 *@className CacheService
 *@description TODO
 *@autor Administrator
 *@date 2018/12/19 20:11
 **/
public interface CacheService {

    /**
     * 依据key前缀删除匹配该模式下的所有key-value如传入shopcategory则以此开头的key_value都会被清空
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
