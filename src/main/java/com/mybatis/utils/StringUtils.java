package com.mybatis.utils;

/**
 * Created by 51667 on 2017/12/20.
 */
public class StringUtils {
    public static boolean isNotEmpty(String str){
        if(null==str||"".equals(str.trim())){
            return false;
        }
        return true;
    }
}
