package com.example.hau.circleprogressview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import top.greendami.circleprogressview.CircleProgressView;
import  android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircleProgressView CV = (CircleProgressView)findViewById(com.example.hau.circleprogressview.R.id.cv);

        CV.setProgress(35.8f);
    }
}
