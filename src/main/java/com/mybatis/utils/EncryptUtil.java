package com.mybatis.utils;


import java.security.MessageDigest;

/**
 * Created by Mazexal on 2017/4/25.
 * base64加密不做处理，若了解请看 NoRunTest.base64Test();
 * base64并不安全，可对字符做处理然后加密，解密时在处理回来
 */
public class EncryptUtil {

    public static String sha1(String cleanData)
    {
        return hashEncode("SHA-1",cleanData);
    }

    public static String md5(String cleanData) {
        return hashEncode("MD5",cleanData);
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
    private static String hashEncode(String algorithm,String cleanData)
    {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
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
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}