package com.imooc.o2o.web.local;
/**
 * Created by Administrator on 2018/12/20.
 *
 * @author Administrator
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @className LocalController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/20 18:19
 **/
@Controller
@RequestMapping(value = "/local")
public class LocalController {
    /**
     * 绑定账号路由
     * @return
     */
    @RequestMapping(value = "/accountbind",method = RequestMethod.GET)
    private String accountbind() {
        return "/local/accountbind";
    }

    /**
     *登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    private String login(){
        return "local/login";
    }
    /**
     *注册
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    private String register(){
        return "local/register";
    }

    @RequestMapping(value = "/changepsw",method = RequestMethod.GET)
    private String changepwd(){
        return "local/changepsw";
    }
}
