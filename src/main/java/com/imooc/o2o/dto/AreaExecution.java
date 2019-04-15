package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.enums.AreaStateEnum;

import java.util.List;

/**
 * 封装了对area进项操作的返回集
 * 针对不同的操作结果有不同的构造方法
 * @author ft
 * create  on 2019-03-27
 */
public class AreaExecution {
    //结果状态码,应该为state和stateInfo封装一个枚举类型
    private int state ;
    //状态标识
    private String stateInfo ;
    //数量，主要是查询的时候用，可以用作分页参数的依据
    private int count = 1;
    //单个Area,在更新或者查询单个店铺的时候应该把最新的修改结果返回回来
    private Area area ;
    //在查询的时候需要用到，需要返回结果集合，用ArrayList实现
    private List<Area> areaList ;

    //构默认造方法
    public AreaExecution(){
    }
    //失败的构造方法
    public AreaExecution(AreaStateEnum areaStateEnum){
        this.state = areaStateEnum.getState() ;
        this.stateInfo = areaStateEnum.getStateInfo();
    }
    //成功的构造方法，单个店铺
    public AreaExecution(AreaStateEnum areaStateEnum, Area area){
        this.state = areaStateEnum.getState() ;
        this.stateInfo = areaStateEnum.getStateInfo();
        this.area = area ;
    }
    //成功的构造方法，区域列表
    public AreaExecution(AreaStateEnum areaStateEnum, List<Area> areaList){
        this.state = areaStateEnum.getState() ;
        this.stateInfo = areaStateEnum.getStateInfo();
        this.areaList = areaList ;
    }
    //getter AND setter
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
