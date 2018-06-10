package com.example.sheikr.muziekapplicatie.musicPlayer;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.sheikr.muziekapplicatie.R;

public class PlayListActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        webView = (WebView)findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.loadUrl("https://streamboxr.com/demo");

    }
}

