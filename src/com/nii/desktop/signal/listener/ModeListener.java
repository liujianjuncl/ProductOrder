package com.nii.desktop.signal.listener;

import com.nii.desktop.signal.event.ModeEvent;

import java.util.EventListener;

/**
 * Created by wzj on 2017/1/23.
 */
public interface ModeListener extends EventListener
{
    //����ʵ�ֵĽӿ�
    void handleEvent(ModeEvent event);
}
