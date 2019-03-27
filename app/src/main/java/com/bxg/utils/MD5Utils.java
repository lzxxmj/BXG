package com.bxg.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static  String md5(String text){
        //初始化
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            md.update(text.getBytes());
            byte[] result=md.digest();
            StringBuilder sb=new StringBuilder();
            for(byte b:result){
                int number=b&0xff;//将一个字节转换为整数
                String hex=Integer.toHexString(number);//将整数转换为十六进制的字符串
                if(hex.length()==1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }
            }

          return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
}
