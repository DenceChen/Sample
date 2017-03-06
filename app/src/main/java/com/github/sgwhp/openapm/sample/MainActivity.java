package com.github.sgwhp.openapm.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends BaseActivity<String>
        implements  View.OnClickListener {
    private Button mLoanButton;
    private Button mReportButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar(toolbar);
        mLoanButton = (Button) findViewById(R.id.button);
        mReportButton = (Button)findViewById(R.id.report);
        mLoanButton.setOnClickListener(this);
        mReportButton.setOnClickListener(this);
        /*int[] arr = new int[1];
        try{
            arr[0] = 0;
            arr[1] = 1;
            System.out.println(arr[0]);
        } catch(IndexOutOfBoundsException e){
            System.out.println("oops");
        }*/
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://baidu.com").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            Toast.makeText(MainActivity.this, "successed", Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if(view == mLoanButton){
            Intent intent = new Intent(MainActivity.this, LoanActivity.class);
            startActivity(intent);
        }else if(view == mReportButton){
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(intent);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
}
