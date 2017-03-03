package com.github.sgwhp.openapm.monitor.io;

/**
 * Created by chenqihong on 2017/3/1.
 */

public  interface StreamCompleteListener {
    void streamComplete(StreamCompleteEvent paramStreamCompleteEvent);

    void streamError(StreamCompleteEvent paramStreamCompleteEvent);
}
