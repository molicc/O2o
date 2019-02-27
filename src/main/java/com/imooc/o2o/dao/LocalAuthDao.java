package com.imooc.o2o.dao;
/**
 * Created by Administrator on 2018/12/19.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @className LocalAuthDao
 * @description TODO
 * @autor Administrator
 * @date 2018/12/19 22:33
 **/
public interface LocalAuthDao {

    /**
     * 通过账号密码查询对应的信息，登录使用
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String username, @Param("password") String password);

    /**
     * 通过用户id查询对应的localauth
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    Integer queryLocalByUsername(@Param("username")String username);

    /**
     * 添加平台账号
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**通过userid，username，password更改密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId,
                        @Param("userName") String userName,
                        @Param("password") String password,
                        @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}
