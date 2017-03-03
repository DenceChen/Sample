package com.github.sgwhp.openapm.monitor;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by chenqihong on 2017/3/2.
 */

public class Util {
    public static String sanitizeUrl(String urlString) {
        if(null == urlString){
            return null;
        }

        URL url;
        try{
            url = new URL(urlString);
        }catch (MalformedURLException e){
            return null;
        }

        StringBuffer sanitizedUrl = new StringBuffer();
        sanitizedUrl.append(url.getProtocol());
        sanitizedUrl.append("://");
        sanitizedUrl.append(url.getHost());
        if(url.getPort() != -1){
            sanitizedUrl.append(":");
            sanitizedUrl.append(url.getPort());
        }

        sanitizedUrl.append(url.getPath());
        return sanitizedUrl.toString();
    }
}
