package com.imooc.o2o.service.impl;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ImageCategoryEnum;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className ProductServiceImpl
 * @description TODO
 * @autor Administrator
 * @date 2018/12/14 14:36
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     *1、处理缩略图，获取缩略图相对路径并赋值给product
     * 2、往tb_product写入商品信息，获取productid
     * 3、结合productId批量处理商品详情图
     * 4、将商品详情图列表批量插入tb_product_img中
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail!=null){
                addThumbnail(product,thumbnail);
            }
            try {
                //创建商品信息
                int effectNum = productDao.insertProduct(product);
                if (effectNum<=0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败:"+e.getMessage());
            }
            //若商品详情图不为空则添加
            if (productImgHolderList !=null&& productImgHolderList.size()>0){
                addProductImgHolerList(product, productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId );

    }

    /**
     * 1、若缩略图参数有值，则处理缩略图
     * 2、若原先存在缩略图先删除再添加新图，之后获取缩略图相对路径并赋值给product
     * 3、若商品详情图列表参数有值，对商品详情图列表进行同样操作
     * 4、将tb_product_img下面的该商品原先的商品详情图记录清除
     * 5、更新tb_product信息
     * @param product
     * @param thumbnail
     * @param productImg
     * @return
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImg) {
        if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setLastEditTime(new Date());
            if (thumbnail!=null){
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }

            if (productImg!=null&&productImg.size()>0){
                deleteProductImg(product.getProductId());
                addProductImgHolerList(product,productImg);
            }

            try {
                int effectNum = productDao.updateProduct(product);
                if (effectNum<=0){
                    throw new ProductOperationException("更新商品失败");
                }
                return  new ProductExecution(ProductStateEnum.SUCCESS);
            }catch (Exception e){
                throw new ProductOperationException("更新商品失败"+e.getMessage());
            }

        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> products = productDao.queryProductList(productCondition, rowIndex, pageSize);
        //todo :找到这个代码的异常之处
        int count = 10 ;
        try{
         count = productDao.queryProductCount(productCondition);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductList(products);
        productExecution.setCount(count);
        return productExecution;
    }

    private void addProductImgHolerList(Product product, List<ImageHolder> productImgHolderList) {

        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgs = new ArrayList<>();

        for (ImageHolder imageHolder:productImgHolderList
             ) {
            String imgAddr = ImageUtil.generateImage(imageHolder, dest, ImageCategoryEnum.NORMAL_IMG);

            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }

        if (productImgs.size()>0){
            try {

                int effectNum = productImgDao.batchInsertProductImg(productImgs);
                if (effectNum<=0){
                    throw  new ProductOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败:"+e.getMessage());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateImage(thumbnail, dest, ImageCategoryEnum.THUMBNAIL_IMG);
        product.setImgAddr(thumbnailAddr);
    }

    private void deleteProductImg(long productId){
        List<ProductImg> productImgList = productImgDao.queryProductByProductId(productId);
        for (ProductImg img:productImgList
             ) {
            ImageUtil.deleteFileOrPath(img.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }
}
