package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionData;
import com.github.sgwhp.openapm.monitor.TransactionState;
import com.github.sgwhp.openapm.monitor.TransactionStateUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class CallbackExtension implements Callback {

    private TransactionState mTransactionState;
    private Callback mCallback;

    public CallbackExtension(Callback callback, TransactionState transactionState){
        mCallback = callback;
        mTransactionState = transactionState;
    }
    @Override
    public void onFailure(Request request, IOException e) {
        error(e);
        mCallback.onFailure(request, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {

        response = checkResponse(response);
        mCallback.onResponse(response);
    }

    private Response checkResponse(Response response){
        if(! getTransactionState().isComplete()){
            response = OkHttp2TransactionStateUtil.
                    inspectAndInstrumentResponse(getTransactionState(), response);
        }
        return response;
    }

    private TransactionState getTransactionState(){
        return mTransactionState;
    }

    private void error(Exception e){
        TransactionState transactionState = getTransactionState();
        TransactionStateUtil.setErrorCodeFromException(transactionState, e);
        if(!transactionState.isComplete()){
            TransactionData transactionData = transactionState.end();
            if(transactionData != null){
                //TaskQueue
            }
        }

    }
}
