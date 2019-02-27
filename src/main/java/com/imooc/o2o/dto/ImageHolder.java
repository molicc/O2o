package com.imooc.o2o.dto;
/**
 * Created by Administrator on 2018/12/14.
 *
 * @author Administrator
 */

import java.io.InputStream;

/**
 * @className ImageHolder
 * @description TODO
 * @autor Administrator
 * @date 2018/12/14 14:40
 **/
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
