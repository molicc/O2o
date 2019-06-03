package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    /**
     * 返回商品详情
     * @return
     */
    @ResponseBody
    @RequestMapping("/listproductdetailpageinfo")
    public Map<String,Object> listProductDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        String productId = HttpServletRequestUtil.getString(request,"productId");
        if(productId!=null&&!productId.equals("")){
            try{
                Product product  = productService.getProductById(Long.parseLong(productId));
                modelMap.put("product",product);
                modelMap.put("success",true);
                modelMap.put("errMsg","操作成功");
            }catch (Exception e){
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","参数为空");
        }

        return modelMap ;
    }
}
