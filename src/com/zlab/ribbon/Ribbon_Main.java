package com.zlab.ribbon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.List;

public class Ribbon_Main extends FragmentActivity {

    Ribbon_PagerAdapter mRibbonPagerAdapter;
    ViewPager mViewPager;
    static Context mContext;
    static Ribbon_Main mActivity; /* TODO удалить если не используется */

    /** ID **/
    static final int TAB_USER = 1;
    static final int TAB_RIBBON = 2;
    static final int TAB_NETWORK = 3;
    static final int TWITTER = 1;
    static final int FACEBOOK = 2;

    /** НАСТРОЙКИ **/
    private static SharedPreferences oAuthSharedPreferences;

    /** TWITTER **/ /* TODO Сделать Private и добавить методы setter и getter */
    //private static Twitter twitter;
    private static RequestToken requestToken;
    //private static AccessToken accessToken;
    public static String TokenVerifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ribbon_main);

        mContext = this;
        mActivity = ((Ribbon_Main) Ribbon_Main.mContext);

        oAuthSharedPreferences = getSharedPreferences("oAuth", MODE_PRIVATE);

        mRibbonPagerAdapter = new Ribbon_PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mRibbonPagerAdapter);
        mViewPager.setCurrentItem(1);

        /**updateAdaptors();**/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ribbon__main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent i = new Intent(getApplicationContext(), Ribbon_oAuth.class);
                startActivity(i);
                return true;
            }
            case R.id.action_exit:{
                finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    public static void updateAdaptors(){
        if(!isConnected("twitter") && !isConnected("facebook")){
            Intent i = new Intent(mActivity.getApplicationContext(), Ribbon_oAuth.class);
            mActivity.startActivity(i);
        } else {
            if(isConnected("twitter")){
                String oauthAccessToken = oAuthSharedPreferences.getString(twitter_constant.PREF_KEY_TOKEN, "");
                String oAuthAccessTokenSecret = oAuthSharedPreferences.getString(twitter_constant.PREF_KEY_SECRET, "");
                new twitter_build_list(mActivity).execute(oauthAccessToken,oAuthAccessTokenSecret);
            }
        }
    }

    public static void updTwitter(){
        String oauthAccessToken = oAuthSharedPreferences.getString(twitter_constant.PREF_KEY_TOKEN, "");
        String oAuthAccessTokenSecret = oAuthSharedPreferences.getString(twitter_constant.PREF_KEY_SECRET, "");
        new twitter_build_list(mActivity).execute(oauthAccessToken,oAuthAccessTokenSecret);
    }

    public static boolean isConnected(String network) {
        return oAuthSharedPreferences.getString(network+"_oauth_token", null) != null;
    }
    public static void setTwitter(int network_id, Twitter tw){
        //twitter = tw;
    }
    public static void setTwitterRequestToken(int network_id, RequestToken rt){
        requestToken = rt;
    }
    public static void writeTwitterToken(String token, String secrettoken){
        SharedPreferences.Editor e = oAuthSharedPreferences.edit();
        e.putString(twitter_constant.PREF_KEY_TOKEN, token);
        e.putString(twitter_constant.PREF_KEY_SECRET, secrettoken);
        e.commit();
    }
    public static void setAccessToken(String verifier){
        if(requestToken!=null){
            TokenVerifier=verifier;
            new twitter_accessToken(mActivity).execute(requestToken);
        }
    }
    public static void notify_toast(String msg,String type){
        Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_LONG);
        View toast_rootview = toast.getView();
        TextView text = (TextView) toast_rootview.findViewById(android.R.id.message);

        if(type.equals("error")){
            text.setTextColor(Color.RED);
        }

        text.setShadowLayer(0,0,0,0);

        toast.show();
    }

}
