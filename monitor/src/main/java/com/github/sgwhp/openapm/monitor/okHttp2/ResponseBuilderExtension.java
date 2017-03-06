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

    }

    @Override
    public Response.Builder request(Request request){
        return super.request(request);
    }

    @Override
    public Response.Builder protocol(Protocol protocol){
        return super.protocol(protocol);
    }

    @Override
    public Response.Builder code(int code){
        return super.code(code);
    }

    @Override
    public Response.Builder message(String message){
        return super.message(message);
    }

    @Override
    public Response.Builder handshake(Handshake handshake){
        return super.handshake(handshake);
    }

    @Override
    public Response.Builder header(String name, String value){
        return super.header(name, value);
    }

    @Override
    public Response.Builder addHeader(String name, String value){
        return super.addHeader(name, value);
    }

    @Override
    public Response.Builder removeHeader(String name){
        return super.removeHeader(name);
    }

    @Override
    public Response.Builder headers(Headers headers){
        return super.headers(headers);
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
                    return super.body(new PreBufferedRepsonseBody(body, buffer));
                }
            }
        }catch (IOException e){

        }catch (IllegalStateException e1){

        }

        return super.body(body);
    }

    @Override
    public Response.Builder networkResponse(Response networkResponse){
        return super.networkResponse(networkResponse);
    }

    @Override
    public Response.Builder cacheResponse(Response cacheReponse){
        return super.cacheResponse(cacheReponse);
    }

    @Override
    public Response.Builder priorResponse(Response priorResponse){
        return super.priorResponse(priorResponse);
    }

    @Override
    public Response build(){
        return super.build();
    }
}
