package com.nii.desktop.util.conf;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Encoder {

    public static String EncoderByMd5(String str) {
        // ȷ�����㷽��
        String newstr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // ���ܺ���ַ���
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newstr;
    }
    
    public static void main(String[] args) {
        String str = "123456789";
        System.out.println(str);
        System.out.println(EncoderByMd5(str));
        
    }

}
