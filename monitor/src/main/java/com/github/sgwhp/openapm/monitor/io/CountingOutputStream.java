package com.github.sgwhp.openapm.monitor.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by chenqihong on 2017/3/1.
 */

public class CountingOutputStream extends OutputStream implements StreamCompleteListenerSource{

    private final OutputStream mOutputStream;
    private long count = 0L;
    private final StreamCompleteListenerManager mListenerManager = new StreamCompleteListenerManager();

    public CountingOutputStream(OutputStream outputStream){
        mOutputStream = outputStream;
    }

    @Override
    public void write(int oneByte) throws IOException {
        try{
            mOutputStream.write(oneByte);
            count += 1L;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        try{
            mOutputStream.write(buffer);
            count += buffer.length;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException{
        try{
            mOutputStream.write(buffer, offset, count);
            this.count += count;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void flush() throws IOException{
        try{
            mOutputStream.flush();
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void close() throws IOException{
        try{
            mOutputStream.close();;
            notifyStreamComplete();
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void addStreamCompleteListener(StreamCompleteListener paramStreamCompleteListener) {
        mListenerManager.addStreamCompleteListener(paramStreamCompleteListener);

    }

    @Override
    public void removeStreamCompleteListener(StreamCompleteListener paramStreamCompleteListener) {
        mListenerManager.removeStreamCompleteListener(paramStreamCompleteListener);

    }

    public long getCount(){
        return count;
    }

    private void notifyStreamComplete(){
        if(! mListenerManager.isComplete()){
            mListenerManager.notifyStreamComplete(new StreamCompleteEvent(this, this.count));
        }
    }

    private void notifyStreamError(Exception e){
        if(! mListenerManager.isComplete()){
            mListenerManager.notifyStreamError(new StreamCompleteEvent(this, this.count, e));
        }
    }
}
