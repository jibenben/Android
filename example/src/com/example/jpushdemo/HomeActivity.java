package com.example.jpushdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiwawa.jiguang.R;
import java.net.URLEncoder;
import static android.R.attr.data;

public class HomeActivity extends Activity implements View.OnClickListener {
    private Button student,course,order,my;
    private TextView stu_ts,cou_ts,ord_ts,my_ts;
    private WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        stu_ts=(TextView)findViewById(R.id.student_text);
        cou_ts=(TextView)findViewById(R.id.course_text);
        ord_ts=(TextView)findViewById(R.id.order_text);
        my_ts=(TextView)findViewById(R.id.my_text);
        browser=(WebView)findViewById(R.id.content);
        student=(Button)findViewById(R.id.student);
        student.setTextColor(Color.BLACK);
        course=(Button)findViewById(R.id.course);
        course.setTextColor(Color.BLACK);
        my=(Button)findViewById(R.id.my);
        my.setTextColor(Color.BLACK);
        order=(Button)findViewById(R.id.order) ;
        order.setTextColor(Color.BLACK);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new JavaScriptInterface(this,""), "Home");
        browser.addJavascriptInterface(new JavaScriptInterface(this), "Color");
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        student.setOnClickListener(this);
        order.setOnClickListener(this);
        my.setOnClickListener(this);
        course.setOnClickListener(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private Handler handler = new Handler();
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.student:
                student.setEnabled(false);
                stu_ts.setTextColor(Color.RED);
                course.setEnabled(true);
                order.setEnabled(true);
                my.setEnabled(true);
                cou_ts.setTextColor(Color.BLACK);
                ord_ts.setTextColor(Color.BLACK);
                my_ts.setTextColor(Color.BLACK);
                browser.loadUrl(Routing.getStudenturl());
                break;
            case R.id.order:
                order.setEnabled(false);
                ord_ts.setTextColor(Color.RED);
                course.setEnabled(true);
                my.setEnabled(true);
                student.setEnabled(true);
                stu_ts.setTextColor(Color.BLACK);
                my_ts.setTextColor(Color.BLACK);
                cou_ts.setTextColor(Color.BLACK);
                browser.loadUrl(Routing.getOrderurl());
                break;
            case R.id.course:
                course.setEnabled(false);
                cou_ts.setTextColor(Color.RED);
                stu_ts.setTextColor(Color.BLACK);
                my_ts.setTextColor(Color.BLACK);
                ord_ts.setTextColor(Color.BLACK);
                my.setEnabled(true);
                student.setEnabled(true);
                order.setEnabled(true);
                browser.loadUrl(Routing.getcourserul());
                break;
            default:
                my.setEnabled(false);
                student.setEnabled(true);
                order.setEnabled(true);
                course.setEnabled(true);
                my_ts.setTextColor(Color.RED);
                stu_ts.setTextColor(Color.BLACK);
                my_ts.setTextColor(Color.BLACK);
                ord_ts.setTextColor(Color.BLACK);
                browser.loadUrl(Routing.getLogouturl());
                break;
        }
    }
}
