package com.zlab.ribbon;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Ribbon_OAuth_Twitter2 extends AsyncTask<String, Void, AccessToken> {
        public Ribbon_Worker_Twitter activity;
        AccessToken accessToken;

        public Ribbon_OAuth_Twitter2(Ribbon_Worker_Twitter a)
        {
            activity = a;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(activity, "Please authorize this app!", Toast.LENGTH_LONG).show();
        }

        @Override
        protected AccessToken doInBackground(String... params) {
            try {
                accessToken = activity.twitter.getOAuthAccessToken(activity.requestToken, params[0]);
            } catch (TwitterException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            SharedPreferences.Editor e = activity.mSharedPreferences.edit();
            e.putString(cons_twitter.PREF_KEY_TOKEN, accessToken.getToken());
            e.putString(cons_twitter.PREF_KEY_SECRET, accessToken.getTokenSecret());
            e.commit();
            return accessToken;
        }

        protected void onPostExecute(AccessToken result) {
            super.onPostExecute(result);
        }
}
