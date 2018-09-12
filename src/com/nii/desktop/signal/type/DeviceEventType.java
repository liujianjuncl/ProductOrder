package com.nii.desktop.signal.type;

/**
 * Created by wzj on 2017/1/22.
 */
public enum DeviceEventType {

    /**
     * ���ָı�
     */
    NUMBER_CHANGE("Number Change"),

    /**
     * ���ָı�
     */
    NAME_CHANGE("Name Change");

    private String desc;

    /**
     * ���캯��
     * 
     * @param desc ������Ϣ
     */
    DeviceEventType(String desc) {
        this.desc = desc;
    }

    /**
     * ��ȡ������Ϣ
     * 
     * @return ������Ϣ
     */
    public String getDesc() {
        return desc;
    }
}
