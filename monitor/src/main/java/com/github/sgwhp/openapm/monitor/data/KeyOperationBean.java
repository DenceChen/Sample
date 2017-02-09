package com.github.sgwhp.openapm.monitor.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class KeyOperationBean {
    private List<String> keyToStart = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private List<String> keyToFinished = new ArrayList<>();

    public List<String> getKeyToStart() {
        return keyToStart;
    }

    public void setKeyToStart(List<String> keyToStart) {
        this.keyToStart = keyToStart;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getKeyToFinished() {
        return keyToFinished;
    }

    public void setKeyToFinished(List<String> keyToFinished) {
        this.keyToFinished = keyToFinished;
    }

    public void addKeyToStart(List<String> list){
        keyToStart.addAll(list);
    }

    public void addKeyToFinished(List<String> list){
        keyToFinished.addAll(list);
    }

    public void addKeys(List<String> list){
        keys.addAll(list);
    }
}
