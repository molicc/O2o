package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.PersonInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonInfoDaoTest extends BaseTest {
    @Autowired
    PersonInfoDao personInfoDao ;

    @Test
    public void testQueryPersonInfo(){
        Assert.assertEquals(3,personInfoDao.queryPersonInfo().size());
    }
    @Test
    public void testUpdatePersonInfo(){
        PersonInfo personInfo = new PersonInfo() ;
        personInfo.setUserId(Long.parseLong(String.valueOf(1)));
        personInfo.setEnableStatus(Integer.parseInt(String.valueOf(0)));
        Assert.assertEquals(1,personInfoDao.updatePersonInfo(personInfo));
    }
}
