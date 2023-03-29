package com.example.dechuan.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Md5Utils {
	public static String md5(String data) {
		try {
			byte[] md5 = md5(data.getBytes("utf-8"));
			return toHexString(md5);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static byte[] md5(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(data);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}
	
	private static String toHexString(byte[] md5) {
		StringBuilder sb = new StringBuilder();
		for(byte b : md5) {
			sb.append(leftPad(Integer.toHexString(b & 0xff), '0', 2));
		}
		return sb.toString();
	}
	
	private static String leftPad(String hex, char c, int size) {
		char[] cs = new char[size];
		Arrays.fill(cs, c);
		System.arraycopy(hex.toCharArray(), 0, cs, cs.length - hex.length(), hex.length());
		return new String(cs);
	}
	

    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{
           //引用  java.security.MessageDigest公共类
           // getInstance("MD5")方法 设置加密格式
            md5 = MessageDigest.getInstance("MD5");  //此句是核心
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();

    }  
  
    /** 
     * 加密解密算法[可逆] 执行一次加密，执行两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
    
    // 测试主函数  ff8a2fed725306ea9cd3e26ea0675875
    public static void main(String args[]) {  
        String s = new String("111111");
        System.out.println(Md5Utils.md5("123456"));
    }

}