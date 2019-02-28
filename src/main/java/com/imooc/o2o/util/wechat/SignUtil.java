package com.imooc.o2o.util.wechat;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 *  微信请求校验工具类
 */
public class SignUtil {
    //设置token，要与接口token配置信息中的一致
    private static String token="O2o";

    /**
     * 用于微信接口校验
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){

        String[] arr = new String[]{token,timestamp,nonce};
        //将token，timestamp,nonce三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest messageDigest = null ;
        String tmpStr = null ;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            //将三个参数字符串拼接成一个字符串进行SHA-1加密,然后返回byte数组给digest
            byte[] digest = messageDigest.digest(content.toString().getBytes());
            //转换成16进制字符串
            tmpStr = byteTostr(digest);
        }catch (Exception e){
            e.printStackTrace();
        }
        content = null ;
        //将SHA-1加密后的字符串与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()):false;
    }

    /**
     * 将字节数组转换为字符串
     * @param byteArray
     * @return
     */
    private static String byteTostr(byte[] byteArray){
        String strDigest = "" ;
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte){
        char[] digit= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4)&0X0F];
        tempArr[1] = digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s ;
    }
}
