package com.example.zuimeichuangyi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.zuimeichuangyi.MainActivity;
import com.example.zuimeichuangyi.R;

public class StartActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        handler = new Handler();

        new Thread() {
            public void run() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(StartActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);//两秒后到达MainActivity
            }
        }.start();
    }
}
