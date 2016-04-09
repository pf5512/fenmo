package com.cn.fenmo.util;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
* @author carlos carlosk@163.com
* @version 创建时间：2012-5-17 上午9:48:35 类说明
*/

public class AESDecodeUtils {
    public static final  String KEY = "1QAZ2WSX";
    
    public static void main(String[] args) {
      String content = "123456";  
      // 加密  
      System.out.println("加密前content：" + content);  
      
      String encryptResultStr = encrypt(content);   
      
      System.out.println("加密后content：" + encryptResultStr);  
//      // 解密
      String userAccount = decrypt(encryptResultStr);  
      System.out.println("解密后content：" + userAccount);  
    }
    
    public static String encrypt(String clearText) {
      // Log.d(TAG, "加密前的seed=" + KEY + ",内容为:" + clearText);
      byte[] result = null;
      try {
        byte[] rawkey = getRawKey(KEY.getBytes());
        result = encrypt(rawkey, clearText.getBytes());
      } catch (Exception e) {
        e.printStackTrace();
      }
      String content = toHex(result);
      return content;
    }

    public static String decrypt(String encrypted) {
      // Log.d(TAG, "解密前的seed=" + KEY + ",内容为:" + encrypted);
      byte[] rawKey;
      try {
        rawKey = getRawKey(KEY.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        String coentn = new String(result);
        // Log.d(TAG, "解密后的内容为:" + coentn);
        return coentn;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    private static byte[] getRawKey(byte[] KEY) throws Exception {
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
      sr.setSeed(KEY);
      kgen.init(128, sr);
      SecretKey sKey = kgen.generateKey();
      byte[] raw = sKey.getEncoded();
      return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      // Cipher cipher = Cipher.getInstance("AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
      new byte[cipher.getBlockSize()]));
      byte[] encrypted = cipher.doFinal(clear);
      return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      // Cipher cipher = Cipher.getInstance("AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
      new byte[cipher.getBlockSize()]));
      byte[] decrypted = cipher.doFinal(encrypted);
      return decrypted;
    }

    public static String toHex(String txt) {
      return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
       return new String(toByte(hex));
    }
    
    public static byte[] toByte(String hexString) {
      int len = hexString.length() / 2;
      byte[] result = new byte[len];
      for (int i = 0; i < len; i++)
      result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
      16).byteValue();
      return result;
    }
    
    public static String toHex(byte[] buf) {
      if (buf == null){
        return "";
      }
      StringBuffer result = new StringBuffer(2 * buf.length);
      for (int i = 0; i < buf.length; i++) {
        appendHex(result, buf[i]);
      }
      return result.toString();
    }
      
    private static void appendHex(StringBuffer sb, byte b) {
      final String HEX = "0123456789ABCDEF";
      sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}