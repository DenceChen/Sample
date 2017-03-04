package com.github.sgwhp.openapm.monitor.okHttp2;

import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by chenqihong on 2017/3/4.
 */

public class ResponseBuilderExtension extends Response.Builder {
    Response.Builder mBuilder;
    public ResponseBuilderExtension(Response.Builder builder){

        mBuilder = builder;
    }

    @Override
    public Response.Builder request(Request request){
        return mBuilder.request(request);
    }

    @Override
    public Response.Builder protocol(Protocol protocol){
        return mBuilder.protocol(protocol);
    }

    @Override
    public Response.Builder code(int code){
        return mBuilder.code(code);
    }

    @Override
    public Response.Builder message(String message){
        return mBuilder.message(message);
    }

    @Override
    public Response.Builder handshake(Handshake handshake){
        return mBuilder.handshake(handshake);
    }

    @Override
    public Response.Builder header(String name, String value){
        return mBuilder.header(name, value);
    }

    @Override
    public Response.Builder addHeader(String name, String value){
        return mBuilder.addHeader(name, value);
    }

    @Override
    public Response.Builder removeHeader(String name){
        return mBuilder.removeHeader(name);
    }

    @Override
    public Response.Builder headers(Headers headers){
        return mBuilder.headers(headers);
    }

    @Override
    public Response.Builder body(ResponseBody body){
        try {
            if (null != body) {
                BufferedSource e = body.source();
                boolean length = false;
                if (null != e) {
                    Buffer buffer = new Buffer();
                    e.readAll(buffer);
                    return mBuilder.body(new PreBufferedRepsonseBody(body, buffer));
                }
            }
        }catch (IOException e){

        }catch (IllegalStateException e1){

        }

        return mBuilder.body(body);
    }

    @Override
    public Response.Builder networkResponse(Response networkResponse){
        return mBuilder.networkResponse(networkResponse);
    }

    @Override
    public Response.Builder cacheResponse(Response cacheReponse){
        return mBuilder.cacheResponse(cacheReponse);
    }

    @Override
    public Response.Builder priorResponse(Response priorResponse){
        return mBuilder.priorResponse(priorResponse);
    }

    @Override
    public Response build(){
        return mBuilder.build();
    }
}
