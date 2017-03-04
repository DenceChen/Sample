package com.github.sgwhp.openapm.monitor.okHttp2;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class CallExtension extends Call {
    protected CallExtension(OkHttpClient client, Request originalRequest, Call call) {
        super(client, originalRequest);
    }
}
