package com.nii.desktop.util.conf;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encoder {

    //�ַ�������
    public static String EncoderByMd5(String str) {
        // ȷ�����㷽��
        String newstr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            
            // ���ܺ���ַ���
            newstr = Base64.getEncoder().encodeToString(md5.digest(str.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 

        return newstr;
    }
    
    public static void main(String[] args) {
        System.out.println(EncoderByMd5("123456"));
    }
}
