package com.imooc.o2o.dao;


import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 测试dao
 */
public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList =areaDao.queryArea();
        assertEquals(4,areaList.size());
    }

    @Test
    public void testModifyArea(){
        Area area = new Area();
        area.setAreaId(4);
        area.setAreaName("测试地址");
        area.setPriority(120);
        area.setLastEditTime(new Date());
        Assert.assertEquals(1,areaDao.modifyArea(area));
    }

    @Test
    @Ignore
    public void testInsertArea(){
        Area area = new Area();
        area.setAreaName("新增");
        area.setPriority(30);
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());

        Assert.assertEquals(1,areaDao.insertArea(area));

    }
    @Test
    public void testQueryAreaById(){
        Assert.assertEquals(5,(int)areaDao.queryAreaById(5).getAreaId());
    }
}

