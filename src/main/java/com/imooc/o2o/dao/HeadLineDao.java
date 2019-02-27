package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/16.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *@className HeadLineDao
 *@description TODO
 *@autor Administrator
 *@date 2018/12/16 20:57
 **/
public interface HeadLineDao {

    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
