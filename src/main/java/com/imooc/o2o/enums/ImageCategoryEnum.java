package com.imooc.o2o.enums;
/**
 * Created by Administrator on 2019/2/28.
 *
 * @author Administrator
 */

/**
 * @className ImageCategoryEnum
 * @description
 * @autor Administrator
 * @date 2019/2/28 16:52
 **/
public enum ImageCategoryEnum {
    NORMAL_IMG(0, "正常水印图"), THUMBNAIL_IMG(1, "缩略水印图"), USER_IMG(2, "用户无水印图");

    private int state;
    private String stateInfo;

    ImageCategoryEnum(int i, String s) {
        this.state = i;
        this.stateInfo = s;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
