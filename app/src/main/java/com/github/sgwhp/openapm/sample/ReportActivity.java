package com.github.sgwhp.openapm.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.github.sgwhp.openapm.sample.data.KeyInfo;
import com.github.sgwhp.openapm.sample.data.LogBean;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenqihong on 2017/2/10.
 */

public class ReportActivity extends Activity {
    private TextView mReportText;
    private LogBean mKeyLog;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mReportText = (TextView)findViewById(R.id.report);
        readFromLog();
        if(null != mKeyLog) {
            mReportText.setText(showLog());
        }
    }

    private String showLog(){
        StringBuilder sb = new StringBuilder();
        List<KeyInfo> list = mKeyLog.getKeyInfoList();
        Iterator<KeyInfo> i = list.iterator();
        sb.append("事件链：");
        long startTime = 0;
        if(i.hasNext()){
            KeyInfo keyInfo = i.next();
            sb.append(keyInfo.getKeyName());
            startTime = Long.parseLong(keyInfo.getTime());
        }

        while(i.hasNext()){
            sb.append("->");
            KeyInfo keyInfo = i.next();
            sb.append(keyInfo.getKeyName());
            if(!(i.hasNext())){
                sb.append("\n\n").append("耗时： ").append((Long.parseLong(keyInfo.getTime()) - startTime)/1000 + "秒");
                sb.append("\n").append("操作合规：").append(mKeyLog.isKeyMatched() == true? "是" : "否");
                sb.append("\n").append("模拟器：").append(keyInfo.isEmulator()== true? "是" : "否");
                sb.append("\n").append("附着：").append(keyInfo.isHasHooked() == true? "是" : "否");
            }
        }

        return sb.toString();
    }

    private void readFromLog() {
        String path = Environment.getExternalStorageDirectory() + "/monitor";

        Gson gson = new Gson();
        String res = readFile(path);
        if(null == res){
            return;
        }

        mKeyLog = gson.fromJson(res, LogBean.class);

    }

    private String readFile(String filepath) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(filepath);

           int length = fin.available();

            byte[] buffer = new byte[length];
            fin.read(buffer);

            res = new String(buffer, "UTF-8");

            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
