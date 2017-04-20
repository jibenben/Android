package com.example.jpushdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

/**
 * Created by Kaike on 2017/3/27.
 */

public class JavaScriptInterface {
    Context mContext;
    String regid;
    Activity activity;
    /**
     * Instantiate the interface and set the context
     */
    JavaScriptInterface(Context c,String str) {
        mContext = c;
        regid=str;
    }
    JavaScriptInterface(Activity activity){
        this.activity=activity;
    }
    @JavascriptInterface  //高于API17的必须加上注解
    //实现页面之间的跳转，从登陆页面直接跳转到首页
    public void openActivity() {
        mContext.startActivity(new Intent(mContext, HomeActivity.class));
    }
    @JavascriptInterface
    //将注册的id 传输到服务器
    public String openRegsId(){
        return regid;
    }
    @JavascriptInterface
    //改变状态栏颜色
    public void  ChangeColor(int color){
        Message message = new Message();
        message.obj=color;
        handler.sendMessage(message);
    }
    @JavascriptInterface
    //实现页面退出按钮
    public void ChangeActivity(){
        Intent intent=new Intent();
        intent.putExtra("flag","111");
        intent.setClass(mContext,MainActivity.class);
        mContext.startActivity(intent);
    }
     private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int color=(int)msg.obj;
            if(color==1){
            StatusBarUtils.setWindowStatusBarColor(activity, android.R.color.white);
            }else{
                StatusBarUtils.setWindowStatusBarColor(activity, android.R.color.holo_red_dark);
            }
        }
    };
    @JavascriptInterface
    //获取缓存数据的大小
    public long getDate(){
        return DataCleanManager.getDirSize(mContext.getCacheDir())/1024/1024;
    }
    @JavascriptInterface
    //清除缓存程序
    public long  clean(){
        DataCleanManager.cleanApplicationData(mContext);
        return DataCleanManager.getDirSize(mContext.getCacheDir())/1024/1024;
    }
}
