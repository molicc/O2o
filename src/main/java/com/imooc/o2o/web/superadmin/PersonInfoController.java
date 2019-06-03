package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.service.PersonInfoService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/superadmin")
public class PersonInfoController {
    @Autowired
    PersonInfoService personInfoService ;

    /**
     * 获取账户信息，商家和用户信息，用于审核
     * @return
     */
    @RequestMapping(value = "/querypersoninfolist",method = GET)
    @ResponseBody
    public Map<String,Object> queryPersonInfoList(){
        List<PersonInfo> mapList ;
        Map<String,Object> modelMap = new HashMap<String,Object>();

        mapList = personInfoService.getPersonList();
        if(mapList == null){
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败!");
        }else {
            modelMap.put("personList",mapList);
            modelMap.put("success", true);
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifystate",method = GET)
    @ResponseBody
    public Map<String,Object> modifyState(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //获取用户信息，封装成一个实体
        String userId = request.getParameter("userId");
        String state = request.getParameter("enableStatus");
        if(userId==null||userId.equals("")||state==null||state.equals("")){
            modelMap.put("success", false);
            modelMap.put("errMsg", "请求参数错误!");
            return modelMap;
        }
        PersonInfo personInfo = new PersonInfo() ;
        personInfo.setUserId(Long.parseLong(userId));
        personInfo.setEnableStatus(Integer.parseInt(state));
        Boolean flag = personInfoService.updatePersonState(personInfo);
        if(flag == false){
            modelMap.put("success", false);
            modelMap.put("errMsg", "审核失败!");
        }else {
            modelMap.put("success", true);
            modelMap.put("errMsg", "审核成功!");
        }
        return modelMap;
    }
}
