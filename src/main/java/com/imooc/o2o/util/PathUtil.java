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

    /**
     * 根据不同操作系统获取不同的文件存放路径
     * 在linux下结对路径为部分为/home/mypro/tomcat/apache-2-tomcat-8.0.53/webapps
     * @return
     */
    public static String getImgBasePath(){
        String os =System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){
            //basePath="C:/Users/Administrator/Desktop/pictures";
            basePath="F:/IDEA/image";
        }else {
            basePath="/home/mypro/tomcat/apache-2-tomcat-8.0.53/webapps";
        }
        basePath=basePath.replace("/",seperator);
        return basePath;
    }

    /**
     * 根据shopId生成不同商铺图片存放的专属文件夹的相对路径
     * @param shopId
     * @return
     */
    public static String getShopImgPath(Long shopId){
        String imgPath="/upload/shop/shop"+shopId+"/";
        return imgPath.replace("/",seperator);
    }

    /**
     * 根据userId生成不同的用户图片存放专属文件夹的相对路径
     *
     * @param userId
     * @return
     */
    public static String getUserImgPath(Long userId) {
        String userPath = "/upload/user/user" + userId + "/";
        return userPath.replace("/", seperator);
    }
}
