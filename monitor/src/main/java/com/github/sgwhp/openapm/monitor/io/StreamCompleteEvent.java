package com.github.sgwhp.openapm.monitor.io;

import java.util.EventObject;

/**
 * Created by chenqihong on 2017/3/1.
 */

public class StreamCompleteEvent extends EventObject{
    private final long mBytes;
    private final Exception mException;
    public StreamCompleteEvent(Object source, long bytes, Exception exception) {
        super(source);
        mBytes = bytes;
        mException = exception;
    }

    public StreamCompleteEvent(Object source, long bytes){
        this(source, bytes, null);
    }

    public long getBytes() {
        return mBytes;
    }

    public Exception getException() {
        return mException;
    }

    public boolean isError(){
        return mException != null;
    }
}
