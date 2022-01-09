package cn.kevinlu98.cloud.freewindcloud.common;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtils {

    private static final String DES="DES";

    /**
     * 公钥  8位以上
     */
    private static final String SECRET_KEY="kevinlu98.cn";

    /**
     * 获取秘钥对象
     */
    private static SecretKey getSecretKeyFactory() throws Exception {
        SecretKeyFactory des = SecretKeyFactory.getInstance(DES);
        return des.generateSecret(new DESKeySpec(SECRET_KEY.getBytes()));
    }

    /**
     * 加密
     */
    public static String encryption(String param) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey secretKey = getSecretKeyFactory();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return  new String(Base64.encodeBase64(cipher.doFinal(param.toString().getBytes())));
    }

    /**
     * 解密
     */
    public static String decrypt(String value) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecretKey secretKey = getSecretKeyFactory();
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
    }

}