package com.github.sgwhp.openapm.monitor.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by chenqihong on 2017/3/1.
 */

public final class CountingInputStream extends InputStream implements StreamCompleteListenerSource{
    public static final int DEFAULT_RESPONSE_LIMIT = 2048;
    private final InputStream mInputStream;
    private long count = 0L;
    private final StreamCompleteListenerManager mListenerManager = new StreamCompleteListenerManager();
    private final ByteBuffer mBuffer;
    private boolean enableBuffering;

    public CountingInputStream(InputStream inputStream){
        mInputStream = inputStream;

        if(enableBuffering){
            mBuffer = ByteBuffer.allocate(DEFAULT_RESPONSE_LIMIT);
            fillBuffer();
        }else{
            this.mBuffer = null;
        }
    }

    public CountingInputStream(InputStream inputStream, boolean enableBuffering){
        mInputStream = inputStream;
        enableBuffering = enableBuffering;

        if(enableBuffering){
            mBuffer = ByteBuffer.allocate(DEFAULT_RESPONSE_LIMIT);
            fillBuffer();
        }else{
            this.mBuffer = null;
        }
    }

    public void addStreamCompleteListener(StreamCompleteListener streamCompleteListener){
        mListenerManager.addStreamCompleteListener(streamCompleteListener);
    }

    public void removeStreamCompleteListener(StreamCompleteListener streamCompleteListener){
        mListenerManager.removeStreamCompleteListener(streamCompleteListener);
    }


    @Override
    public int read() throws IOException {
        /**
         * 如果有buffer，直接从buffer中读取；
         */
        if(enableBuffering){
            synchronized (mBuffer){
                if(bufferHasBytes(1L)){

                    int n = readBuffer();
                    if(n >= 0){
                        count += 1L;
                    }

                    return n;
                }
            }
        }

        /**
         * 没有buffer从Stream中读取
         */
        try{
            int n = mInputStream.read();
            if(n >= 0){
                count += 1L;
            }else{
                notifyStreamComplete();
            }
            return n;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public int read(byte[] b) throws IOException{
        int n = 0;
        int numBytesFromBuffer = 0;
        int inputBufferRemaining = b.length;

        if(enableBuffering){
            synchronized (mBuffer){
                if(bufferHasBytes(inputBufferRemaining)){
                    n = readBufferBytes(b);
                    if(n >= 0){
                        this.count += n;
                    }else{
                        throw new IOException("读取Buffer数据错误");
                    }

                    return n;
                }

                int remaining = mBuffer.remaining();
                if(remaining > 0){
                    numBytesFromBuffer = readBufferBytes(b, 0, remaining);
                    if(numBytesFromBuffer < 0){
                        throw new IOException("读取部分数据时发生错误");
                    }

                    inputBufferRemaining -= numBytesFromBuffer;
                    count += numBytesFromBuffer;
                }
            }
        }

        try{
            n = mInputStream.read(b, numBytesFromBuffer, inputBufferRemaining);
            if(n >= 0){
                count += n;
                return  n + numBytesFromBuffer;
            }

            if(numBytesFromBuffer <= 0){
                notifyStreamComplete();
                return n;
            }

            return numBytesFromBuffer;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public int read(byte[] b, int off, int len)throws IOException{
        int n = 0;
        int numBytesFromBuffer = 0;
        int inputBufferRemaining = len;

        if(enableBuffering){
            synchronized (mBuffer){
                if(bufferHasBytes(inputBufferRemaining)){
                    n = readBufferBytes(b, off, len);
                    if(n >= 0){
                        count += n;
                    }else {
                         throw new IOException("读取Buffer数据失败");
                    }

                    return n;
                }

                int remaining = mBuffer.remaining();
                if(remaining > 0){
                    numBytesFromBuffer = readBufferBytes(b, off, remaining);
                    if(numBytesFromBuffer < 0){
                        throw new IOException("读取部分数据时发生错误");
                    }

                    inputBufferRemaining -= numBytesFromBuffer;
                    count += numBytesFromBuffer;
                }
            }
        }

        try {
            n = mInputStream.read(b, off + numBytesFromBuffer, inputBufferRemaining);
            if(n >= 0){
                count += n;
                return n + numBytesFromBuffer;
            }

            if(numBytesFromBuffer <= 0){
                notifyStreamComplete();;
                return n;
            }

            return numBytesFromBuffer;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public long skip(long byteCount) throws IOException{
        long toSkip = byteCount;
        if(enableBuffering){
            synchronized (mBuffer){
                if(bufferHasBytes(byteCount)){
                    mBuffer.position((int) byteCount);
                    count += byteCount;
                    return byteCount;
                }

                toSkip = byteCount - mBuffer.remaining();
                if(toSkip > 0L){
                    mBuffer.position(mBuffer.remaining());
                }else{
                    throw new IOException("读取部分数据（skip）时发生错误");
                }
            }
        }

        try{
            long n = mInputStream.skip(toSkip);
            count += n;
            return n;
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public int available() throws IOException{
        int remaining = 0;

        if(enableBuffering){
            remaining = mBuffer.remaining();
        }

        try{
            return remaining + mInputStream.available();
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void close() throws IOException{
        try{
            mInputStream.close();
            notifyStreamComplete();
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    @Override
    public void mark(int readlimit){
        if(!markSupported()) {
            return;
        }
        mInputStream.mark(readlimit);
    }

    @Override
    public boolean markSupported(){
        return mInputStream.markSupported();
    }

    @Override
    public void reset() throws IOException{
        if(!markSupported()){
            return;
        }

        try{
            mInputStream.reset();
        }catch (IOException e){
            notifyStreamError(e);
            throw e;
        }
    }

    public void fillBuffer(){
        if(mBuffer != null){
            if(!mBuffer.hasArray()){
                return;
            }

            synchronized (mBuffer){
                int bytesRead = 0;
                try{
                    bytesRead = mInputStream.read(mBuffer.array(), 0, mBuffer.capacity());
                }catch (IOException e){

                }

                if(bytesRead <= 0){
                    mBuffer.limit(0);
                }else if(bytesRead < mBuffer.capacity()){
                    mBuffer.limit(bytesRead);
                }
            }
        }
    }

    public String getBufferAsString(){
        if(mBuffer != null){
            synchronized (mBuffer){
                byte[] buf = new byte[mBuffer.limit()];
                for (int i = 0; i < mBuffer.limit(); i++){
                    buf[i] = mBuffer.get(i);
                }

                return new String(buf);
            }
        }

        return "";
    }

    private void notifyStreamComplete(){
        if(!mListenerManager.isComplete()){
            mListenerManager.notifyStreamComplete(new StreamCompleteEvent(this, count));
        }
    }

    private void notifyStreamError(Exception e){
        if(!mListenerManager.isComplete()){
            mListenerManager.notifyStreamError(new StreamCompleteEvent(this, count, e));
        }
    }

    private boolean bufferHasBytes(long num){
        return mBuffer.remaining() >= num;
    }

    private boolean bufferEmpty(){
        if(mBuffer.hasRemaining()){
            return false;
        }

        return true;
    }

    private int readBuffer(){
        if(bufferEmpty()){
            return -1;
        }

        return mBuffer.get();
    }

    private int readBufferBytes(byte[] bytes){
        return readBufferBytes(bytes, 0, bytes.length);
    }

    private int readBufferBytes(byte[] bytes, int offset, int length){
        if(bufferEmpty()){
            return  -1;
        }

        int remainingBefore = mBuffer.remaining();
        mBuffer.get(bytes, offset, length);
        return remainingBefore - mBuffer.remaining();
    }
}
