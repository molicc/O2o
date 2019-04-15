package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/7.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;

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

    AreaExecution queryAreaById(long areaId);
    /**
     * 依据area实体修改区域列表
     * @return
     */
    AreaExecution modifyArea(Area area);

    /**
     * 根据area实体添加区域信息
     * @return AreaExecution
     */
    AreaExecution addArea(Area area) ;
}
