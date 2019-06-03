package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg(){
        ProductImg productImg = new ProductImg() ;
        productImg.setProductId(2L);
        productImg.setImgDesc("描述图一");
        productImg.setCreateTime(new Date());
        productImg.setPriority(100);
        productImg.setImgAddr("F:/IDEA/image/chiji2.png");

        ProductImg productImg2 = new ProductImg() ;
        productImg2.setProductId(2L);
        productImg2.setImgDesc("描述图二");
        productImg2.setCreateTime(new Date());
        productImg2.setPriority(200);
        productImg2.setImgAddr("F:/IDEA/image/chiji1.png");
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg);
        productImgList.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        System.out.println("插入了"+ effectNum + "行数据");

    }
    @Test
    public void testBQueryProductImgList(){
        long productId = 10L;
        List<ProductImg> produictImgList = productImgDao.queryProductByProductId(productId);
        System.out.println("商品详情图数量"+produictImgList.size());
    }
    @Test
    public void testCDeleteProductImgList(){
        //检查product为27De商品是否仅仅有两张商品详情图
        long productId = 2L ;
        long effectNum = productImgDao.deleteProductImgByProductId(productId);
        System.out.println("受影响的行数(删除)："+effectNum);
    }

}
