package com.imooc.o2o.dao;/**
 * Created by Administrator on 2018/12/6.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Area;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 修改区域名称
     * @param area
     * @return
     */
    int modifyArea(@Param("area")Area area);

    /**
     * 插入数据
     * @param area
     * @return
     */
    int insertArea(@Param("area")Area area);

    Area queryAreaById(@Param("areaId")long areaId);
}
