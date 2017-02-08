package com.github.sgwhp.openapm.monitor;

import com.github.sgwhp.openapm.monitor.data.KeyOperationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class KeyOperationHandler {
    private static KeyOperationHandler mInstance;
    private List<KeyOperationBean> mBeanList = new ArrayList<>();

    public static KeyOperationHandler getInstance(){
        if(null == mInstance){
            mInstance = new KeyOperationHandler();
        }

        return mInstance;
    }

    private KeyOperationHandler(){

    }

    public void addKeyOperation(KeyOperationBean bean){
        mBeanList.add(bean);
    }

    public List<KeyOperationBean> getOperationList(){
        return mBeanList;
    }
}
