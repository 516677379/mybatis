package com.mybatis.QuartzTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 51667 on 2017/12/19.
 * 该配置为单线程，若改为多线程可设置配置类
 * ScheduleConfig implements SchedulingConfigurer{
      public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
     }
 }
 *
 * }
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
    /**
     *  @Scheduled 其他参数类型
     1.cron：cron表达式，指定任务在特定时间执行；
     2.fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
     3.fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
     4.fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
     5.fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
     6.initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
     7.initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
     8.zone：时区，默认为当前时区，一般没有用到。
     */
}
