package com.zlab.ribbon;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Ribbon_OAuth_Twitter extends AsyncTask<String, Void, RequestToken> {
    public Ribbon_Worker_Twitter activity;
    Twitter twitter;
    RequestToken requestToken;
    AccessToken accessToken;
    public Ribbon_OAuth_Twitter(Ribbon_Worker_Twitter a)
    {
        activity = a;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity, "Please authorize this app!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected RequestToken doInBackground(String... params) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(cons_twitter.CONSUMER_KEY);
        configurationBuilder.setOAuthConsumerSecret(cons_twitter.CONSUMER_SECRET);
        Configuration configuration = configurationBuilder.build();
        twitter = new TwitterFactory(configuration).getInstance();

        try {
            requestToken = twitter.getOAuthRequestToken(cons_twitter.CALLBACK_URL);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return activity.requestToken;
    }

    protected void onPostExecute(RequestToken result) {
        super.onPostExecute(result);
        activity.twitter=twitter;
        activity.requestToken=requestToken;
        activity.startActivity(
                new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(activity.requestToken.getAuthenticationURL()))
        );

    }
}