package com.example.phonegapsim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Servidor server;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server= new Servidor(this);
        try{
            server.start();
        }
        catch (IOException ioe)
        {

        }

        webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://localhost:8000");

    }
}