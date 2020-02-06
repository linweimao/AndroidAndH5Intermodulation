package com.lwm.androidandh5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JavaAndJsCallActivity extends Activity implements View.OnClickListener {

    private EditText etNumber;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnLogin1;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js_call);
        initView();
        // 初始化本地网页
        initWebview1();
    }

    private void initView() {
        etNumber = (EditText) findViewById(R.id.et_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin1 = (Button) findViewById(R.id.btn_login1);
        btnLogin.setOnClickListener(this);
        btnLogin1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            login();
        } else if (v == btnLogin1) {
            login1();
        }
    }

    // 登录(加载网络的网页)
    private void login() {
        // 1、得到账号和密码
        // trim() : 把空格去掉
        String number = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        // 2、判断密码是否为空
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJsCallActivity.this, "账号和密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            // 账号和密码不为空
            initWebview();
        }
    }

    // 加载网络的网页
    private void initWebview() {
        // WebView 的用途
        // 1、加载网页(H5、html、自定义浏览器、直接在 WebView 中播放视频)
        webView = new WebView(this);
        // 设置支持 js
        WebSettings webSettings = webView.getSettings();
        // 设置支持 js
        webSettings.setJavaScriptEnabled(true);

        // 不调用系统浏览器(相当于自定义浏览器)
        // 设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());

        // 加载网络的网页（或者本地的网页）
        webView.loadUrl("https://www.csdn.net/");


        // 把 webView 设置为我们可以看见的页面
        setContentView(webView);
    }

    // 登录(加载本地的网页)
    private void login1() {
        // 1、得到账号和密码
        // trim() : 把空格去掉
        String number = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        // 2、判断密码是否为空
        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            Toast.makeText(JavaAndJsCallActivity.this, "账号和密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            // 账号和密码不为空
            //initWebview1();
            login1(number);
        }
    }

    /**
     * Java调用javaScript
     *
     * @param numebr
     */
    private void login1(String numebr) {
        // 调用 javascript(js)中的 javaCallJs 方法
        // javascript:javaCallJs(123):传入一个值相当于是 "'"+numebr+"'"(这种写法)
        webView.loadUrl("javascript:javaCallJs(" + "'" + numebr + "'" + ")");
        setContentView(webView);
    }


    // 加载本地的网页
    private void initWebview1() {
        // WebView 的用途
        // 1、加载网页(H5、html、自定义浏览器、直接在 WebView 中播放视频)
        webView = new WebView(this);
        // 设置支持 js
        WebSettings webSettings = webView.getSettings();
        // 设置支持 js
        webSettings.setJavaScriptEnabled(true);

        // 不调用系统浏览器(相当于自定义浏览器)
        webView.setWebViewClient(new WebViewClient());


        // js 调用 java
        // 添加 js 的一个接口 JavascriptInterface
        // 以后 Js 就可以通过 Android 这个字段调用 AndroidAndJsInterface 类中的任何方法
        // 字段必须和 onclick="window.Android.showToast() 中的 Android 保持一致
        webView.addJavascriptInterface(new AndroidAndJsInterface(), "Android");

        // 加载本地的网页
        // 这里的 asset 不能有 s
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");


        // 把 webView 设置为我们可以看见的页面
        // setContentView(webView);
    }

    class AndroidAndJsInterface {
        // 在 JavaAndJavaScriptCall.html 中的按钮点击事件为 java 的 showToast() 方法
        // 将会被 Js 调用
        //  js可以调用该类的方法
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(JavaAndJsCallActivity.this, "我是Android代码，我被Js调用了！", Toast.LENGTH_SHORT).show();
        }
    }
}
