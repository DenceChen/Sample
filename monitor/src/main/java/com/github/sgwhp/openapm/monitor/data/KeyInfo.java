package com.github.sgwhp.openapm.monitor.data;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class KeyInfo {
    private String keyName;
    private String time;
    private boolean isEmulator;
    private boolean hasHooked;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
