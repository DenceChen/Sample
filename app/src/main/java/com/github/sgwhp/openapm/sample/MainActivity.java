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

public class MainActivity extends Activity
        implements  View.OnClickListener {
    private Button mLoanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar(toolbar);
        mLoanButton = (Button) findViewById(R.id.button);
        mLoanButton.setOnClickListener(this);
        /*int[] arr = new int[1];
        try{
            arr[0] = 0;
            arr[1] = 1;
            System.out.println(arr[0]);
        } catch(IndexOutOfBoundsException e){
            System.out.println("oops");
        }*/
    }

    @Override
    public void onClick(View view) {
        if(view == mLoanButton){
            Intent intent = new Intent(MainActivity.this, LoanActivity.class);
            startActivity(intent);
        }
    }
}
