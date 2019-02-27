package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/16.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.HeadLine;

import java.util.List;

/**
 *
 *@className HeadLineService
 *@description TODO
 *@autor Administrator
 *@date 2018/12/16 21:23
 **/
public interface HeadLineService {
    String HLLISTKEY = "headlinelist";


    List<HeadLine>  getHeadLineList(HeadLine headLineCondition);
}
