package com.fit2081.assignment3;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Google Search Results");

        WebView webView = findViewById(R.id.webView);

        String eventName = getIntent().getStringExtra("eventName");
        if (eventName != null && !eventName.isEmpty()) {
            String googleSearchURL = "https://www.google.com/search?q=" + eventName;
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(googleSearchURL);
        } else {
            Toast.makeText(this, "Event name not found", Toast.LENGTH_LONG).show();
        }
    }
}
