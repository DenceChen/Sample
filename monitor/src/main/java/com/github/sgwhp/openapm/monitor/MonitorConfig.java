package com.github.sgwhp.openapm.monitor;

import com.github.sgwhp.openapm.monitor.data.KeyOperationBean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class MonitorConfig {
    private KeyOperationBean mBean;
    private boolean isConfigLoaded;
    public MonitorConfig(){
        Map<String, String> properties = parseProperties();
        if(null == properties){
            return;
        }

        mBean = new KeyOperationBean();
        mBean.addKeyToStart(parseKeys(properties.get("keyToStart")));
        mBean.addKeys(parseKeys(properties.get("keys")));
        mBean.addKeyToFinished(parseKeys(properties.get("keyToFinish")));
    }

    public KeyOperationBean getBean() {
        return mBean;
    }

    public void setBean(KeyOperationBean bean) {
        this.mBean = bean;
    }

    private List<String> parseKeys(String keys){
        String[] keyGroup = keys.split("\\|");
        List<String> keyList = new ArrayList<>();
        for(int i = 0; i < keyGroup.length; i++){
            keyList.add(keyGroup[i]);
        }

        return keyList;
    }

    private Map parseProperties(){
        if(isConfigLoaded){
            return null;
        }

        isConfigLoaded = true;

        Properties properties = new Properties();

        InputStreamReader is = null;
        try{
            is = new InputStreamReader(MonitorConfig.class.getResourceAsStream("/assets/config.properties"), "UTF-8") ;
            if(null == is){
                return null;
            }
            BufferedReader br = new BufferedReader(is);
            properties.load(br);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

}
