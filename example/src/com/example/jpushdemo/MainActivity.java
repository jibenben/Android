package com.example.jpushdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.jiwawa.jiguang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends Activity {
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	private UpdateManager mUpdateManager;
	private String version;
	private  String infro[];
	private WebView login;
	private static String regid;
	private long exitTime = 0;
	private static boolean flag=true;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//初始化极光推送注册推送id
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		registerMessageReceiver();
		regid = JPushInterface.getRegistrationID(getApplicationContext());
		login=(WebView)findViewById(R.id.login);
		if(intent.getStringExtra("flag")!=null){
			flag=!true;
		}
		//判断是初始跳转还是从注销跳转（flag==true 初始页面；flag==false 注销页面）
		if(!flag) {
			version = mUpdateManager.getVersion(this);//获取当前apk版本号
			sendRequestWithHttpClient();//连接网络请求是否需要升级
		}else{
			login.loadUrl(Routing.getLoginurl());
		}
		login.getSettings().setJavaScriptEnabled(true);
		login.getSettings().setSupportZoom(true);
		login.getSettings().setBuiltInZoomControls(true);
		login.addJavascriptInterface(new JavaScriptInterface(this,regid), "Android");
		login.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String response = (String) msg.obj;
			mUpdateManager=new UpdateManager(MainActivity.this,response.split(" ")[1]);
			mUpdateManager .checkUpdateInfo();
			login.loadUrl(Routing.getLoginurl());
		}
	};
	//与服务器进行交互，如果有新的版本高于现在的版本就发送新的下载地址给现在的版本
	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String path=Routing.getVersion()+"?version="+version;
					String str=getJsonByInternet(path);
					try {
						JSONObject jsonObj = new JSONObject(str).getJSONObject("Version");
						String  flagversion = jsonObj.getString("flag");
						if(flagversion.equals("true")){
							Message message = new Message();
							message.obj=jsonObj.getString("address");
							handler.sendMessage(message);
						}
					} catch (JSONException e) {
						System.out.println("Json parse error");
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();//这个start()方法不要忘记了
	}
	public static String getJsonByInternet(String path){
		//使用httpURLconnention 进行连接
		try {
			URL url = new URL(path.trim());
			//打开连接
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			if(200 == urlConnection.getResponseCode()){
				//得到输入流
				InputStream is =urlConnection.getInputStream();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while(-1 != (len = is.read(buffer))){
					baos.write(buffer,0,len);
					baos.flush();
				}
				return baos.toString("utf-8");
			}
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}