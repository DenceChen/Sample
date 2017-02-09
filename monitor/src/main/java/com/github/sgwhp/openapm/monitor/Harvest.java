package com.github.sgwhp.openapm.monitor;

import android.os.Environment;

import com.github.sgwhp.openapm.monitor.data.KeyInfo;
import com.github.sgwhp.openapm.monitor.data.KeyLog;
import com.github.sgwhp.openapm.monitor.data.KeyOperationBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/9.
 */

public class Harvest {
    public void logKeys(MonitorConfig config){
        List<KeyInfo> keyLogList = new ArrayList<>();
        KeyOperationHandler.getInstance().refreshDescendingIterator();
        while(KeyOperationHandler.getInstance().hasNext()){
            KeyInfo keyInfo = KeyOperationHandler.getInstance().nextDescendingElement();
            String text = keyInfo.getKeyName();
            keyLogList.add(keyInfo);
            Iterator<String> keyIterator = config.getBean().getKeyToStart().iterator();
            while(keyIterator.hasNext()){
                if(text.equals(keyIterator.next())){
                    harvest(keyLogList, config);
                    return;
                }
            }
        }
    }

    private void harvest(List<KeyInfo> keyLoglist, MonitorConfig config){
        KeyLog keyLog = new KeyLog();
        Collections.reverse(keyLoglist);
        keyLog.addList(keyLoglist);
        Iterator<KeyInfo> logIteratoor = keyLoglist.iterator();
        while(logIteratoor.hasNext()){
            KeyInfo info = logIteratoor.next();
            Iterator<String> keyIterator = config.getBean().getKeys().iterator();
            int matchedCount = 0;
            while(keyIterator.hasNext()){
                String key = keyIterator.next();
                if(key.equals(info.getKeyName())){
                    matchedCount ++;
                }
            }

            if(matchedCount == config.getBean().getKeys().size()){
                keyLog.setKeyMatched(true);
            }
        }

        //其他Plugin的检查

        endAndLog(keyLog);
    }

    private void endAndLog(KeyLog keyLog){
        String fileName = Environment.getExternalStorageDirectory() + "/monitor";
        Gson gson = new Gson();
        String log = gson.toJson(keyLog).toString();
        try{
            File file = new File(fileName);
            if(file.exists()){
                file.delete();
            }

            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = log.getBytes();
            fout.write(bytes);
            fout.close();
            KeyOperationHandler.getInstance().clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
