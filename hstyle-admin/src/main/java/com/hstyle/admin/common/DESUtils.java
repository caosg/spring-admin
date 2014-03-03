package com.hstyle.admin.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import com.sun.org.apache.xml.internal.security.utils.Base64; 
public class DESUtils {
	public final static String FILEPATH = "C:\\BitIO.txt";
	public final static String 	CHARSET="UTF-8";
    /** 
     * 生成密钥  
     * @return 密钥内容 
     * @throws NoSuchAlgorithmException 
     */  
    public void generateKey() throws NoSuchAlgorithmException {  
        //实例化密钥生成器  
        KeyGenerator kg = KeyGenerator.getInstance("DES");  
        //初始化  
        kg.init(56);  
        //生成密钥  
        SecretKey secretKey = kg.generateKey();  
        //获得二进制编码的密钥  
        byte[] b = secretKey.getEncoded();  
  
        /* 
         * 将密钥写入到文件中 
         * */  
        File fileInst = new File(FILEPATH);  
        try {  
            if (!fileInst.exists())  
                fileInst.createNewFile();  
            FileWriter fw = new FileWriter(fileInst);  
            //对生成的密钥进行了BASE64加密  
            String keyEncode = Base64.encode(b);  
            System.out.println("生成的密钥:"+keyEncode);  
            fw.write(keyEncode);  
            fw.flush();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        }  
    }  
  
    private byte[] getKey() {  
        byte[] content = null;  
        try {  
            File fileInst = new File(FILEPATH);  
            BufferedInputStream in = new BufferedInputStream(  
                    new FileInputStream(fileInst));  
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);  
            byte[] temp = new byte[1024];  
            int size = 0;  
            while ((size = in.read(temp)) != -1) {  
                out.write(temp, 0, size);  
            }  
            in.close();  
            content = out.toByteArray();  
            content = Base64.decode(content);  
        } catch(IOException ioe){  
            ioe.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
        return content;  
    }  
      
    /** 
     * DES数据加密 
     * @return 加密后的数据(用户base64进行了编码) 
     */  
    public static String encryptData(String message,String key)throws Exception{  
        byte[] encrypt =null;  
        byte[] sk =  Base64.decode(key.getBytes());  
        try {  
            //实例化密钥材料  
            DESKeySpec dks = new DESKeySpec(sk);  
            //实例化秘密密钥工厂  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            //生成秘密密钥  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            //实例化  
            Cipher cipher = Cipher.getInstance("DES");  
            //实始化，设置为加密模式  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);  
            //执行加密操作  
            encrypt = cipher.doFinal(message.getBytes());  
        } catch (Exception e) {  
        }  
        //对加密后的内容进行了BASE64编码。非必须，对于网络传送数据来说，二进制更为理想  
        String encryptStr = Base64.encode(encrypt);  
        return encryptStr;  
    }  
      
    /** 
     * DES数据解密 
     * @param d 需要解密的数据 
     * @return 解密后的数据 
     */  
    public static  String decryptData(String encryptStr,String key)throws Exception{  
        byte[] decrypt =null;  
        /* 
         * 得到密钥材料 
         * */  
        byte[] sk =  Base64.decode(key.getBytes()); 
        try {  
            //实例化密钥材料  
            DESKeySpec dks = new DESKeySpec(sk);  
            //实例化秘密密钥工厂  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            //生成秘密密钥  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            //实例化  
            Cipher cipher = Cipher.getInstance("DES");  
            //实始化，设置为解密模式  
            cipher.init(Cipher.DECRYPT_MODE, secretKey);  
            //执行解密操作  
            decrypt = cipher.doFinal(Base64.decode(encryptStr));  
        } catch (Exception e) {  
        }  
        return new String(decrypt);  
    }  
      /**
       * 
      * @Title: encodeBase64
      * @Description: TODO(这里用一句话描述这个方法的作用)
      * @param @param message
      * @param @return    设定文件
      * @return String    返回类型
      * @throws
       */
   public static String encodeBase64(String message){
	   try {
		return Base64.encode(message.getBytes(CHARSET));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
   }
    /**
     * 
    * @Title: decodeBase64
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param encryptStr
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
     */
   public static String decodeBase64(String encryptStr){
	   try
	   {
		   return new String(Base64.decode(encryptStr.getBytes()),CHARSET);
	   }catch(Exception e){
		   e.printStackTrace();
		   return null;
	   }
   }
    public static void main(String[] args) throws Exception{  
    	DESUtils dt = new DESUtils();
    	System.out.println(new String(Base64.encode("ceshi123".getBytes())));
       System.out.println( dt.encryptData("12345", "MRVnGWc7y4k="));
       System.out.println(new String(dt.decryptData("5B6PFoqUfTU=", "MRVnGWc7y4k=")));
    }  

}
