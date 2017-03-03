package com.github.sgwhp.openapm.monitor;

/**
 * Created by chenqihong on 2017/3/2.
 */

public class TransactionData {
    private long timestamp;
    private String url;
    private String httpMethod;
    private String carrier;
    private float time;
    private int statusCode;
    private int errorCode;
    private long bytesSent;
    private long bytesReceived;
    private String wanType;

    public TransactionData(String url,
                           String httpMethod,
                           String carrier,
                           float time,
                           int statusCode,
                           int errorCode,
                           long bytesSent,
                           long bytesReceived,
                           String wanType) {

        int endPos = url.indexOf('?');
        if(endPos < 0){
            endPos = url.indexOf(':');
            if(endPos < 0){
                endPos = url.length();
            }
        }

        String trimmedUrl = url.substring(0, endPos);
        this.url = trimmedUrl;
        this.httpMethod = httpMethod;
        this.carrier = carrier;
        this.time = time;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.bytesSent = bytesSent;
        this.bytesReceived = bytesReceived;
        this.wanType = wanType;

        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(long bytesSent) {
        this.bytesSent = bytesSent;
    }

    public long getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getWanType() {
        return wanType;
    }

    public void setWanType(String wanType) {
        this.wanType = wanType;
    }
}
