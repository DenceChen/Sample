package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionState;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Callback;

import java.io.IOException;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class CallExtension extends Call {
    private TransactionState mTransactionState;
    private OkHttpClient mClient;
    private Request mRequest;
    private Call mCall;
    protected CallExtension(OkHttpClient client, Request request, Call call) {
        super(client, request);
        mClient = client;
        mRequest = request;
        mCall = call;
    }

    @Override
    public Response execute() throws IOException{
        getTransactionState();
        Response response = null;

        try{
            response = mCall.execute();
        }catch (IOException e){
            error(e);
            throw e;
        }

        return checkResponse(response);
    }

    @Override
    public void enqueue(Callback responseCallback){
        getTransactionState();
        mCall.enqueue(new CallbackExtension(responseCallback, mTransactionState));
    }

    @Override
    public void cancel(){
        mCall.cancel();
    }

    @Override
    public boolean isCanceled(){
        return  mCall.isCanceled();
    }

    private TransactionState getTransactionState(){
        if(null == mTransactionState){
            mTransactionState = new TransactionState();

        }

        return mTransactionState;
    }

    private void error(Exception e){

    }

    private Response checkResponse(Response response){
        return response;
    }
}
