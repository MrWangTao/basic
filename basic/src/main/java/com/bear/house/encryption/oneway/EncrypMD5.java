package com.bear.house.encryption.oneway;

import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 即Message-Digest Algorithm 5（信息-摘要算法 5），用于确保信息传输完整一致。
 * 是计算机广泛使用的杂凑算法之一（又译摘要算法、哈希算法），主流编程语言普遍已有MD5实现。
 * 将数据（如汉字）运算为另一固定长度值，是杂凑算法的基础原理，MD5的前身有MD2、MD3和MD4。
 * MD5的作用是让大容量信息在用数字签名软件签署私人密钥前被"压缩"成一种保密的格式（就是把一个任意长度的字节串变换成一定长的十六进制数字串）。
 * 除了MD5以外，其中比较有名的还有sha-1、RIPEMD以及Haval等
 */
public class EncrypMD5 {

    public byte[] eccrypt(String info) throws NoSuchAlgorithmException{
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        // md5.digest(srcBytes);
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    /**
     * 返回32位字符串
     * @param originString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getEncryption(String originString)
            throws UnsupportedEncodingException {
        String result = "";
        if (originString != null) {
            try {
                // 指定加密的方式为MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 进行加密运算
                byte bytes[] = md.digest(originString.getBytes("ISO8859-1"));
                for (int i = 0; i < bytes.length; i++) {
                    // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
                    String str = Integer.toHexString(bytes[i] & 0xFF);
                    if (str.length() == 1) {
                        str += "F";
                    }
                    result += str;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String msg = "fucking";
        EncrypMD5 md5 = new EncrypMD5();
        byte[] resultBytes = md5.eccrypt(msg);
        String content = new String(resultBytes, "UTF-8");
        System.out.println(resultBytes.toString());
        System.out.println("密文是：" + new String(resultBytes));
        System.out.println("明文是：" + msg);
        System.out.println(EncrypMD5.getEncryption(msg).toUpperCase());
    }

}