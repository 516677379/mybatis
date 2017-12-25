package com.mybatis.utils;

import java.text.DecimalFormat;

/**
 * Created by 51667 on 2017/12/25.
 */
public class NumberUtil {

    //double转换为指定位数小数,注意：此处会四舍五入
    public static double parsePatternNum(double parseNum,int pattern){
        StringBuffer sb=new StringBuffer("0");
        for(int i=0;i<pattern;i++){
            if(i==0){ sb.append(".");}
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString()); // 格式化number String字符
        return Double.valueOf(df.format(parseNum));
    }
}
