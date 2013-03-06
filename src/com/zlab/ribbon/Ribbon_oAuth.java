package com.zlab.ribbon;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ribbon_oAuth extends Activity {

    static Context oAuthContext;
    static Ribbon_oAuth oAuthState;

    private Button btn_auth_twitter,btn_auth_facebook;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ribbon_account_page);

        oAuthContext = this;
        oAuthState = ((Ribbon_oAuth) Ribbon_oAuth.oAuthContext);

        initUI();

        Uri uri = getIntent().getData();
        if (uri != null){
            Ribbon_oAuthBrowser.oAuth_Browser_State.finish();
            if(uri.toString().startsWith(twitter_constant.CALLBACK_URL)) {
                String verifier = uri.getQueryParameter(twitter_constant.IEXTRA_OAUTH_VERIFIER);
                new twitter_accessToken(this).execute(verifier);
            }

            /* TODO Добавить Другие сети
            else if(uri.toString().startsWith(facebook_constant.CALLBACK_URL)
            */
        }
    }
    private void initUI(){
        btn_auth_twitter = (Button) findViewById(R.id.btn_auth_twitter);
        btn_auth_facebook = (Button) findViewById(R.id.btn_auth_facebook);

        if (Ribbon_Main.isConnected("twitter")) {
            btn_auth_twitter.setEnabled(false);
        }

        /* TODO Кнопки можно добавить в массив и обращаться по ID сети */

    }
    public  void btn_listener(View view) {
        switch (view.getId()) {
            case R.id.btn_auth_twitter:{
                new twitter_requestToken(this).execute();
                break;}
            case R.id.btn_auth_facebook: {
                break;}
        }
    }

}
