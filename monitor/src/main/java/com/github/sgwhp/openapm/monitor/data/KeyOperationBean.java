package com.github.sgwhp.openapm.monitor.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class KeyOperationBean {
    private String targetPacket;
    private String targetPage;
    private List<String> keyNameList = new ArrayList<>();
    private List<String> keyToCheckList = new ArrayList<>();

    public String getTargetPacket() {
        return targetPacket;
    }

    public void setTargetPacket(String targetPacket) {
        this.targetPacket = targetPacket;
    }

    public String getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    public List<String> getKeyNameList() {
        return keyNameList;
    }

    public void setKeyNameList(List<String> keyNameList) {
        this.keyNameList = keyNameList;
    }

    public List<String> getKeyToCheckList() {
        return keyToCheckList;
    }

    public void setKeyToCheckList(List<String> keyToCheckList) {
        this.keyToCheckList = keyToCheckList;
    }

    public void addKeysToNameList(List<String> keys){
        keyNameList.addAll(keys);
    }

    public void addKeysToKeyToCheckList(List<String> keys){
        keyToCheckList.addAll(keys);
    }
}
