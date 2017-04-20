package com.example.jpushdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import com.example.jiwawa.jiguang.R;
/**
 * Created by Kaike on 2017/3/27.
 */
public class MyActivity extends Activity{
    private Button cache,logout,aboutUs;
    private long cacheData;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        logout=(Button)findViewById(R.id.logout);
        cache=(Button)findViewById(R.id.cache);
        cacheData=DataCleanManager.getDirSize(getCacheDir());
        cache.setText("清理缓存"+cacheData/1024/1024+"M");
        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCleanManager.cleanApplicationData(MyActivity.this);
                cacheData=DataCleanManager.getDirSize(getCacheDir());
                cache.setText("清理缓存"+cacheData/1024/1024+"M");
            }
        });
        aboutUs=(Button)findViewById(R.id.about);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MyActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MyActivity.this,LogoutActivity.class);
                startActivity(intent);
            }
        });
    }

}
