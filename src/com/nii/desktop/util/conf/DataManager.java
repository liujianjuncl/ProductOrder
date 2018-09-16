package com.nii.desktop.util.conf;

import java.util.HashMap;
import java.util.Map;

import com.nii.desktop.model.User;

import javafx.stage.Stage;

public class DataManager {
    
    public static Map<String, Stage> STAGES=new HashMap<String, Stage>();
    
    public static Map<String, Object> CONTROLLERS=new HashMap<String, Object>();
    
    public static Map<String, User> USERS=new HashMap<String, User>();

}
