package com.imooc.o2o.cache;
/**
 * Created by Administrator on 2018/12/19.
 *
 * @author Administrator
 */

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *强指定redis的JedisPool接口构造函数，这样才能在centos成功构建jedisPool
 *@className JedisPoolWriper
 *@description TODO
 *@autor Administrator
 *@date 2018/12/19 15:32
 **/
public class JedisPoolWriper {
    /**
     * Redis连接池对象
     */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig,final String host,final int port) {
        try {
             jedisPool = new JedisPool(jedisPoolConfig, host, port);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 获取Redis连接池对象
     * @return
     */
    public JedisPool getJedisPool(){
        return jedisPool;
    }

    /**
     * 注入Redis连接池对象
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
