package com.github.sgwhp.openapm.sample.data;

/**
 * Created by chenqihong on 2017/2/10.
 */

public class KeyInfo {
    public static int KEY_OP_OPEN = 0;
    public static int KEY_OP_CLICK = 1;
    private String keyName;
    private int operationCode;
    private String time;

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


    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }
}
