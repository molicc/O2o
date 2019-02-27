package com.imooc.o2o.util;
/**
 * Created by Administrator on 2018/12/9.
 *
 * @author Administrator
 */

import javax.servlet.http.HttpServletRequest;

/**
 * @className HttpServletRequestUtil
 * @description TODO
 * @autor Administrator
 * @date 2018/12/9 19:28
 **/
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key) {
        try {
            //将key从String转码为Integer
            return Integer.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1L;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1.0D;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
