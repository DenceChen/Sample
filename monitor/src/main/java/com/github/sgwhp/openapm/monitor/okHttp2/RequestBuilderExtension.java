package com.github.sgwhp.openapm.monitor.okHttp2;

import com.github.sgwhp.openapm.monitor.TransactionState;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.net.URL;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class RequestBuilderExtension extends Request.Builder {
    private TransactionState transactionState;
    private Request.Builder mBuilder;

    public RequestBuilderExtension(Request.Builder builder){
        mBuilder = builder;

    }

    @Override
    public Request.Builder url(String url){
        return mBuilder.url(url);
    }

    @Override
    public Request.Builder url(URL url){
        return mBuilder.url(url);
    }

    @Override
    public Request.Builder header(String name, String value){
        return mBuilder.header(name, value);
    }

    @Override
    public Request.Builder addHeader(String name, String value){
        return mBuilder.addHeader(name, value);
    }

    @Override
    public Request.Builder headers(Headers headers){
        return mBuilder.headers(headers);
    }

    @Override
    public Request.Builder cacheControl(CacheControl cacheControl){
        return mBuilder.cacheControl(cacheControl);
    }

    @Override
    public Request.Builder get(){
        return mBuilder.get();
    }

    @Override
    public Request.Builder head(){
        return mBuilder.head();
    }

    @Override
    public Request.Builder post(RequestBody body){
        return mBuilder.post(body);
    }

    @Override
    public Request.Builder delete(){
        return mBuilder.delete();
    }

    @Override
    public Request.Builder put(RequestBody body){
        return mBuilder.put(body);
    }

    @Override
    public Request.Builder patch(RequestBody body){
        return mBuilder.patch(body);
    }

    @Override
    public Request.Builder method(String method, RequestBody body){
        return mBuilder.method(method, body);
    }

    @Override
    public Request.Builder tag(Object tag){
        return  mBuilder.tag(tag);
    }

    @Override
    public Request build(){
        return mBuilder.build();
    }




}
