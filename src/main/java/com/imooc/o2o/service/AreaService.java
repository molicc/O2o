package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/7.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @className AreaService
 * @description TODO
 * @autor Administrator
 * @date 2018/12/7 19:27
 **/
public interface AreaService {
    String AREALISTKEY = "arealist";

    List<Area> getAreaList();
}
