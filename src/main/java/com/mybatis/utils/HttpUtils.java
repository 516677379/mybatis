package com.mybatis.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyf on 2018/1/4.
 * http://blog.csdn.net/liuchuanhong1/article/details/68194036
 *
 *  1.HttpClient client = HttpClients.createDefault();
 *  2.new httpPost/httpGet
 *  3.HttpResponse response = client.execute(httpGet);
 *
 *  response.getStatusLine().getStatusCode() == 200  响应成功状态码
 *  String conResult = EntityUtils.toString(response.getEntity()); 响应结果
 *
 *  发送json需要contentType
 *  StringEntity s = new StringEntity(jsondata);
 *   s.setContentEncoding("UTF-8"); s.setContentType("application/json");
 *  httppost.setEntity()
 */
public class HttpUtils {


    /**
     * get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String getString(String url)throws IOException
    {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }

    /**
     * post请求
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String postString(String url, Map<String,String> params) throws IOException {
        List<NameValuePair> args = new ArrayList<>();
        for(String key : params.keySet()){
            args.add(new BasicNameValuePair(key,params.get(key)));//防为空
        }
        return postString(url,args);
    }

    public static String postString(String url, List<NameValuePair> params) throws IOException {
        HttpClient client = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        HttpResponse response = client.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }
    public static String postJson(String url, String json) throws IOException {

        HttpClient client = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(url);

        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httppost.setEntity(new StringEntity(json, "utf-8"));

        HttpResponse response = client.execute(httppost);

        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity, "utf-8");
    }

    public static String postJsonUrlencoded(String url, String json) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httppost.setEntity(new StringEntity(json, "utf-8"));
        HttpResponse response = client.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }

    public static String PostRequest(String urlPath,HashMap<String,Object> paramMap) throws Exception {
        String param = "";
        for (String key : paramMap.keySet()) {
            param = param + "&" + key + "=" + URLEncoder.encode(paramMap.get(key)+"","UTF-8");
        }
        param = param.substring(1,param.length());
        //建立连接
        URL url = new URL(urlPath);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("POST");   //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb = new StringBuffer();
            String readLine = new String();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            return sb.toString();
        }
        return null;
    }

    public static String getIP(HttpServletRequest request){
        String ip =  request.getHeader("RemoteIp");

        if (StringUtils.isNotEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (StringUtils.isNotEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isNotEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isNotEmpty(ip)  || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
    public static void main(String[] args){
        try {
            String url="http://localhost:9000/hello/index";
            String getUrl=getString(url);
            System.out.println("getUrl"+getUrl);

            String postUrl=postString(url,new HashMap<String, String>());
            System.out.println("postUrl"+postUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
