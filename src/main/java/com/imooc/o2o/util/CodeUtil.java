package com.imooc.o2o.util;
/**
 * Created by Administrator on 2018/12/10.
 *
 * @author Administrator
 */

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 *验证码校验
 *@className CodeUtil
 *@description TODO
 *@autor Administrator
 *@date 2018/12/10 22:44
 **/
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if (verifyCodeActual==null||!verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)){
            return false;
        }
        return true;
    }
}
