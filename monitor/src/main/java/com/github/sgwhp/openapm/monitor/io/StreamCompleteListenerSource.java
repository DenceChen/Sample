package com.github.sgwhp.openapm.monitor.io;

/**
 * Created by chenqihong on 2017/3/1.
 */

public interface StreamCompleteListenerSource {
    void addStreamCompleteListener(StreamCompleteListener paramStreamCompleteListener);

    void removeStreamCompleteListener(StreamCompleteListener paramStreamCompleteListener);
}
