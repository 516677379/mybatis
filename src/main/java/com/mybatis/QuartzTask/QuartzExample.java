package com.mybatis.QuartzTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 51667 on 2017/12/19.
 */
@Component
public class QuartzExample {
    /*
    Cron表达式的格式
    {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
    秒	0-59	, - * / 解释：*：每秒触发一次；，指定时间触发一次 如0，5 即0s 5s触发一次；-范围内促发一次 eg:5-20；初始值/偏移量，从初始值每到偏移量触发一次
    分	0-59	, - * / 解释：同上
    小时	0-23	, - * / 解释：同上
    日期	1-31	, - * ? / L W C 解释：同上
    月份	1-12 或者 JAN-DEC	, - * /
    星期	1-7 或者 SUN-SAT	, - * ? / L C # 解释：1个代表星期天[1星期第一天]；？与日期互斥，有了日期此处无意义
    年（可为空）	留空, 1970-2099	, - * /
    特殊字符：*代表所有， ？只在星期与日期中使用，避免冲突，指明非确定的值
     - , /  :此处省略
    L:last简写，用在day-of-month/day-of-week eg:day-of-month域中表示一个月的最后一天,
      如果在day-of-week域中前面加上数字，它表示一个月的最后几天，例如‘6L’就表示一个月的最后一个星期五
     */
    //    每5分钟启动
    @Scheduled(cron = "0 0/5 * * * ?")
    public void timerToNow(){
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
