package com.example.jpushdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiwawa.jiguang.R;

/**
 * Created by Kaike on 2017/3/31.
 */

public class AboutUsActivity extends Activity{
    private Button call;
    private TextView textView;
    private String phone="18810792528";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        call=(Button)findViewById(R.id.phone);
        call.setText("问题反馈"+" "+phone);
    }
}
