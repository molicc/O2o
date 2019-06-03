package com.imooc.o2o.service;

import com.imooc.o2o.entity.PersonInfo;

import java.util.List;

public interface PersonInfoService {

    List<PersonInfo> getPersonList();

    Boolean updatePersonState(PersonInfo personInfo);
}
