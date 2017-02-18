package com.github.sgwhp.openapm.sample.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/10.
 */

public class LogBean {
    private List<KeyInfo> keyInfoList = new ArrayList<>();
    private boolean keyMatched;
    private boolean isEmulator;
    private boolean hasHooked;

    public List<KeyInfo> getKeyInfoList() {
        return keyInfoList;
    }

    public void setKeyInfoList(List<KeyInfo> keyInfoList) {
        this.keyInfoList = keyInfoList;
    }

    public void addList(List<KeyInfo> list){
        keyInfoList.addAll(list);
    }

    public boolean isKeyMatched() {
        return keyMatched;
    }

    public void setKeyMatched(boolean keyMatched) {
        this.keyMatched = keyMatched;
    }

    public boolean isEmulator() {
        return isEmulator;
    }

    public void setEmulator(boolean emulator) {
        isEmulator = emulator;
    }

    public boolean isHasHooked() {
        return hasHooked;
    }

    public void setHasHooked(boolean hasHooked) {
        this.hasHooked = hasHooked;
    }
}
