package com.imooc.o2o.enums;
/**
 * Created by Administrator on 2018/12/9.
 *
 * @author Administrator
 */

/**
 * @className ShopStateEnum
 * @description TODO
 * @autor Administrator
 * @date 2018/12/9 14:40
 **/
public enum ShopStateEnum {
    //店铺状态枚举值
    CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"),PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop信息为空");
    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        for (ShopStateEnum stateEnum:values()
             ) {
            if (stateEnum.getState()==state){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
