package com.github.sgwhp.openapm.monitor;

/**
 * Created by chenqihong on 2017/3/1.
 */

public class TransactionState {
    private String url;
    private String htttpMethod;
    private int statusCode;
    private int errorCode;
    private long bytesSent;
    private long bytesReceived;
    private long startTime;
    private long endTime;
    private String carrier;
    private String wanType;
    private State state;
    private String contentType;
    private TransactionData completedData;

    public TransactionState() {
        startTime = System.currentTimeMillis();
        carrier = "unknown";
        wanType = "unknown";
        state = State.READY;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlString) {
        String url = Util.sanitizeUrl(urlString);
        if(url == null){
            return;
        }
        if(!isSent()) {
            this.url = url;
        }
    }

    public String getHtttpMethod() {
        return htttpMethod;
    }

    public void setHtttpMethod(String htttpMethod) {
        if(!isSent()) {
            this.htttpMethod = htttpMethod;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        if(!isComplete()){
            this.statusCode = statusCode;
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        if(!isComplete()) {
            this.errorCode = errorCode;
        }
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(long bytesSent) {
        if(!isComplete()) {
            this.bytesSent = bytesSent;
            this.state = State.SENT;
        }
    }

    public long getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(long bytesReceived) {
        if(!isComplete()) {
            this.bytesReceived = bytesReceived;
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        if(!isSent()) {
            this.carrier = carrier;
        }
    }

    public boolean isSent(){
        return state.ordinal() >= State.SENT.ordinal();
    }

    public boolean isComplete(){
        return state.ordinal() >= State.COMPLETE.ordinal();
    }

    public String getWanType() {
        return wanType;
    }

    public void setWanType(String wanType) {
        if(!isSent()) {
            this.wanType = wanType;
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public TransactionData end(){
        if(!isComplete()){
            this.state = State.COMPLETE;
            this.endTime = System.currentTimeMillis();
        }

        return toTransactionData();
    }

    private TransactionData toTransactionData(){
        completedData = new TransactionData(url,
                htttpMethod,
                carrier,
                (float)(this.endTime - this.startTime) / 1000.0F,
                statusCode,
                errorCode,
                bytesSent,
                bytesReceived,
                wanType);

        return completedData;
    }

    private enum State{
        READY,
        SENT,
        COMPLETE;
    }
}
