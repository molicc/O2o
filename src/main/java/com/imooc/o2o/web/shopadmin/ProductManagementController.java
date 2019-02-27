package com.imooc.o2o.web.shopadmin;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className ProductManagementController
 * @description TODO
 * @autor Administrator
 * @date 2018/12/14 18:16
 **/
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @ResponseBody
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        //接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");

        MultipartHttpServletRequest multipartHttpServletRequest;
        //缩略图
        ImageHolder imageHolder = null;
        //详情图
        ArrayList<ImageHolder> imageHolders = new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        try {

            if (commonsMultipartResolver.isMultipart(request)) {
                imageHolder=handleImage((MultipartHttpServletRequest) request, imageHolder, imageHolders);

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }


        try {
            //转化实例
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product != null && imageHolder != null && imageHolders.size() > 0) {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            Shop shop = new Shop();
            shop.setShopId(currentShop.getShopId());
            product.setShop(shop);

            ProductExecution productExecution = productService.addProduct(product, imageHolder, imageHolders);

            if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productExecution.getStateInfo());
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList =
                    productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("success", true);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("product", product);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value = "modifyproduct", method = RequestMethod.POST)
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        ImageHolder imageHolder = null;
        List<ImageHolder> imageHolders = new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                imageHolder = handleImage((MultipartHttpServletRequest) request, imageHolder, imageHolders);

            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution productExecution = productService.modifyProduct(product, imageHolder, imageHolders);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    private Map<String,Object> getProductByShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (pageIndex>-1&&pageSize>-1&&currentShop!=null&&currentShop.getShopId()!=null){

            Long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product product = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);

            ProductExecution pe = productService.getProductList(product, pageIndex, pageSize);
            modelMap.put("success",true);
            modelMap.put("count",pe.getCount());
            modelMap.put("productList",pe.getProductList());

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition(Long shopId, Long productCategoryId, String productName) {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        product.setShop(shop);
        if (productCategoryId!=-1){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }

        if (productName!=null){
            product.setProductName(productName);
        }

        return product;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request, ImageHolder imageHolder,
                                    List<ImageHolder> imageHolders) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = request;
        //取出缩略图
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            imageHolder = new ImageHolder(thumbnailFile.getOriginalFilename(),
                    thumbnailFile.getInputStream());
        }

        //取出详情图列表，并构建List<ImageHolder>，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile(
                    "productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                imageHolders.add(productImg);
            } else {
                break;
            }
        }
        return imageHolder;
    }



}
