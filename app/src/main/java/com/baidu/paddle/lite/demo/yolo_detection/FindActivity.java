package com.baidu.paddle.lite.demo.yolo_detection;

import android.app.Activity;

import android.os.Bundle;
import android.webkit.WebView;

public class FindActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_find);
        //获得WebView控件
        load();
    }
    private void load(){
        //获得WebView控件
        WebView webView=(WebView)findViewById(R.id.idWebView);
        //调用loadUrl()方法嵌入百度新闻页面
        webView.loadUrl("https://zhuanlan.zhihu.com/p/65142304");
    }
}