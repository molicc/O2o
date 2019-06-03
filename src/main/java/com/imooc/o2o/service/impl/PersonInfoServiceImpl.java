package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    PersonInfoDao personInfoDao ;
    @Override
    public List<PersonInfo> getPersonList() {
        List<PersonInfo> personInfoList = null ;
        try {
            personInfoList = personInfoDao.queryPersonInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return personInfoList;
    }

    @Override
    public Boolean updatePersonState(PersonInfo personInfo) {
        if(personInfo != null){
            try {
                int effectNum = personInfoDao.updatePersonInfo(personInfo);
                if(effectNum==0){
                    return false ;
                }else {
                    return true ;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false ;
    }
}
