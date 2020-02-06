package com.lwm.androidandh5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class JsCallJavaVideoActivity extends Activity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
        webview = (WebView) findViewById(R.id.webview);
        // WebView 的用途
        // 1、加载网页(H5、html、自定义浏览器、直接在 WebView 中播放视频)
        // webview = new WebView(this);
        // 设置支持 Js
        WebSettings webSettings = webview.getSettings();
        // 设置支持 Js
        webSettings.setJavaScriptEnabled(true);

        // 不调用系统浏览器(相当于自定义浏览器)
        webview.setWebViewClient(new WebViewClient());


        // js 调用 java
        // 添加 js 的一个接口 JavascriptInterface
        // 以后 Js 就可以通过 Android 这个字段调用 AndroidAndJsInterface 类中的任何方法
        // 字段必须和 onclick="window.Android.showToast() 中的 Android 保持一致
        webview.addJavascriptInterface(new AndroidAndJsInterface(), "android");

        // 加载本地的网页
        // 这里的 asset 不能有 s
        webview.loadUrl("file:///android_asset/RealNetJSCallJavaActivity.htm");


        // 把 webView 设置为我们可以看见的页面
        // setContentView(webview);
    }

    class AndroidAndJsInterface {
        // 在 JavaAndJavaScriptCall.html 中的按钮点击事件为 java 的 showToast() 方法
        // 将会被 Js 调用
        //  js可以调用该类的方法
        @JavascriptInterface
        public void playVideo(int id, String videoUrl, String title) {
            // 1、把所有的播放器调起来,并且播放(如果系统只有一个播放器就会直接播放)
            Intent intent = new Intent();
            // video/* ：匹配所有的视频格式
            intent.setDataAndType(Uri.parse(videoUrl), "video/*");
            startActivity(intent);
            Toast.makeText(JsCallJavaVideoActivity.this, "videoUrl：" + videoUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
