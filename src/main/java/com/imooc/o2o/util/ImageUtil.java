package com.imooc.o2o.util;
/**
 * Created by Administrator on 2018/12/8.
 *
 * @author Administrator
 */

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.enums.ImageCategoryEnum;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @className ImageUtil
 * @description TODO
 * @autor Administrator
 * @date 2018/12/8 15:06
 **/
public class ImageUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM=new Random();

    /**
     * 保存处理后的图片
     * @param imageHolder 原始图片
     * @param targetAddr 目标地址
     * @return
     */
    public static String generateImage(ImageHolder imageHolder, String targetAddr,
                                       ImageCategoryEnum imageCategoryEnum) {
        InputStream image = imageHolder.getImage();
        String fileName =imageHolder.getImageName();
        //随机生成图片名
        String realFileName = getRandomFileName();
        //获取图片格式后缀名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        //生成绝对路径下的各个子级文件目录
        makeDirPath(targetAddr);

        //相对地址+随机文件名+后缀
        String relativeAddr = targetAddr + realFileName + extension;
        //根据图片绝对路径生成图片
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        switch (imageCategoryEnum.getState()) {
            case 0:
                try {
                    //添加水印后的图片对原图片进行覆盖
                    Thumbnails.of(image).size(337, 640).watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\pictures\\waterMark.jpg")),
                            0.25f).outputQuality(0.8f)
                            .toFile(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    Thumbnails.of(image).size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\pictures\\waterMark.jpg")),
                            0.25f).outputQuality(0.9f)
                            .toFile(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    Thumbnails.of(image).size(300, 300).outputQuality(0.9f).toFile(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg
     * 那么三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File file = new File(realFileParentPath);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int rannum= RANDOM.nextInt(99999)+10000;
        String nowTimeStr = SIMPLE_DATE_FORMAT.format(new Date());
        return nowTimeStr+rannum;
    }

    /**
     * 根据相对路径删除已存在的文件和文件夹
     * @param storePath 文件的路径或是目录的路径
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()){
            //如果是路径，删除路径及其以下的文件
            if (fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                for (File file:files){
                    file.delete();
                }
            }
            //如果是文件，删除文件；是最后一个文件夹则删除文件夹
            fileOrPath.delete();
        }
    }
}
