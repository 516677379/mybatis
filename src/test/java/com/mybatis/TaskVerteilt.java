package com.mybatis;

import org.junit.Test;

/**
 * Created by zyf on 2017/12/29.
 */
public class TaskVerteilt {

    @Test
    public void test(){
        int[] a={1,2};
        char[] b={'a','b'};
        int[] c={1,2,3,4,5,6,7};
        int avg=(int)Math.ceil(c.length*1.0/a.length);
        System.out.println("avg"+avg);
        int k=0;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<avg;j++){
                if(k==(c.length-1)) {break;}k++;
                System.out.println("id"+a[i]+"名"+b[i]+"renwu"+c[k]);

            }
        }
    }

    /**
     * 任务c平均分配给a这些人
     */
    @Test
    public void test2(){
        int[] a={1,2,3,4};
        char[] b={'a','b','c','d'};
        int[] c={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
        int t=(c.length*1.0%a.length)>0?1:0;//若有余数需要多分配一次
        System.out.println("t"+t);
        int avg=(int)Math.floor(c.length*1.0/a.length)+t;//取得分配次数
        System.out.println("avg"+avg);
        int k=0;
        for(int i=0;i<avg;i++){
            for(int j=0;j<a.length;j++){
                System.out.println("人"+a[j]+"名"+b[j]+"任务"+c[k]);
                if(k==(c.length-1))break;//如果任务已经分配完了 跳出循环
                k++;
            }
        }
    }
}
