package com.zlab.ribbon;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

public class Ribbon_oAuthBrowser extends Activity {
    private WebView webView;
    public static Context oAuth_Browser_Context;
    public static Ribbon_oAuthBrowser oAuth_Browser_State;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ribbon_oauth_browser);

        oAuth_Browser_Context = this;
        oAuth_Browser_State = ((Ribbon_oAuthBrowser) Ribbon_oAuthBrowser.oAuth_Browser_Context);
        //Uri uri = getIntent().getData();
        Uri uri = Uri.parse(getIntent().getExtras().getString("uri"));
        if (uri != null){
            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(uri.toString());
        } else {
            finish();
        }
    }
}
