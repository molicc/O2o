package com.imooc.o2o.web.superadmin;
/**
 * Created by Administrator on 2018/12/7.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.AreaExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.sun.xml.internal.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @className AreaController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/7 19:49
 **/
@Controller
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    /**
     * 获取区域信息列表
     * @return
     */
    @RequestMapping(value = "/listarea", method = GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        logger.info("-------start-------");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelmap = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelmap.put("arealist", list);
            modelmap.put("count", list.size());
            modelmap.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            modelmap.put("success", false);
            modelmap.put("errMsg", e.toString());
        }
        long endTime = System.currentTimeMillis();
        logger.error("test error!");
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("-------end-------");

        return modelmap;
    }

    @RequestMapping(value = "/modifyareabyid",method = GET)
    @ResponseBody
    private Map<String,Object> modifyAreaById(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //获取到区域信息，有需要修改的areaId，priority，areaName，有就修改，没有就不做修改
        String areaName = HttpServletRequestUtil.getString(request,"areaName");
        int priority = Integer.parseInt(request.getParameter("priority"));
        int areaId = Integer.valueOf(request.getParameter("areaId"));
        Area area = null;
        //araId不为空，则获取areaId
        if(areaId>0){
            area.setAreaId(areaId);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","areaId为空");
            return modelMap ;
        }
        //areaName不为空则获取
        if(areaName!=null){ area.setAreaName(areaName); }
        //priority不为空，则获取
        if(priority>0){ area.setPriority(priority); }
        //为area附上最后修改时间
        area.setLastEditTime(new Date());
        //调用server处理
        AreaExecution areaExecution = areaService.modifyArea(area);
        if(areaExecution.getState()==1){
            //状态码为1，则处理成功
            modelMap.put("success",true);
            modelMap.put("area",areaExecution.getArea());
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg",areaExecution.getStateInfo());
        }
        return modelMap ;
    }

    @RequestMapping(value = "/addarea",method = POST)
    @ResponseBody
    public Map<String,Object> addArea(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //获取前端的发送的数据
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            modelMap.put("success",false);
            return modelMap;
        }
        String areaName = request.getParameter("areaName");
        String  priority = request.getParameter("priority");
        if(areaName!=null&&areaName!=""){
            Area area = new Area();
            //整合实体数据
            area.setAreaName(areaName);
            if(priority!=null&&priority!="") {
                area.setPriority(Integer.parseInt(priority));
            }else {
                //默认值为0
                area.setPriority(0);
            }
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            //调用service处理
            AreaExecution areaExecution = areaService.addArea(area);
            if(areaExecution.getState()== AreaStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",areaExecution.getStateInfo());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入区域名称或优先级");
        }
        //System.out.println(areaName+priority);
        return modelMap ;
    }
}
