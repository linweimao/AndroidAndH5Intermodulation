package com.lwm.androidandh5;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class JsCallJavaCallPhoneActivity extends Activity {

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
        webview.addJavascriptInterface(new AndroidAndJsInterface(), "Android");

        // 加载本地的网页
        // 这里的 asset 不能有 s
        webview.loadUrl("file:///android_asset/JsCallJavaCallPhone.html");


        // 把 webView 设置为我们可以看见的页面
        // setContentView(webview);
    }

    class AndroidAndJsInterface {

        @JavascriptInterface
        public void call(String phone) {
            // 1、拨打电话的意图
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            startActivity(intent);
            // 2、要加拨打电话的权限
        }

        // 将会被 Js 调用,加载联系人数据
        //  js可以调用该类的方法
        @JavascriptInterface
        public void showcontacts() {
            String json = "[{\"name\":\"沫沫然\", \"phone\":\"18318412345\"}]";
            // 调用JS中的 show() 方法
            webview.loadUrl("javascript:show('" + json + "')");

        }
    }
}
