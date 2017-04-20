package com.example.jpushdemo;

/**
 * Created by Kaike on 2017/3/12.
 */

import android.content.SharedPreferences;
import android.os.Message;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Map;
import java.io.IOException;
import java.net.URLEncoder;
import java.io.ByteArrayOutputStream;

public class HttpUtil {
    private static  SharedPreferences preference;
    //专门用来连接
    public static String getJsonByInternet(String path,String session){
        //使用httpURLconnention 进行连接
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("cookie", session);
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
}
