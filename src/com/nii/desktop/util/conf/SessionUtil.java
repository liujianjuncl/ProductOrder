package com.nii.desktop.util.conf;

import java.util.HashMap;
import java.util.Map;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.User;

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

}
