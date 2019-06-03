package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;


    @Test
    public void testAddProduct() throws Exception{
        //测试addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(9L);
        Shop shop = new Shop();
        shop.setShopId(27L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("高级烧仙草");
        product.setProductDesc("高级烧仙草");
        product.setNormalPrice("20");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        //创建缩略图输入
        File thumbnailFile = new File("F:/IDEA/image/ptc1.png");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(),inputStream);
        //创建两个商品详情图片
        File thumbnailFile1 = new File("F:/IDEA/image/timg5.jpg");
        InputStream inputStream1 = new FileInputStream(thumbnailFile1);
        ImageHolder imageHolder1 = new ImageHolder(thumbnailFile1.getName(),inputStream1);
        File thumbnailFile2 = new File("F:/IDEA/image/timg6.jpg");
        InputStream inputStream2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(),inputStream2);
        List<ImageHolder> productImgs = new ArrayList<ImageHolder>() ;
        productImgs.add(imageHolder1);
        productImgs.add(imageHolder2);

        ProductExecution effect = productService.addProduct(product,imageHolder,productImgs);
        System.out.println(effect);
    }

    @Test
    public void testModifyProduct() throws ProductOperationException, FileNotFoundException {
        //创建shopid为27的product实例
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(9L);
        Shop shop = new Shop();
        shop.setShopId(27L);

        product.setProductId(6L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式的商品1");
        product.setProductDesc("正式的商品1");
        product.setNormalPrice("20");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        //创建缩略图输入
        File thumbnailFile = new File("F:/IDEA/image/ptc1.png");
        InputStream inputStream = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(),inputStream);
        //创建两个商品详情图片
        File thumbnailFile1 = new File("F:/IDEA/image/timg5.jpg");
        InputStream inputStream1 = new FileInputStream(thumbnailFile1);
        ImageHolder imageHolder1 = new ImageHolder(thumbnailFile1.getName(),inputStream1);
        File thumbnailFile2 = new File("F:/IDEA/image/timg6.jpg");
        InputStream inputStream2 = new FileInputStream(thumbnailFile2);
        ImageHolder imageHolder2 = new ImageHolder(thumbnailFile2.getName(),inputStream2);
        List<ImageHolder> productImgs = new ArrayList<ImageHolder>() ;
        productImgs.add(imageHolder1);
        //productImgs.add(imageHolder2);
        ProductExecution pe = productService.modifyProduct(product,imageHolder,productImgs);
        System.out.println(pe.getState());
    }
}
