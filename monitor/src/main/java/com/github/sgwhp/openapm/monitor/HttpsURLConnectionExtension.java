package com.github.sgwhp.openapm.monitor;

import com.mucfc.monitor.io.CountingInputStream;
import com.mucfc.monitor.io.CountingOutputStream;
import com.mucfc.monitor.io.StreamCompleteEvent;
import com.mucfc.monitor.io.StreamCompleteListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by chenqihong on 2017/2/28.
 */

public class HttpsURLConnectionExtension extends HttpsURLConnection {

    private HttpsURLConnection mConnection;
    private TransactionState mTransactionState;


    protected HttpsURLConnectionExtension(HttpsURLConnection connection) {
        super(connection.getURL());
        mConnection = connection;
    }

    @Override
    public String getCipherSuite() {
        return mConnection.getCipherSuite();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return mConnection.getLocalCertificates();
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        try {
            return mConnection.getServerCertificates();
        }catch (SSLPeerUnverifiedException e){
            error(e);
            throw e;
        }
    }

    @Override
    public void addRequestProperty(String field, String newValue){
        mConnection.addRequestProperty(field, newValue);
    }

    @Override
    public boolean usingProxy(){
        return mConnection.usingProxy();
    }

    @Override
    public String getContentEncoding(){
        getTransactionState();
        String contentEncoding = mConnection.getContentEncoding();
        checkResponse();
        return contentEncoding;
    }

    @Override
    public int getContentLength(){
        getTransactionState();
        int contentLength = mConnection.getContentLength();
        checkResponse();
        return contentLength;
    }

    @Override
    public String getContentType(){
        getTransactionState();
        String contentType = mConnection.getContentType();
        checkResponse();
        return contentType;
    }

    @Override
    public long getDate(){
        getTransactionState();
        long date = mConnection.getDate();
        checkResponse();
        return date;
    }

    @Override
    public InputStream getErrorStream(){
        getTransactionState();
        CountingInputStream in;
        try{
            in = new CountingInputStream(mConnection.getErrorStream(), true);
        }catch (Exception e){
            return mConnection.getErrorStream();
        }

        return in;
    }

    @Override
    public long getHeaderFieldDate(String field, long defaultValue){
        getTransactionState();
        long date = mConnection.getHeaderFieldDate(field, defaultValue);
        checkResponse();
        return date;
    }

    @Override
    public boolean getInstanceFollowRedirects(){
        return mConnection.getInstanceFollowRedirects();
    }

    @Override
    public Permission getPermission() throws IOException{
        return mConnection.getPermission();
    }

    @Override
    public String getRequestMethod(){
        return mConnection.getRequestMethod();
    }

    @Override
    public int getResponseCode() throws IOException{
        getTransactionState();
        int responseCode;
        try{
            responseCode = mConnection.getResponseCode();
        }catch (IOException e){
            error(e);
            throw e;
        }

        checkResponse();
        return responseCode;
    }

    @Override
    public String getResponseMessage() throws IOException {
        getTransactionState();
        String message;
        try{
            message = mConnection.getResponseMessage();
        }catch (IOException e){
            error(e);
            throw e;
        }

        checkResponse();
        return message;
    }

    @Override
    public void setChunkedStreamingMode(int chunkLength){
        mConnection.setChunkedStreamingMode(chunkLength);
    }

