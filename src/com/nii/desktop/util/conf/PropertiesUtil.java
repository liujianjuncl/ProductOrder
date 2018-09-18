package com.nii.desktop.util.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nii.desktop.util.ui.ResourceLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.tools.Tool;

/**
 * Created by wzj on 2017/1/1.
 */
public final class PropertiesUtil {
    /**
     * ��־
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * ˽�й��캯��
     */
    private PropertiesUtil() {

    }

    /**
     * ��ȡĬ�ϵ�properties
     * 
     * @return Properties
     */
    public static Properties getDefaultProperties() {
        Properties defaultProperties = new Properties();
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(ResourceLoader.getPropertiesResource("config.properties").getFile());
            InputStreamReader reader = new InputStreamReader(fin, "GBK");
            defaultProperties.load(reader);
            
            fin.close();
        } catch (IOException e) {
            LOGGER.error("��ȡĬ�������ļ�ʧ�ܣ�", e);
        }

        return defaultProperties;
    }

    /**
     * get String vale
     * 
     * @param key key
     * @return ���
     */
    public static String getStringValue(String key) {
        Properties defaultProperties = getDefaultProperties();
        return defaultProperties.getProperty(key);
    }

    /**
     * ��ȡָ��·����properties
     * 
     * @return Properties
     */
    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();
        FileInputStream fin = null;
        InputStreamReader reader = null;
        try {
            fin = new FileInputStream(ResourceLoader.getPropertiesResource(filePath).getFile());
            reader = new InputStreamReader(fin, "GBK");
            prop.load(reader);
            
            reader.close();
            fin.close();
        } catch (IOException e) {
            LOGGER.error("��ȡ�����ļ�ʧ�ܣ�", e);
        }

        return prop;
    }

    /**
     * ���ļ�����д������
     * 
     * @param key   key
     * @param value value
     * @return true �ɹ� | false ʧ��
     */
    public static boolean writeHostInfo(String key, String value) {
        Properties prop = new Properties();
        FileOutputStream fos = null;

        try {
            System.out.println(ResourceLoader.getPropertiesResource("host.properties").getFile());
            fos = new FileOutputStream(ResourceLoader.getPropertiesResource("host.properties").getPath());
            prop.setProperty(key, value);
            prop.store(fos, "����������");
            
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } 

        return true;
    }

    public static void main(String[] args) {
        Properties prop = getDefaultProperties();
//        System.out.println(prop);
        boolean res = writeHostInfo("host", "127.0.0.1");
        System.out.println(res);
//        System.out.println(ResourceLoader.getPropertiesResource("config.properties").getFile());

    }
}
