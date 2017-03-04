package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionState;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class RequestBuilderExtension extends Request.Builder {
    private TransactionState transactionState;
    public RequestBuilderExtension(Request.Builder builder){

    }
}
