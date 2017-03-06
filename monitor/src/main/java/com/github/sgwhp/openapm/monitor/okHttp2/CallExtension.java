package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionData;
import com.github.sgwhp.openapm.monitor.TransactionState;
import com.github.sgwhp.openapm.monitor.TransactionStateUtil;
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
    private Request mRequest;
    private Call mCall;
    protected CallExtension(OkHttpClient client, Request request, Call call) {
        super(client, request);
        mRequest = request;
    }

    @Override
    public Response execute() throws IOException{
        getTransactionState();
        Response response = null;

        try{
            response = super.execute();
        }catch (IOException e){
            error(e);
            throw e;
        }

        return checkResponse(response);
    }

    @Override
    public void enqueue(Callback responseCallback){
        getTransactionState();
        super.enqueue(new CallbackExtension(responseCallback, mTransactionState));
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
            OkHttp2TransactionStateUtil.inspactAndInstrument(mTransactionState, mRequest);

        }

        return mTransactionState;
    }

    private void error(Exception e){
        TransactionState transactionState = this.getTransactionState();
        TransactionStateUtil.setErrorCodeFromException(transactionState, e);
        if(!transactionState.isComplete()) {
            TransactionData transactionData = transactionState.end();
            if(transactionData != null) {
                //TaskQueue.queue(new HttpTransactionMeasurement(transactionData.getUrl(), transactionData.getHttpMethod(), transactionData.getStatusCode(), transactionData.getErrorCode(), transactionData.getTimestamp(), (double)transactionData.getTime(), transactionData.getBytesSent(), transactionData.getBytesReceived(), transactionData.getAppData()));
            }
        }


    }

    private Response checkResponse(Response response){
        if(getTransactionState().isComplete()){
            response = OkHttp2TransactionStateUtil.
                    inspectAndInstrumentResponse(getTransactionState(), response);
        }

        return response;
    }
}
