package com.imooc.o2o.enums;

public enum AreaStateEnum {
    //枚举
    OFFLINE(-1, "非法操作"), SUCCESS(1, "操作成功"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_AREA_ID(-1002,"areaId参数错误"),INSERT_ERROR(-1003,"插入area失败"),
    INSERT_REPEAT(-1004,"输入了已有的区域信息"),NULL_AREA(-1003,"area信息为空");
    private int state ;

    private String stateInfo ;

    AreaStateEnum(int state, String stateInfo) {
        this.state = state ;
        this.stateInfo = stateInfo ;
    }
    //根据状态返回一个
    public static AreaStateEnum stateOf(int state){
        for(AreaStateEnum stateEnum:values()){
            if(stateEnum.getState() == state){
                return stateEnum ;
            }
        }
        return null ;
    }
    //获取属性的方法
    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
