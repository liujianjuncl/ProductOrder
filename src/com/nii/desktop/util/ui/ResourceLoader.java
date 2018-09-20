package com.nii.desktop.util.ui;

import java.net.URL;

/**
 * Created by ljj on 2018/9/10.
 */
public final class ResourceLoader {
    /**
     * 私有构造函数
     */
    private ResourceLoader() {

    }

    public static URL getFxmlResource(String fxmlName) {
        String resourcePath = "/resources/fxml/" + fxmlName;
        return ResourceLoader.class.getResource(resourcePath);
    }

    public static URL getPropsResource(String propName) {
        String resourcePath = "/resources/conf/" + propName;
        return ResourceLoader.class.getResource(resourcePath);
    }
}
