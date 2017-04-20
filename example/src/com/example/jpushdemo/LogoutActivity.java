package com.example.jpushdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.jiwawa.jiguang.R;

import cn.jpush.android.api.JPushInterface;

import static com.example.jpushdemo.MainActivity.KEY_EXTRAS;
import static com.example.jpushdemo.MainActivity.KEY_MESSAGE;
import static com.example.jpushdemo.MainActivity.MESSAGE_RECEIVED_ACTION;

/**
 * Created by Kaike on 2017/4/10.
 */

public class LogoutActivity extends Activity {
    private WebView login;
    private static String regid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        registerMessageReceiver();
        regid = JPushInterface.getRegistrationID(getApplicationContext());
        System.out.println(regid);
        login=(WebView)findViewById(R.id.login);
        login.getSettings().setJavaScriptEnabled(true);
        login.getSettings().setSupportZoom(true);
        login.getSettings().setBuiltInZoomControls(true);
        login.addJavascriptInterface(new JavaScriptInterface(this,regid), "Android");
        login.loadUrl(Routing.getLoginurl());
        login.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
    }
    public void registerMessageReceiver() {
        MessageReceiver mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//				setCostomMsg(showMsg.toString());
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return false;
    }
}
