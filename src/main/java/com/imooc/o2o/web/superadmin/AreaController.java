package com.imooc.o2o.web.superadmin;
/**
 * Created by Administrator on 2018/12/7.
 *
 * @author Administrator
 */

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        logger.info("-------start-------");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelmap = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            modelmap.put("rows", list);
            modelmap.put("total", list.size());
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

}
