package com.github.sgwhp.openapm.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by chenqihong on 2017/2/8.
 */

public class LoanActivity extends BaseActivity<String> implements View.OnClickListener{

    private Button mConfirmButton;
    private Button mKeyButton1;
    private Button mKeyButton2;
    private Button mKeyButton3;
    private String tag = "hello world";
    private int count= 0;
    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.loan_activity);
        //mList.add(tag);
        mConfirmButton = (Button)findViewById(R.id.confirm);
        mKeyButton1 = (Button)findViewById(R.id.key_button_1);
        mKeyButton2 = (Button)findViewById(R.id.key_button_2);
        mKeyButton3 = (Button)findViewById(R.id.key_button_3);
        mConfirmButton.setOnClickListener(this);
        mKeyButton1.setOnClickListener(this);
        mKeyButton2.setOnClickListener(this);
        final String clickposition="hello";
        mKeyButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("hello", getHello() + count + clickposition);
                count++;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == mConfirmButton){
            finish();
        }
    }
}
