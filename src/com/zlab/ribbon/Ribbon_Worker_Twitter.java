package com.zlab.ribbon;

import android.content.Context;
import android.os.AsyncTask;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Ribbon_Worker_Twitter extends Activity implements OnClickListener {
    private static final String TAG = "Ribbon:";

    private Button buttonLogin;
    private Button getTweetButton;
    private TextView tweetText;
    private ScrollView scrollView;

    public static Twitter twitter;
    public static RequestToken requestToken;
    public static SharedPreferences mSharedPreferences;
    private static TwitterStream twitterStream;
    private boolean running = false;
    static Context mTContext;
    public static AccessToken accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTContext = this;

        mSharedPreferences = getSharedPreferences(cons_twitter.PREFERENCE_NAME, MODE_PRIVATE);
        scrollView = (ScrollView)findViewById(R.id.scrollView2);
        tweetText =(TextView)findViewById(R.id.tweetText);
        getTweetButton = (Button)findViewById(R.id.getTweet);
        getTweetButton.setOnClickListener(this);
        buttonLogin = (Button) findViewById(R.id.twitterLogin);
        buttonLogin.setOnClickListener(this);

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(cons_twitter.CALLBACK_URL)) {
            String verifier = uri.getQueryParameter(cons_twitter.IEXTRA_OAUTH_VERIFIER);
            new Ribbon_OAuth_Twitter2(this).execute(verifier);
        }
    }

    protected void onResume() {
        super.onResume();

        if (isConnected()) {
            String oauthAccessToken = mSharedPreferences.getString(cons_twitter.PREF_KEY_TOKEN, "");
            String oAuthAccessTokenSecret = mSharedPreferences.getString(cons_twitter.PREF_KEY_SECRET, "");

            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            Configuration conf = confbuilder
                    .setOAuthConsumerKey(cons_twitter.CONSUMER_KEY)
                    .setOAuthConsumerSecret(cons_twitter.CONSUMER_SECRET)
                    .setOAuthAccessToken(oauthAccessToken)
                    .setOAuthAccessTokenSecret(oAuthAccessTokenSecret)
                    .build();
            twitterStream = new TwitterStreamFactory(conf).getInstance();

            buttonLogin.setText(R.string.label_disconnect);
            getTweetButton.setEnabled(true);
        } else {
            buttonLogin.setText(R.string.label_connect);
        }
    }

    private boolean isConnected() {
        boolean connect = mSharedPreferences.getString(cons_twitter.PREF_KEY_TOKEN, null) != null;
        return connect;
    }

    private void askOAuth() {
        new Ribbon_OAuth_Twitter(this).execute();
    }

    /**
     * Remove Token, Secret from preferences
     */
    private void disconnectTwitter() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(cons_twitter.PREF_KEY_TOKEN);
        editor.remove(cons_twitter.PREF_KEY_SECRET);

        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.twitterLogin:
                if (isConnected()) {
                    disconnectTwitter();
                    buttonLogin.setText(R.string.label_connect);
                } else {
                    askOAuth();
                }
                break;
            case R.id.getTweet:
                if (running) {
                    stopStreamingTimeline();
                    running = false;
                    getTweetButton.setText("start streaming");
                } else {
                    startStreamingTimeline();
                    running = true;
                    getTweetButton.setText("stop streaming");
                }
                break;
        }
    }

    private void stopStreamingTimeline() {
        twitterStream.shutdown();
    }

    public void startStreamingTimeline() {
        UserStreamListener listener = new UserStreamListener() {

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                System.out.println("deletionnotice");
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                System.out.println("scrubget");
            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onStatus(Status status) {
                final String tweet = "@" + status.getUser().getScreenName() + " : " + status.getText() + "\n";
                System.out.println(tweet);
                tweetText.post(new Runnable() {
                    @Override
                    public void run() {
                        tweetText.append(tweet);
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                System.out.println("trackLimitation");
            }

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onBlock(User arg0, User arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDeletionNotice(long arg0, long arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDirectMessage(DirectMessage arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFavorite(User arg0, User arg1, Status arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFollow(User arg0, User arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFriendList(long[] arg0) {
                // TODO Auto-generated method stub
            }
                       /*
            @Override
            public void onRetweet(User arg0, User arg1, Status arg2) {
                // TODO Auto-generated method stub
            }           */

            @Override
            public void onUnblock(User arg0, User arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUnfavorite(User arg0, User arg1, Status arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListCreation(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListDeletion(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListMemberDeletion(User arg0, User arg1,  UserList arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserListUpdate(User arg0, UserList arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onUserProfileUpdate(User arg0) {
                // TODO Auto-generated method stub
            }
        };
        twitterStream.addListener(listener);
        twitterStream.user();
    }
}
