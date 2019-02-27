package com.imooc.o2o.util;
/**
 * Created by Administrator on 2018/12/8.
 *
 * @author Administrator
 */

/**
 *
 *@className PathUtil
 *@description TODO
 *@autor Administrator
 *@date 2018/12/8 15:41
 **/
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os =System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){
            basePath="C:/Users/Administrator/Desktop/pictures";
        }else {
            basePath="/home/pictures";
        }
        basePath=basePath.replace("/",seperator);
        return basePath;
    }

    public static String getShopImgPath(Long shopId){
        String imgPath="/upload/item/shop"+shopId+"/";
        return imgPath.replace("/",seperator);
    }
}
