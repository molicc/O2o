package com.imooc.o2o.web.wechat;

import com.imooc.o2o.util.wechat.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        //todo :去除输出信息提示
        System.out.println("微信 get......");
        //微信加密签名。signature结合了开发者写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter("signature");
        //获取时间戳
        String timestamp = request.getParameter("timestamp");
        //获取随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        //通过检验signature对请求的校验，成功则原样返回echostr，表示成功介入，否则返回false
        PrintWriter out = null ;
        try {
            out = response.getWriter();
            if(SignUtil.checkSignature(signature,timestamp,nonce)){
                System.out.println("微信get success。。。");
                out.print(echostr);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
        }
    }
}
