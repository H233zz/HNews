package com.example.rain.haohao.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rain.haohao.R;

import java.util.Timer;
import java.util.TimerTask;


public class qidongActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动界面
        setContentView(R.layout.activity_qidong);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent it = new Intent(qidongActivity.this, MainActivity.class);
                startActivity(it);
            }
        };
        timer.schedule(timerTask, 2000);
    }
}
