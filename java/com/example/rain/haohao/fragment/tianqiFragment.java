package com.example.rain.haohao.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.rain.haohao.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * data 2017/8/15.
 * author:
 * function:
 */

public class tianqiFragment extends AppCompatActivity {

    private WebView www_wb;
    private ProgressBar webView_pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动界面
        setContentView(R.layout.fragment_tianqi);
        www_wb = (WebView) findViewById(R.id.www_wb);
        webView_pb = (ProgressBar) findViewById(R.id.webView_pb);
        //如果直接这样加载，在一些机型会跳转到浏览器上，因此我们还要做一些特殊操作

        //使用WebView对象进行一些初始化操作
        webViewInit();
        //webView的settings对象进行webView一系列初始化的设置
        webViewSettingInit();
    }


    private void webViewSettingInit() {
        //加载某一个网址，跳转到浏览器进行加载,点击事件的监听
        www_wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url) {

                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //指UI发送改变时进行的各种监听
        www_wb.setWebChromeClient(new WebChromeClient(){
            //newProgress网页加载的进度，当是100的时候，代表加载成功
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //让进度条显示出来
                webView_pb.setVisibility(View.VISIBLE);
                //对进度条设置进度参数
                webView_pb.setProgress(newProgress);
                if (newProgress == 100){
                    //让进度条消失
                    webView_pb.setVisibility(View.GONE);

                }


                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void webViewInit() {
        WebSettings settings = www_wb.getSettings();
        //使用webView对设置对象，使WebView支持JS
        settings.setJavaScriptEnabled(true);
        //使webview可以根据手机自动适配屏幕
        settings.setSupportZoom(true);
    }

    public void load(View view) {
        www_wb.loadUrl("http://tianqi.2345.com/");

    }


}
