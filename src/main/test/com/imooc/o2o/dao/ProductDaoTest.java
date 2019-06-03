package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao ;
    //插入一个产品
    @Test
    @Ignore
    public void testAInsertProduct(){
        ProductCategory productCategory =
                new ProductCategory();
        productCategory.setProductCategoryId(2L);
        Shop shop = new Shop();
        shop.setShopId(27L);
        Product product = new Product();
        product.setProductName("测试回环");
        product.setProductDesc("多种食材混合而成");
        product.setNormalPrice("10");
        product.setEnableStatus(1);
        product.setCreateTime(new Date());
        product.setPriority(100);
        product.setProductCategory(productCategory);
        product.setShop(shop);

        int effectUnm = productDao.insertProduct(product);
        System.out.println("插入操作影响的行数"+effectUnm);
    }
    //查询产品信息
    @Test
    public void testBQueryProductById() throws Exception{
        long productId= 6L ;
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片一");
        productImg1.setImgDesc("测试图片一");
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        productImg1.setPriority(100);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片二");
        productImg2.setImgDesc("测试图片二");
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        productImg2.setPriority(200);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectNUm = productImgDao.batchInsertProductImg(productImgList);
        System.out.println("插入"+effectNUm+"张详情图");
        Product product = productDao.queryProductById(productId);
        System.out.println("商品详情图数量："+product.getProductImgList().size());
        effectNUm = productImgDao.deleteProductImgByProductId(productId);
        System.out.println("删除详情图行数："+effectNUm);
    }
    @Test
    public void testqueryProductList(){
        Product productCondition = new Product();
        //分页查询，预期返回结果
        List<Product> productList = productDao.queryProductList(productCondition,0,6);
        assertEquals(6,productList.size());
        int count = productDao.queryProductCount(productCondition);
        System.out.println("商品列表总数"+count);
        //模糊查询
        productCondition.setProductName("a");
        productList = productDao.queryProductList(productCondition,0,3);
        assertEquals(3,productList.size());
        count = productDao.queryProductCount(productCondition);
        System.out.println("模糊查询商品列表总数"+count);

    }

    @Test
    public void testCUpdateProduct(){
        Product product = new Product();
        ProductCategory productCategory =new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(27L);
        productCategory.setProductCategoryId(11L);
        product.setProductId(13L);
        product.setShop(shop);
        product.setProductName("第一个产品aa");
        product.setLastEditTime(new Date());
        product.setEnableStatus(0);
        product.setProductCategory(productCategory);
        int effectNum = productDao.updateProduct(product);
        System.out.println("修改的行数"+effectNum);
    }
}
