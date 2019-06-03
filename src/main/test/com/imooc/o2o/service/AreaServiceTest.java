package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService ;
    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("南街",areaList.get(1).getAreaName());
    }

    @Test
    public void testQueryAreaById(){
        long areaId = 5 ;
        Assert.assertEquals(1,areaService.queryAreaById(areaId).getState());
    }

    @Test
    public void testModifyArea(){
        Area area = new Area();
        area.setAreaId(4);
        area.setLastEditTime(new Date());
        //area.setAreaName("北街");
        area.setPriority(500);
        Assert.assertEquals(1,areaService.modifyArea(area).getState());
    }

    @Test
    public void testAddArea(){
        Area area = new Area();
        area.setLastEditTime(new Date());
        area.setAreaName("测试");
        area.setPriority(100);
        area.setCreateTime(new Date());
        Assert.assertEquals(1,areaService.addArea(area).getState());
    }
}

