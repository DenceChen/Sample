package com.github.sgwhp.openapm.monitor;

import com.github.sgwhp.openapm.monitor.data.KeyInfo;
import com.github.sgwhp.openapm.monitor.data.KeyOperationBean;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by chenqihong on 2017/2/9.
 */

public class KeyOperationHandler {
    private LinkedList<KeyInfo> mBeanLinkedList = new LinkedList<>();
    private static KeyOperationHandler mInstance;

    private Iterator<KeyInfo> mDescendingIterator;

    public static KeyOperationHandler getInstance(){
        if(null == mInstance){
            mInstance = new KeyOperationHandler();
        }

        return mInstance;
    }

    private KeyOperationHandler(){

    }

    public void refreshDescendingIterator(){
        mDescendingIterator = mBeanLinkedList.descendingIterator();
    }

    public KeyInfo nextDescendingElement(){
        return mDescendingIterator.next();
    }

    public boolean hasNext(){
        return mDescendingIterator.hasNext();
    }

    public void addList(KeyInfo bean){
        mBeanLinkedList.add(bean);
    }

    public void clear(){
        mBeanLinkedList.clear();
    }


}
