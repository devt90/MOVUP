package com.example.ayushsethi.movup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Youtube extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Bundle extras=getIntent().getExtras();
        String url=extras.getString("link");
        setTitle(extras.getString("title"));
        WebView web=(WebView)findViewById(R.id.you);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){});
        web.loadUrl(url);

    }
}
