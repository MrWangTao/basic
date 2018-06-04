package com.bear.house.encryption.twoway.notsymmetric;

//import org.apache.commons.codec.binary.Base64;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 1： 需要依赖 commons-codec 包<br>
 *     这里的Base64来源于此包 Base64.decodeBase64(key)； Base64.encodeBase64String(bytes)<br>
 * 2： jdk1.8之后使用
 * @author WangTao
 * @author HuangXin
 * Created at 18/6/4 上午10:11.
 */
public class RSADetail {

    // key的算法名称
    public static final String KEY_ALGORITHM = "RSA";
    // 签名的算法名称
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    // 公钥
    private static final String PUBLIC_KEY = "RSAPublicKey";
    // 私钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 解密  <br>
     * 1: Base64.decodeBase64(key)  来源commons-codec  <br>
     * 2: Base64.decode(key)  jdk1.8之后使用jdk自身的，但是需要抛出Base64DecodingException异常
     */
    public static byte[] decryptBASE64(String key) throws Base64DecodingException{
        // return Base64.decodeBase64(key);
        return Base64.decode(key);
    }

    /**
     * 加密 <br>
     * 1: Base64.encodeBase64String(bytes)  来源commons-codec  <br>
     * 2: Base64.encode(bytes)  jdk1.8之后使用jdk自身的
     * @param bytes
     * @return
     */
    public static String encryptBASE64(byte[] bytes) {
        // return Base64.encodeBase64String(bytes);
        return Base64.encode(bytes);
    }

    /**
     * 用私钥对信息生成数字签名
     * @param data
     * @param privateKey
     * @return
     * @throws Base64DecodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String sign(byte[] data, String privateKey) throws Base64DecodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Base64DecodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Base64DecodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 通过私钥解密
     * @param data
     * @param key
     * @return
     * @throws Base64DecodingException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Base64DecodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥<br>
     * 获取公钥和私钥
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        // 获取编码方式的键对对象
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }


    public static void main(String[] args) throws Exception {
        Map<String, Key> keyMap = initKey();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);

        System.out.println(keyMap);
        System.out.println("-----------------------------------");
        System.out.println(publicKey);
        System.out.println("-----------------------------------");
        System.out.println(privateKey);
        System.out.println("-----------------------------------");
        byte[] encryptByPrivateKey = encryptByPrivateKey("123456".getBytes(),privateKey);
        byte[] encryptByPublicKey = encryptByPublicKey("123456",publicKey);
        System.out.println(new String(encryptByPrivateKey));
        System.out.println("-----------------------------------");
        System.out.println(new String(encryptByPublicKey));
        System.out.println("-----------------------------------");
        String sign = sign(encryptByPrivateKey,privateKey);
        System.out.println(sign);
        System.out.println("-----------------------------------");
        boolean verify = verify(encryptByPrivateKey,publicKey,sign);
        System.out.println(verify);
        System.out.println("-----------------------------------");
        byte[] decryptByPublicKey = decryptByPublicKey(encryptByPrivateKey,publicKey);
        byte[] decryptByPrivateKey = decryptByPrivateKey(encryptByPublicKey,privateKey);
        System.out.println(new String(decryptByPublicKey));
        System.out.println("-----------------------------------");
        System.out.println(new String(decryptByPrivateKey));

    }

}
