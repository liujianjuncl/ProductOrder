package com.nii.desktop.signal.manager;

import com.nii.desktop.signal.event.ModeEvent;
import com.nii.desktop.signal.listener.ModeListener;

import java.util.*;

/**
 * Created by wzj on 2017/1/22.
 */
public class ModeManager<T> {
    /**
     * �¼��������Ĵ洢Map
     */
    private Map<T, List<ModeListener>> listenerMap = new HashMap<T, List<ModeListener>>();

    /**
     * ע�������
     * 
     * @param type     ����
     * @param listener ����������
     */
    public void addChangeListener(T type, ModeListener listener) {
        List<ModeListener> listenerList = listenerMap.get(type);

        // �����ʱû�и����͵ļ�����
        if (listenerList == null) {
            listenerList = new ArrayList<ModeListener>();
            listenerList.add(listener);
            listenerMap.put(type, listenerList);

            return;
        }

        listenerList.add(listener);
    }

    /**
     * �Ƿ�ɹ��Ƴ�
     * 
     * @param type     ����
     * @param listener ������
     * @return �Ƴ���� true �ɹ� | false �����ڻ����Ƴ�ʧ��
     */
    public boolean removeChangeListener(T type, ModeListener listener) {
        List<ModeListener> listenerList = listenerMap.get(type);

        if (listenerList == null) {
            return false;
        }

        return listenerList.remove(listener);
    }

    /**
     * ֪ͨ���еļ�����
     * 
     * @param type
     * @param source
     */
    protected void notifyListener(T type, Object source) {
        List<ModeListener> listenerList = listenerMap.get(type);

        if (listenerList == null) {
            return;
        }

        for (ModeListener listener : listenerList) {
            listener.handleEvent(new ModeEvent(source));
        }
    }
}
