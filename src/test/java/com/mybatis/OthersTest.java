package com.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyf on 2018/1/2.
 * 仅仅测试一些不太重要的
 * 测试后可删除的
 */
@EnableAsync
public class OthersTest {

    @Test
    public void testNumber(){
        System.out.println("int:"+52/10);
        System.out.println("double"+52d/10);
    }

    @Test
    public void testDate(){
        Date dt=addDay(new Date(),-5);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(dt));
    }

    public static Date addDay(Date date, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        return calendar.getTime();
    }

    @Test
    public void test(){
        try {
            String url="";
            HttpClient client = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(null, "utf-8"));
            HttpResponse response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            String str= EntityUtils.toString(entity, "utf-8");
            System.out.println("str:"+str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObj() throws Exception {
        System.out.println("1");
        for(int i=0;i<10;i++){
            asyncTest(i);
        }
        System.out.println("2");
    }


    @Async
    public void asyncTest(int i){
        try {
            Thread.sleep(1000);
            System.out.println(i+":"+new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mapTest(){
        Map<String,String> map=new HashMap<String,String>();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        for(String str:map.keySet()){
            System.out.println("1:"+str+" ; "+map.get(str));
        }

        for(Map.Entry<String,String> entry:map.entrySet()){
                System.out.println("2:"+entry.getKey()+"  "+ entry.getValue());
        }


    }


    @Test
    public void test2(){
        try {
            InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson(){
        String str="{\"success\":1,\"challenge\":\"23545a01ae177fbf07b146727ad18668\",\"gt\":\"7df0d51d7acf1c8f24afc59f11553872\"}";
        JSONObject json= JSON.parseObject(str);
        json.put("success",0);
        System.out.println(json.toString());
    }

}
