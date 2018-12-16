package com.nii.desktop.util.conf;

import java.util.HashMap;
import java.util.Map;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.User;
import com.nii.desktop.model.Work;
import com.nii.desktop.model.WorkDetail;

import javafx.stage.Stage;

public class SessionUtil {

    // ���ڴ洢ȫ��Stage
    public static Map<String, Stage> STAGES = new HashMap<String, Stage>();

    // ���ڴ洢ȫ��Controller
    public static Map<String, Object> CONTROLLERS = new HashMap<String, Object>();

    // ���ڴ洢ȫ���û�
    public static Map<String, User> USERS = new HashMap<String, User>();

    // ���ڴ洢ȫ���ձ�
    public static Map<String, Daily> DAILYS = new HashMap<String, Daily>();

    // ���ڴ洢ȫ����ҵ
    public static Map<String, Work> WORKS = new HashMap<String, Work>();

    // ���ڴ洢ȫ����ҵ
    public static Map<String, WorkDetail> WORKDETAILS = new HashMap<String, WorkDetail>();
    
   // ���ڴ洢ȫ�ֱ���
    public static Map<String, Object> PARAMS = new HashMap<String, Object>();

}
