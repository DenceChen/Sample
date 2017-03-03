package com.github.sgwhp.openapm.monitor.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/3/1.
 */

public class StreamCompleteListenerManager {
    private boolean isStreamComplete = false;
    private ArrayList<StreamCompleteListener> mStreamCompleteListeners = new ArrayList<>();

    public boolean isComplete(){
        synchronized (this) {
            return isStreamComplete;
        }
    }

    public void addStreamCompleteListener(StreamCompleteListener streamCompleteListener){
        synchronized (mStreamCompleteListeners){
            mStreamCompleteListeners.add(streamCompleteListener);
        }
    }

    public void removeStreamCompleteListener(StreamCompleteListener streamCompleteListener){
        synchronized (mStreamCompleteListeners){
            mStreamCompleteListeners.remove(streamCompleteListener);
        }
    }

    public void notifyStreamComplete(StreamCompleteEvent ev){
        if(!checkComplete()){
            for(StreamCompleteListener listener:getStreamCompleteListeners()){
                listener.streamComplete(ev);
            }
        }
    }

    public void notifyStreamError(StreamCompleteEvent ev){
        if(!checkComplete()){
            for(StreamCompleteListener listener: getStreamCompleteListeners()){
                listener.streamError(ev);
            }
        }
    }

    private boolean checkComplete(){
        boolean streamComplete;
        synchronized (this){
            streamComplete = isComplete();
            if(!streamComplete) {
                this.isStreamComplete = true;
            }
        }

        return streamComplete;
    }

    private List<StreamCompleteListener> getStreamCompleteListeners(){
        ArrayList listeners;
        synchronized (mStreamCompleteListeners){
            listeners = new ArrayList(mStreamCompleteListeners);
            mStreamCompleteListeners.clear();
        }

        return listeners;
    }
}
