package com.zlab.ribbon;

import android.content.Context;
import twitter4j.*;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class Ribbon_Worker_Twitter extends Activity implements OnClickListener {
    private Button buttonLogin;
    private Button getTweetButton;
    public TextView tweetText;
    private ScrollView scrollView;

    protected static SharedPreferences mSharedPreferences;
    private static TwitterStream twitterStream;
    private boolean running = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mSharedPreferences = getSharedPreferences(twitter_constant.PREFERENCE_NAME, MODE_PRIVATE);
        scrollView = (ScrollView)findViewById(R.id.scrollView2);
        tweetText =(TextView)findViewById(R.id.tweetText);
        getTweetButton = (Button)findViewById(R.id.getTweet);
        getTweetButton.setOnClickListener(this);
        buttonLogin = (Button) findViewById(R.id.twitterLogin);
        buttonLogin.setOnClickListener(this);

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(twitter_constant.CALLBACK_URL)) {
            String verifier = uri.getQueryParameter(twitter_constant.IEXTRA_OAUTH_VERIFIER);
            //new twitter_accessToken(this).execute(verifier);
        }
    }

    protected void onResume() {
        super.onResume();

        if (/*isConnected()*/1>0) {
            String oauthAccessToken = mSharedPreferences.getString(twitter_constant.PREF_KEY_TOKEN, "");
            String oAuthAccessTokenSecret = mSharedPreferences.getString(twitter_constant.PREF_KEY_SECRET, "");

            ConfigurationBuilder confbuilder = new ConfigurationBuilder();
            Configuration conf = confbuilder
                    .setOAuthConsumerKey(twitter_constant.CONSUMER_KEY)
                    .setOAuthConsumerSecret(twitter_constant.CONSUMER_SECRET)
                    .setOAuthAccessToken(oauthAccessToken)
                    .setOAuthAccessTokenSecret(oAuthAccessTokenSecret)
                    .build();
            twitterStream = new TwitterStreamFactory(conf).getInstance();
            //new twitter_build_list(this).execute("");

            buttonLogin.setText(R.string.label_disconnect);
            getTweetButton.setEnabled(true);

        } else {
            buttonLogin.setText(R.string.label_connect);
        }
    }

    private void askOAuth() {
        //new twitter_requestToken(this).execute();
    }

    private void disconnectTwitter() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(twitter_constant.PREF_KEY_TOKEN);
        editor.remove(twitter_constant.PREF_KEY_SECRET);

        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.twitterLogin:
                if (/*isConnected()*/1>0) {
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
