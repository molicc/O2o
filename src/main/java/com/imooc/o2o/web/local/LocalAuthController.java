package com.imooc.o2o.web.local;
/**
 * Created by Administrator on 2018/12/20.
 *
 * @author Administrator
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.LocalAuthExecution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.enums.LocalAuthStateEnum;
import com.imooc.o2o.service.LocalAuthService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @className LocalAuthController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/20 15:03
 **/
@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息和平台账号绑定
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);

            LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
            if (localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", localAuthExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");

        }
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null) {
            try {
                LocalAuth localAuthByUserId = localAuthService.getLocalAuthByUserId(user.getUserId());
                //查看原先账号，看看与输入的账号是否一致，不一致则是非法操作
                if (localAuthByUserId == null || !localAuthByUserId.getUserName().equals(userName)) {
                    //不一致则直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的账号非本次登录的账号");
                    return modelMap;
                }
                //修改平台账号的通用密码
                LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(user.getUserId(), userName,
                        password, newPassword);
                if (localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", localAuthExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入账号或密码");
        }
        return modelMap;


    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    private Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        if (userName != null && password != null) {
            LocalAuth localAuthByUserNameAndPwd = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuthByUserNameAndPwd != null&&localAuthByUserNameAndPwd.getPersonInfo().getEnableStatus()==1) {
                modelMap.put("success", true);
                modelMap.put("userType", localAuthByUserNameAndPwd.getPersonInfo().getUserType());
                request.getSession().setAttribute("user", localAuthByUserNameAndPwd.getPersonInfo());
            } else if(localAuthByUserNameAndPwd != null&&localAuthByUserNameAndPwd.getPersonInfo().getEnableStatus()==0){
                modelMap.put("success", false);
                modelMap.put("errMsg", "该用户禁止使用本系统");
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码不能为空");
        }
        return modelMap;
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 注册用户
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registerNew", method = RequestMethod.POST)
    private Map<String, Object> register(HttpServletRequest request) {
        HashMap<String, Object> modelMap = new HashMap<>();
        //1、验证码校验
        boolean checkVerifyCode = CodeUtil.checkVerifyCode(request);
        if (!checkVerifyCode) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        //2、获取localAuth对象
        String localStr = HttpServletRequestUtil.getString(request, "localAuth");
        ObjectMapper objectMapper = new ObjectMapper();
        LocalAuth localAuth;
        try {
            localAuth = objectMapper.readValue(localStr, LocalAuth.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //3、获取用户头像
        CommonsMultipartFile userImg = null;
        ImageHolder imageHolder;
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            userImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("userImg");
        } else {

        }
        try {
            imageHolder = new ImageHolder(userImg.getOriginalFilename(), userImg.getInputStream());
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //处理业务
        LocalAuthExecution localAuthExecution = localAuthService.insertNewUser(localAuth, imageHolder);
        if (localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", localAuthExecution.getStateInfo());
        }
        return modelMap;
    }
}
