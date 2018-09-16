package com.nii.desktop.util.ui;

import java.net.URL;

/**
 * Created by wzj on 2016/12/25.
 */
public final class ResourceLoader {
    /**
     * ˽�й��캯��
     */
    private ResourceLoader() {

    }

    public static URL getFxmlResource(String fxmlName) {
        String resourcePath = "/resources/fxml/" + fxmlName;
        return ResourceLoader.class.getResource(resourcePath);
    }
    
    public static URL getPropertiesResource(String propName) {
        String resourcePath = "/resources/conf/" + propName;
        return ResourceLoader.class.getResource(resourcePath);
    }
}
