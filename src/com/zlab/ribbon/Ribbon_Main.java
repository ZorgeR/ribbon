package com.zlab.ribbon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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
    public static SharedPreferences oAuthSharedPreferences;

    /** TWITTER **/ /* TODO Сделать Private и добавить методы setter и getter */
    public static Twitter twitter;
    public static RequestToken requestToken;
    public static AccessToken accessToken;

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

        updateAdaptors();
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
            Intent i = new Intent(Ribbon_Main.mActivity.getApplicationContext(), Ribbon_oAuth.class);
            Ribbon_Main.mActivity.startActivity(i);
        } else {
            if(isConnected("twitter")){
                new twitter_build_list(Ribbon_Main.mActivity).execute("");
            }
        }
    }

    public static boolean isConnected(String network) {
        return oAuthSharedPreferences.getString(network+"_oauth_token", null) != null;
    }
    public static void setTwitter(int network_id, Twitter tw){
        twitter = tw;
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

}
