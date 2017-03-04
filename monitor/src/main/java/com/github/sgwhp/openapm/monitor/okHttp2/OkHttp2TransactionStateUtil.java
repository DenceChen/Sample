package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionData;
import com.github.sgwhp.openapm.monitor.TransactionState;
import com.github.sgwhp.openapm.monitor.TransactionStateUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.TreeMap;

import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by abby on 2017/3/4.
 */

public class OkHttp2TransactionStateUtil extends TransactionStateUtil{

    public OkHttp2TransactionStateUtil(){

    }

    public static void inspactAndInstrument(TransactionState transactionState, Request request){
        if(null == request){

        }else{
            inspectAndInstrument(transactionState, request.urlString(), request.method());
        }
    }

    public static Response inspectAndInstrumentResponse(TransactionState transactionState, Response response) {
        boolean statusCode = true;
        long contentLength = 0L;
        int statusCode1;
        if(response == null) {
            statusCode1 = 500;

        } else {
            statusCode1 = response.code();

            try {
                contentLength = response.body().contentLength();
            } catch (Exception var7) {

            }
        }

        inspectAndInstrumentResponse(transactionState, (int)contentLength, statusCode1);
        return addTransactionAndErrorData(transactionState, response);
    }

    private static Response addTransactionAndErrorData(TransactionState transactionState, Response response) {
        TransactionData transactionData = transactionState.end();
        if(transactionData != null) {
            //TaskQueue.queue(new HttpTransactionMeasurement(transactionData.getUrl(), transactionData.getHttpMethod(), transactionData.getStatusCode(), transactionData.getErrorCode(), transactionData.getTimestamp(), (double)transactionData.getTime(), transactionData.getBytesSent(), transactionData.getBytesReceived(), transactionData.getAppData()));
            if((long)transactionState.getStatusCode() >= 400L && response != null) {
                String contentTypeHeader = response.header("Content-Type");
                Object contentType = null;
                TreeMap params = new TreeMap();
                if(contentTypeHeader != null && contentTypeHeader.length() > 0 && !"".equals(contentTypeHeader)) {
                    params.put("content_type", contentType);
                }

                params.put("content_length", transactionState.getBytesReceived() + "");
                String responseBodyString = "";

                try {
                    final ResponseBody e = response.body();
                    responseBodyString = e.string();
                    final Buffer contents = (new Buffer()).write(responseBodyString.getBytes());
                    ResponseBody responseBody = new ResponseBody() {
                        public MediaType contentType() {
                            return e.contentType();
                        }

                        public long contentLength() {
                            return contents.size();
                        }

                        public BufferedSource source() {
                            return contents;
                        }
                    };
                    response = response.newBuilder().body(responseBody).build();
                } catch (Exception var10) {
                    if(response.message() != null) {
                        responseBodyString = response.message();
                    }
                }

                //Measurements.addHttpError(transactionData.getUrl(), transactionData.getHttpMethod(), transactionData.getStatusCode(), responseBodyString, params);
            }
        }

        return response;
    }

}
