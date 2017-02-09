package com.github.sgwhp.openapm.monitor.data;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class KeyInfo {
    public static int KEY_OP_OPEN = 0;
    public static int KEY_OP_CLICK = 1;
    private String keyName;
    private int operationCode;
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

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }
}
