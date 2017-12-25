package com.mybatis;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by 51667 on 2017/12/25.
 */
public class NoRunTest {

    /**
     * 推荐不用 可以解码，容易推测
     */
    @Test
    public void base64Test(){
        try {
            //普通编码
            String base64= Base64.getEncoder().encodeToString("www.baidu.com?pageNum=1".getBytes("utf-8"));
            System.out.println("编码"+base64);
            //普通解码
            byte[] back=Base64.getDecoder().decode(base64);
            System.out.println("解码"+new String(back,"utf-8"));

            //url编码 同上基本一样 只是解析出来上方就是string  而这个为可点的链接
            String baseUrl=Base64.getUrlEncoder().encodeToString("http://www.baidu.com?pageNum=1".getBytes());
            System.out.println("url:"+baseUrl);
            byte[] url=Base64.getDecoder().decode(baseUrl);
            System.out.println("url解码："+new String(url,"utf-8"));

            //MIME编码 使用基本字母数字产生base64输出，而且对mime格式友好，每行不超过76个字符，而且每行以\r\n结束
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<1;i++){
                sb.append(UUID.randomUUID().toString());
            }
            sb.append("jasfljdsa");
            byte[] sbByte=sb.toString().getBytes("utf-8");
           String mime=Base64.getMimeEncoder().encodeToString(sbByte);
           System.out.println("mime"+mime);
           byte[] backMine=Base64.getMimeDecoder().decode(mime.getBytes());
            System.out.println("mime解码"+new String(backMine,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     *  MD5: Message-Digest Algorithm 5（信息-摘要算法）
     *  特点：
     *   不能根据加密后的信息找加密之前的信息
     *   加密后的结果是128位
     *   对于给定的字节数组，不管什么时候采用这种加密算法，结果是相同的；
     *   对于不同的字节数组，加密的结果是不相同的。
     * 特殊处理
     *   1.需要传送加密采用Https协议，成本高
     *   2.需要找回密码，加密后密码无法解码
     * 步骤
     * l 把要加密的信息转换成字节数组；
     * 2.获取MessageDigest对象，该对象完成加密；
     * 3.使用转换后的字节数组初始化MessgeDigest对象；
     * 4.调用digest方法进行加密，返回byte数组；
     * 5.把byte数组转换成字符串，然后就可以使用加密后的字符串了。
     */
    @Test
    public void commonMD5SandSha1(){
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String cleanData="测试加密对象";
     //   String algorithm="MD5";//或者是 SHA-1
        String algorithm="SHA-1";
        try {
            byte[] btInput = cleanData.getBytes("UTF-16LE");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(algorithm);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            System.out.println("通用"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