    @Override
    public void setFixedLengthStreamingMode(int contentLength){
        mConnection.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setInstanceFollowRedirects(boolean followRedirects){
        mConnection.setInstanceFollowRedirects(followRedirects);
    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException{
        try{
            mConnection.setRequestMethod(method);
        }catch (ProtocolException e){
            error(e);
            throw e;
        }
    }

    @Override
    public boolean getDefaultUseCaches(){
        return mConnection.getDefaultUseCaches();
    }

    @Override
    public boolean getDoInput(){
        return mConnection.getDoInput();
    }

    @Override
    public boolean getDoOutput(){
        return mConnection.getDoOutput();
    }

    @Override
    public long getExpiration(){
        getTransactionState();
        long expiration = mConnection.getExpiration();
        checkResponse();
        return expiration;
    }

    @Override
    public String getHeaderField(int pos){
        getTransactionState();
        String header = mConnection.getHeaderField(pos);
        checkResponse();
        return header;
    }

    @Override
    public String getHeaderField(String key){
        getTransactionState();
        String header = mConnection.getHeaderField(key);
        checkResponse();
        return header;
    }

    @Override
    public int getHeaderFieldInt(String field, int defaultValue){
        getTransactionState();
        int header = mConnection.getHeaderFieldInt(field, defaultValue);
        checkResponse();
        return header;
    }

    @Override
    public String getHeaderFieldKey(int posn){
        getTransactionState();
        String key = mConnection.getHeaderFieldKey(posn);
        checkResponse();
        return key;
    }

    @Override
    public Map<String, List<String>> getHeaderFields(){
        getTransactionState();
        Map fields = mConnection.getHeaderFields();
        checkResponse();
        return fields;
    }

    @Override
    public long getIfModifiedSince(){
        getTransactionState();
        long ifModifedSince = mConnection.getIfModifiedSince();
        checkResponse();
        return ifModifedSince;
    }

    @Override
    public InputStream getInputStream() throws IOException{
        final TransactionState transactionState = getTransactionState();
        CountingInputStream in;
        try{
            in = new CountingInputStream(mConnection.getInputStream());
            TransactionStateUtil.inspectAndInstrumentResponse(transactionState, mConnection);
        }catch (IOException e){
            error(e);
            throw e;
        }

        in.addStreamCompleteListener(new StreamCompleteListener() {
            @Override
            public void streamComplete(StreamCompleteEvent paramStreamCompleteEvent) {
                long contentLength = mConnection.getContentLength();
                long numBytes = paramStreamCompleteEvent.getBytes();
                if(contentLength >= 0L){
                    numBytes = contentLength;
                }

                transactionState.setBytesReceived(numBytes);
                addTransactionAndErrorData(transactionState);
            }

            @Override
            public void streamError(StreamCompleteEvent paramStreamCompleteEvent) {
                if(!transactionState.isComplete()){
                    transactionState.setBytesReceived(paramStreamCompleteEvent.getBytes());
                }

                error(paramStreamCompleteEvent.getException());
            }
        });

        return in;
    }

    @Override
    public long getLastModified(){
        getTransactionState();
        long lastModified = mConnection.getLastModified();
        checkResponse();
        return lastModified;
    }

    @Override
    public OutputStream getOutputStream() throws IOException{
        final TransactionState transactionState = getTransactionState();
        CountingOutputStream out;
        try{
            out = new CountingOutputStream(mConnection.getOutputStream());
        }catch (IOException e){
            error(e);
            throw e;
        }

        out.addStreamCompleteListener(new StreamCompleteListener() {
            @Override
            public void streamComplete(StreamCompleteEvent paramStreamCompleteEvent) {
                String header = mConnection.getRequestProperty("content-length");
                long numBytes = paramStreamCompleteEvent.getBytes();
                if(header != null){
                    try {
                        numBytes = Long.parseLong(header);
                    }catch (NumberFormatException ex){

                    }
                }

                transactionState.setBytesSent(numBytes);
                addTransactionAndErrorData(transactionState);
            }

            @Override
            public void streamError(StreamCompleteEvent paramStreamCompleteEvent) {
                if(!transactionState.isComplete()){
                    transactionState.setBytesSent(paramStreamCompleteEvent.getBytes());
                }

                error(paramStreamCompleteEvent.getException());
            }
        });

        return out;
    }

    @Override
    public int getReadTimeout(){
        return mConnection.getReadTimeout();
    }

    @Override
    public Map<String, List<String>>getRequestProperties(){
        return mConnection.getRequestProperties();
    }

    @Override
    public String getRequestProperty(String field){
        return mConnection.getRequestProperty(field);
    }

    @Override
    public URL getURL(){
        return mConnection.getURL();
    }

    @Override
    public boolean getUseCaches(){
        return mConnection.getUseCaches();
    }

    @Override
    public void setAllowUserInteraction(boolean newValue){
        mConnection.setAllowUserInteraction(newValue);
    }

    @Override
    public void setConnectTimeout(int timeoutMillis){
        mConnection.setConnectTimeout(timeoutMillis);
    }

    @Override
    public void setDefaultUseCaches(boolean newValue){
        mConnection.setDefaultUseCaches(newValue);
    }

    @Override
    public void setDoInput(boolean newValue){
        mConnection.setDoInput(newValue);
    }

    @Override
    public void setDoOutput(boolean newValue){
        mConnection.setDoOutput(newValue);
    }

    @Override
    public void setIfModifiedSince(long newValue){
        mConnection.setIfModifiedSince(newValue);
    }

    @Override
    public void setReadTimeout(int timeoutMillis){
        mConnection.setReadTimeout(timeoutMillis);
    }

    @Override
    public void setRequestProperty(String field, String newValue){
        mConnection.setRequestProperty(field, newValue);
    }

    @Override
    public void setUseCaches(boolean newValue){
        mConnection.setUseCaches(newValue);
    }

    @Override
    public String toString(){
        return mConnection.toString();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException{
        return mConnection.getPeerPrincipal();
    }

    @Override
    public Principal getLocalPrincipal(){
        return mConnection.getLocalPrincipal();
    }

    @Override
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier){
        mConnection.setHostnameVerifier(hostnameVerifier);
    }

    @Override
    public HostnameVerifier getHostnameVerifier(){
        return mConnection.getHostnameVerifier();
    }

    @Override
    public void setSSLSocketFactory(SSLSocketFactory sf){
        mConnection.setSSLSocketFactory(sf);
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory(){
        return mConnection.getSSLSocketFactory();
    }

    @Override
    public void connect() throws IOException{
        getTransactionState();
        try {
            mConnection.connect();
        }catch (IOException e){
            error(e);
            throw e;
        }
    }

    @Override
    public boolean getAllowUserInteraction(){
        return mConnection.getAllowUserInteraction();
    }

    @Override
    public int getConnectTimeout(){
        return mConnection.getConnectTimeout();
    }

    @Override
    public Object getContent() throws IOException{
        getTransactionState();
        Object object;
        try{
            object = mConnection.getContent();
        }catch (IOException e){
            error(e);
            throw  e;
        }

        int contentLength = mConnection.getContentLength();
        if(contentLength >= 0){
            TransactionState transactionState = getTransactionState();
            if(!transactionState.isComplete()){
                transactionState.setBytesReceived(contentLength);
                addTransactionAndErrorData(transactionState);
            }
        }

        return object;
    }

    @Override
    public Object getContent(Class[] types) throws IOException{
        getTransactionState();
        Object object;
        try{
            object = mConnection.getContent();
        }catch (IOException e){
            error(e);
            throw  e;
        }

        checkResponse();
        return object;
    }

    @Override
    public void disconnect() {
        if(mTransactionState != null && ! mTransactionState.isComplete()){
            addTransactionAndErrorData(mTransactionState);
        }
        mConnection.disconnect();
    }

    private void checkResponse(){
        if(!getTransactionState().isComplete()){
            TransactionStateUtil.inspectAndInstrumentResponse(getTransactionState(), mConnection);
        }

    }

    private TransactionState getTransactionState(){
        if(mTransactionState == null){
            mTransactionState = new TransactionState();
            TransactionStateUtil.inspectAndInstrument(mTransactionState, mConnection);
        }

        return mTransactionState;
    }

    private void error(Exception e){
        TransactionState transactionState = getTransactionState();
        TransactionStateUtil.setErrorCodeFromException(transactionState, e);
        if(!transactionState.isComplete()){
            TransactionStateUtil.inspectAndInstrumentResponse(transactionState, mConnection);
            TransactionData transactionData = transactionState.end();

            if(transactionData != null){
                //queue
            }
        }
    }

    private void addTransactionAndErrorData(TransactionState transactionState){
        TransactionData transactionData = transactionState.end();

        if(transactionData == null){
            return;
        }

        //queue

        if(transactionState.getStatusCode() >= 400L){
            StringBuilder responseBody = new StringBuilder();
            try{
                InputStream errorStream = getErrorStream();
                if(errorStream instanceof CountingInputStream){
                    responseBody.append(((CountingInputStream)errorStream).getBufferAsString());
                }
            }catch (Exception e){

            }

            Map params = new TreeMap();
            String contentType = mConnection.getContentType();
            if(contentType != null && !("".equals(contentType))){
                params.put("content_type", contentType);
            }

            params.put("content_length", transactionState.getBytesReceived() + "");

            //addHttpError
        }
    }
}
