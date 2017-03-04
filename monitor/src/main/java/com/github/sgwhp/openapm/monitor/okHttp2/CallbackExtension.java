package com.github.sgwhp.openapm.monitor.okHttp2;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class CallbackExtension implements Callback {
    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {

    }
}
