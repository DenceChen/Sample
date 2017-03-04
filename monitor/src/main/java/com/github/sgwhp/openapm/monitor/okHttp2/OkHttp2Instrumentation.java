package com.github.sgwhp.openapm.monitor.okHttp2;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.net.HttpURLConnection;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class OkHttp2Instrumentation {

    private OkHttp2Instrumentation(){

    }

    public static Request build(Request.Builder builder){
        return (new RequestBuilderExtension(builder)).build();
    }

    public static Call newCall(OkHttpClient client, Request request){
        return new CallExtension(client, request, client.newCall(request));
    }

    public static Response.Builder body(Response.Builder builder){
        return new ResponseBuilderExtension(builder);
    }
}
