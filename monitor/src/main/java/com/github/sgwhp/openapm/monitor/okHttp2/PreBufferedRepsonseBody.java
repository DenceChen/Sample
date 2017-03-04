package com.github.sgwhp.openapm.monitor.okHttp2;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Created by abby on 2017/3/4.
 */

public class PreBufferedRepsonseBody extends ResponseBody{
    private ResponseBody mResponseBody;
    private BufferedSource mSource;

    public PreBufferedRepsonseBody(ResponseBody responseBody, BufferedSource source){
        mResponseBody = responseBody;
        mSource = source;
    }
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mSource.buffer().size();
    }

    @Override
    public BufferedSource source() throws IOException {
        return mSource;
    }

    @Override
    public void close() throws IOException{
        mResponseBody.close();
    }
}
