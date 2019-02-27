package com.imooc.o2o.dto;
/**
 * Created by Administrator on 2018/12/12.
 *
 * @author Administrator
 */

/**
 *
 *@className Result
 *@description 封装json对象，所有的返回结果都是用它
 *@autor Administrator
 *@date 2018/12/12 17:33
 **/
public class Result<T> {
    /**
     * 是否成功标志
     */
    private boolean success;

    /**
     * 成功时返回的数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String errorMsg;

    private int errrorCode;

    public Result(){

    }

    /**
     * 成功时的构造器
     * @param success
     * @param data
     */
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 失败时的构造器
     * @param success
     * @param errorMsg
     * @param errrorCode
     */
    public Result(boolean success, String errorMsg, int errrorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errrorCode = errrorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrrorCode() {
        return errrorCode;
    }

    public void setErrrorCode(int errrorCode) {
        this.errrorCode = errrorCode;
    }
}
