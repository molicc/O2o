package com.imooc.o2o.dao;/**
 * Created by Administrator on 2018/12/6.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 *
 *@ClassName AreaDao
 *@Description TODO
 *@Autor Administrator
 *@Date 2018/12/6 19:53
 **/
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();

}
