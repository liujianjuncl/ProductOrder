package com.nii.desktop.adb;

import com.android.ddmlib.AndroidDebugBridge;

/**
 * Created by wzj on 2017/8/21.
 */
public class AndroidDebugBridgeWrapper
{
    /**
     * android bridge
     */
    private AndroidDebugBridge mAdbBridge;


    public AndroidDebugBridgeWrapper()
    {
    }

    public void init(boolean clientSupport)
    {
        AndroidDebugBridge.init(clientSupport);
        mAdbBridge = AndroidDebugBridge.createBridge("adb", false);
    }

    /**
     * ×¢²áÉè±¸¼àÌıÆ÷
     *
     * @param listener ¼àÌıÆ÷
     */
    public void addDeviceChangeListener(AndroidDebugBridge.IDeviceChangeListener listener)
    {
        AndroidDebugBridge.addDeviceChangeListener(listener);
    }

    /**
     * ÒÆ³ı¼àÌıÆ÷
     *
     * @param listener ¼àÌıÆ÷
     */
    public void removeDeviceChangeListener(AndroidDebugBridge.IDeviceChangeListener listener)
    {
        AndroidDebugBridge.removeDeviceChangeListener(listener);
    }

    public void terminate()
    {
        AndroidDebugBridge.terminate();
    }

    public void disconnectBridge()
    {
        AndroidDebugBridge.disconnectBridge();
    }
}
