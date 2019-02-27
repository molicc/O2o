package com.imooc.o2o.service;
/**
 * Created by Administrator on 2018/12/20.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @className LocalAuthService
 * @description TODO
 * @autor Administrator
 * @date 2018/12/20 13:57
 **/
public interface LocalAuthService {
    /**
     * 通过账号密码获取用户信息
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 根据userid获取用户信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
  /*  LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws RuntimeException;
*/
    /**
     * 绑定微信，生成平台专属账号
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     * 修改平台账号的登录密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);

    /**
     * 添加新用户
     * @param localAuth
     * @return
     */
    LocalAuthExecution insertNewUser(LocalAuth localAuth);

}
