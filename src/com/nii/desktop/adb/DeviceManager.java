package com.nii.desktop.adb;

/**
 * �豸������
 * Created by wzj on 2017/8/21.
 */
public class DeviceManager
{
    /*
     * ����
     */
    private static DeviceManager INSTANCE = null;

    /**
     * ��װ��
     */
    private AndroidDebugBridgeWrapper androidDebugBridgeWrapper;

    /**
     * �豸������
     */
    private DeviceChangeListener deviceChangeListener;

    /**
     * ˽�й��캯��
     */
    private DeviceManager()
    {

    }

    /**
     * ��ȡ������
     * @return DeviceManager
     */
    public static DeviceManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DeviceManager();
        }

        return INSTANCE;
    }

    /**
     * ��������
     */
    public void start()
    {
        androidDebugBridgeWrapper = new AndroidDebugBridgeWrapper();
        deviceChangeListener = new DeviceChangeListener();

        androidDebugBridgeWrapper.addDeviceChangeListener(deviceChangeListener);
        androidDebugBridgeWrapper.init(false);

        System.out.println("Device manager start successful.");
    }

    /**
     * ���ٷ���
     */
    public void destory()
    {
        if (androidDebugBridgeWrapper == null)
        {
            return;
        }

        androidDebugBridgeWrapper.removeDeviceChangeListener(deviceChangeListener);
        androidDebugBridgeWrapper.terminate();
    }

}
