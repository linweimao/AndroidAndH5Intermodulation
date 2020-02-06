package com.lwm.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnJavaAndJs;
    private Button btnJsCallJava;
    private Button btnJsCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // 初始化
        btnJavaAndJs = (Button) findViewById(R.id.btn_java_and_js);
        btnJsCallJava = (Button) findViewById(R.id.btn_js_call_java);
        btnJsCallPhone = (Button) findViewById(R.id.btn_js_call_phone);
        // 设置点击事件
        btnJavaAndJs.setOnClickListener(this);
        btnJsCallJava.setOnClickListener(this);
        btnJsCallPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnJavaAndJs) {
            //Toast.makeText(MainActivity.this,"互调",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, JavaAndJsCallActivity.class);
            startActivity(intent);
        } else if (v == btnJsCallJava) {
            //Toast.makeText(MainActivity.this,"播放视频",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, JsCallJavaVideoActivity.class);
            startActivity(intent);
        } else if (v == btnJsCallPhone) {
            Intent intent = new Intent(this, JsCallJavaCallPhoneActivity.class);
            startActivity(intent);
            // Toast.makeText(MainActivity.this, "拨打电话", Toast.LENGTH_SHORT).show();
        }
    }
}
