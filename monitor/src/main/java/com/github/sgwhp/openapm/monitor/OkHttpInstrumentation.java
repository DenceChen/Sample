package com.github.sgwhp.openapm.monitor;

import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by chenqihong on 2017/2/28.
 */

public final class OkHttpInstrumentation {

    public static URLConnection openConnection(URLConnection connection){
        if((connection instanceof HttpsURLConnection)){
            return new HttpsURLConnectionExtension((HttpsURLConnection) connection);
        }

        if(null != connection){
            return new HttpURLConnectionExtension((HttpURLConnection) connection);
        }

        return null;
    }

    public static URLConnection openConnectionWithProxy(URLConnection connection){
        if((connection instanceof HttpsURLConnection)){
            return new HttpsURLConnectionExtension((HttpsURLConnection) connection);
        }

        if(null != connection){
            return new HttpURLConnectionExtension((HttpURLConnection) connection);
        }

        return null;
    }
}
