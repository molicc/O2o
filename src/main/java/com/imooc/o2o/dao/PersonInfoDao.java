package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2019/2/27.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.PersonInfo;

import java.util.List;

/**
 *
 *@className PersonInfoDao
 *@description TODO
 *@autor Administrator
 *@date 2019/2/27 17:08
 **/
public interface PersonInfoDao {
      /**
       * 插入个人信息
       * @param personInfo
       * @return
       */
      int insertPersonInfo(PersonInfo personInfo);

      /**
       * 更新个人信息
       * @param personInfo
       * @return
       */
      int updatePersonInfo(PersonInfo personInfo);

      /**
       * 查询所有用户类别为商家和用户的信息
       * @return
       */
      List<PersonInfo> queryPersonInfo();
}
