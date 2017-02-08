package com.github.sgwhp.openapm.monitor;

import com.github.sgwhp.openapm.monitor.data.KeyOperationBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class MonitorConfig {
    public MonitorConfig(){
        Map<String, String> properties = parseProperties();
        if(null == properties){
            return;
        }

        KeyOperationBean bean = new KeyOperationBean();
        bean.setTargetPacket(parseTarget(properties.get("targetPacket")));
        bean.setTargetPage(parseTarget(properties.get("targetActivity")));
        bean.addKeysToNameList(parseKeys(properties.get("keys")));
        bean.addKeysToKeyToCheckList(parseKeys(properties.get("keysToCheck")));
    }

    private String parseTarget(String packet){
        return packet;
    }

    private List<String> parseKeys(String keys){
        String[] keyGroup = keys.split("|");
        List<String> keyList = new ArrayList<>();
        for(int i = 0; i < keyGroup.length; i++){
            keyList.add(keyGroup[i]);
        }

        return keyList;
    }

    private Map parseProperties(){
        Properties properties = new Properties();
        URL url = MonitorConfig.class.getResource("/config.properties");
        if(url == null){
            return null;
        }

        InputStream is = null;
        try{
            is = url.openStream();
            properties.load(is);
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
