package com.github.sgwhp.openapm.monitor;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by wuhongping on 15-12-3.
 */
public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String formatDate(){
        return sdf.format(new Date());
    }

    public static void closeInputStreamIgnoreException(InputStream is){
        if(is == null) return;
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fileToString(File file){
        FileInputStream is = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader((is = new FileInputStream(file))));
            StringBuilder sb = new StringBuilder();
            String str;
            while((str = reader.readLine()) != null){
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e){
//            e.printStackTrace();
        } finally {
            closeInputStreamIgnoreException(is);
        }
        return null;
    }

    public static Context getContextHolder(){
        try{
            Application application = (Application)Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            return application.getApplicationContext();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
