package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.LocalAuthDao;
import com.imooc.o2o.dao.PersonInfoDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.ImageCategoryEnum;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.MD5;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;
	/*@Autowired
	private PersonInfoDao personInfoDao;*/

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName,
                                                  String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

	/*
	@Override
	@Transactional
	public LocalAuthExecution register(LocalAuth localAuth,
									   CommonsMultipartFile profileImg) throws RuntimeException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUserName() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			if (localAuth.getPersonInfo() != null
					&& localAuth.getPersonInfo().getUserId() == null) {
				if (profileImg != null) {
					localAuth.getPersonInfo().setCreateTime(new Date());
					localAuth.getPersonInfo().setLastEditTime(new Date());
					localAuth.getPersonInfo().setEnableStatus(1);
					try {
						addProfileImg(localAuth, profileImg);
					} catch (Exception e) {
						throw new RuntimeException("addUserProfileImg error: "
								+ e.getMessage());
					}
				}
				try {
					PersonInfo personInfo = localAuth.getPersonInfo();
					int effectedNum = personInfoDao
							.insertPersonInfo(personInfo);
					localAuth.setUserId(personInfo.getUserId());
					if (effectedNum <= 0) {
						throw new RuntimeException("添加用户信息失败");
					}
				} catch (Exception e) {
					throw new RuntimeException("insertPersonInfo error: "
							+ e.getMessage());
				}
			}
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号创建失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}
	*/

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException {
        //控制判断
        if (localAuth == null || localAuth.getPassword() == null
                || localAuth.getUserName() == null
                || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        //查询是否绑定过平台账号
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth
                .getPersonInfo().getUserId());
        if (tempAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            //如果没有绑定过则创建
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号绑定失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
                        localAuth);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertLocalAuth error: "
                    + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                              String password, String newPassword) {
        //空值校验，并且新旧密码不能相同
        if (userId != null && userName != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                //更新密码，将密码用md5加密
                int effectedNum = localAuthDao.updateLocalAuth(userId,
                        userName, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution insertNewUser(LocalAuth localAuth, ImageHolder imageHolder) {

        //检测用户名是否冲突
        Integer i = localAuthDao.queryLocalByUsername(localAuth.getUserName());
        if (i == null) {
            //填充参数
            PersonInfo personInfo = localAuth.getPersonInfo();
            personInfo.setCreateTime(new Date());
            personInfo.setLastEditTime(new Date());
            personInfo.setEnableStatus(1);
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectNum1 = 0;
            int effectNum2 = 0;
            try {
                //将基本信息存入数据库
                effectNum1 = personInfoDao.insertPersonInfo(personInfo);
                effectNum2 = localAuthDao.insertLocalAuth(localAuth);
                if (effectNum1 <= 0 || effectNum2 <= 0) {
                    return new LocalAuthExecution(LocalAuthStateEnum.CREATE_FAILURE);
                } else {
                    //保存用户头像
                    addUserImg(personInfo, imageHolder);
                    int i1 = personInfoDao.updatePersonInfo(personInfo);
                    if (i1 <= 0) {
                        ImageUtil.deleteFileOrPath(personInfo.getProfileImg());
                        throw new RuntimeException("更新图片地址失败");
                    }

                    return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
                }

            } catch (RuntimeException e) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                //返回错误信息
                return new LocalAuthExecution(LocalAuthStateEnum.CREATE_FAILURE);
            }

        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.USERNAME_REP);
        }
    }

    private void addUserImg(PersonInfo personInfo, ImageHolder imageHolder) {
        String userImgPath = PathUtil.getUserImgPath(personInfo.getUserId());
        userImgPath = ImageUtil.generateImage(imageHolder, userImgPath, ImageCategoryEnum.USER_IMG);
        personInfo.setProfileImg(userImgPath);
    }


}
