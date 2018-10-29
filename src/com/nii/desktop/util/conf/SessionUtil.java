package com.nii.desktop.util.conf;

import java.util.HashMap;
import java.util.Map;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.User;

import javafx.stage.Stage;

public class SessionUtil {

    // 用于存储全局Stage
    public static Map<String, Stage> STAGES = new HashMap<String, Stage>();

    // 用于存储全局Controller
    public static Map<String, Object> CONTROLLERS = new HashMap<String, Object>();

    // 用于存储全局用户
    public static Map<String, User> USERS = new HashMap<String, User>();
    
 // 用于存储全局日报
    public static Map<String, Daily> DAILYS = new HashMap<String, Daily>();

}
