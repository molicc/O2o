package com.imooc.o2o.interceptor.shopadmin;
/**
 * Created by Administrator on 2018/12/22.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *店家管理系统登录验证拦截器
 *@className ShopLoginInterceptor
 *@description TODO
 *@autor Administrator
 *@date 2018/12/22 15:38
 **/
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    public ShopLoginInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj!=null){
            PersonInfo user =(PersonInfo) userObj;
            if (user!=null&&user.getUserId()!=null&&user.getUserId()>0&&user.getEnableStatus()==1){
                return true;
            }
        }
        // 校验失败跳转到登录页面
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<script>");
        writer.println("window.open('"+request.getContextPath()+"/local/login?usertype=2','_self')");
        writer.println("</script>");
        writer.println("</html>");
        return false;
    }
}
