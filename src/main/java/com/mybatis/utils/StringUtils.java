package com.mybatis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 51667 on 2017/12/20.
 */
public class StringUtils {
    /**
     * string是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if(null==str||str.trim().length()==0){
            return false;
        }
        return true;
    }

    /**
     * 是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
