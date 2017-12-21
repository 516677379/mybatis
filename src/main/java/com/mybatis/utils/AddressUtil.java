package com.mybatis.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 51667 on 2017/12/20.
 */
public class AddressUtil {
    /**
     * 更具经纬度获取地址
     * @param latitude
     * @param longitude
     */
    public static void getAddresByLatLog(String latitude,String longitude){
        try {
             latitude="30.0000";
             longitude="120.1211";
            BufferedReader in = null;
            URL tirc = new URL("http://api.map.baidu.com/geocoder?location="+ latitude+","+longitude+"&output=json&key="+"E4805d16520de693a3fe707cdc962045");

                in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
                String res;
                StringBuilder sb = new StringBuilder("");
                while((res = in.readLine())!=null){
                    sb.append(res.trim());
                }
                String str = sb.toString();
                System.out.println(str);
                ObjectMapper mapper = new ObjectMapper();
                if(null!=str&&!"".equals(str)){
                    JsonNode jsonNode = mapper.readTree(str);
                    jsonNode.findValue("status").toString();
                    JsonNode resultNode = jsonNode.findValue("result");
                    JsonNode locationNode = resultNode.findValue("formatted_address");
                    System.out.println(locationNode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * 根据地址获取经纬度
     * @param address
     */
    public  void getGeocoderLatitude(String address){
         address="浙江省杭州市西湖区";
        BufferedReader in = null;
        try {
            address = URLEncoder.encode(address, "UTF-8");
            URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address +"&output=json&key="+"7d9fbeb43e975cd1e9477a7e5d5e192a");
            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while((res = in.readLine())!=null){
                sb.append(res.trim());
            }
            String str = sb.toString();
            if(StringUtils.isNotEmpty(str)){
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
                    String lng = str.substring(lngStart+5, lngEnd);
                    String lat = str.substring(lngEnd+7, latEnd);
                    System.out.println("lat"+lat);
                    System.out.println("lng"+lng);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
