package com.github.sgwhp.openapm.monitor;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

/**
 * Created by chenqihong on 2017/3/2.
 */

public class TransactionStateUtil {
    public static final int MUURLErrorUnknown = -1;
    public static final int MUURLErrorBasURL = -1000;
    public static final int MUURLErrorTimeOut = -1001;
    public static final int MUURLErrorCannotConnectToHost = -1004;
    public static final int MUURLErrorDNSLookupFailed = -1006;
    public static final int MUURLErrorSecureConnectionFailed = -1200;
    public static void inspectAndInstrument(TransactionState transactionState, HttpURLConnection conn){
        transactionState.setUrl(conn.getURL().toString());
        transactionState.setHtttpMethod(conn.getRequestMethod());
        transactionState.setCarrier("test");
        transactionState.setWanType("WiFi");
    }
    public static void inspectAndInstrumentResponse(TransactionState transactionState, HttpURLConnection conn){
        int contentLength = conn.getContentLength();
        if(contentLength >= 0){
            transactionState.setBytesReceived(contentLength);
        }

        int statusCode = 0;
        try{
            statusCode = conn.getResponseCode();
        }catch (NullPointerException e){

        } catch (IOException e) {
            e.printStackTrace();
        }

        transactionState.setStatusCode(statusCode);
    }

    public static void setErrorCodeFromException(TransactionState transactionState, Exception e){
        if ((e instanceof UnknownHostException)) {
            transactionState.setErrorCode(MUURLErrorDNSLookupFailed);
        } else if ((e instanceof SocketTimeoutException)) {
            transactionState.setErrorCode(MUURLErrorTimeOut);
        } else if ((e instanceof ConnectException)){
            transactionState.setErrorCode(MUURLErrorCannotConnectToHost);
        } else if ((e instanceof MalformedURLException)) {
            transactionState.setErrorCode(MUURLErrorBasURL);
        } else if ((e instanceof SSLException)) {
            transactionState.setErrorCode(MUURLErrorSecureConnectionFailed);
        } else{
            transactionState.setErrorCode(MUURLErrorUnknown);
        }
    }
}
