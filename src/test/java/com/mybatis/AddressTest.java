package com.mybatis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by 51667 on 2017/12/21.
 */
public class AddressTest {
    @Test
    public void addressTest(){
        try {
           String latitude="23.933";
            String  longitude="117.637";
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
}
