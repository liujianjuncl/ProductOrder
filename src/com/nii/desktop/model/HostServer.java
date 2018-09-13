package com.nii.desktop.model;

/**
 * Created by wzj on 2017/1/2.
 */
public class HostServer {
    private String serverName;

    public HostServer() {

    }

    public HostServer(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
