package com.mybatis.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 51667 on 2017/12/25.
 */
public class DateUtil {

    /**
     * 将日期按指定格式输入
     * @param date
     * @param pattern
     * @return
     */
    public static String patternDate(Date date, String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     *某天添加指定天数
     * @param date
     * @param days
     * @return
     */
    public static Date addSomeDay(Date date,int days){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,days);
        return calendar.getTime();
    }

    /**
     * 字符串转换成日期
     * @param strDate
     * @param formatStr
     * @return
     */
    public static Date strToDate(String strDate, String formatStr ) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 两日期之间间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     * @param date
     * @return
     */
    public static HashMap<String,String> getTimeInterval(Date date) {
        HashMap<String, String> result = new HashMap<String,String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());//周一日期
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());//周日日期
        result.put("start",imptimeBegin);
        result.put("end",imptimeEnd);
        return result;
    }

    public static void main(String[] args){
        Date day1=addSomeDay(new Date(),1);
        String day=patternDate(day1,"yyyy-MM-dd HH:mm:ss");
        System.out.println(day);

        HashMap<String,String> map=getTimeInterval(new Date());
        System.out.println(map.get("start"));
        System.out.println(map.get("end"));
    }
}
