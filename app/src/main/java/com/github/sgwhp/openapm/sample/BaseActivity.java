package com.github.sgwhp.openapm.sample;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by chenqihong on 2017/2/17.
 */

public class BaseActivity<T> extends Activity{
    protected String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
