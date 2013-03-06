package com.zlab.ribbon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class twitter_accessToken extends AsyncTask<String, Void, AccessToken> {
        public Ribbon_oAuth activity;
        private Twitter twitter;
        private AccessToken accessToken;
        ProgressDialog progress;

    public twitter_accessToken(Ribbon_oAuth a)
        {
            activity = a;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(activity, "Please wait...",
                    "Get accessToken!", true);
        }

        @Override
        protected AccessToken doInBackground(String... params) {
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setOAuthConsumerKey(twitter_constant.CONSUMER_KEY);
            configurationBuilder.setOAuthConsumerSecret(twitter_constant.CONSUMER_SECRET);
            Configuration configuration = configurationBuilder.build();
            twitter = new TwitterFactory(configuration).getInstance();

            try {
                accessToken = twitter.getOAuthAccessToken(Ribbon_Main.requestToken, params[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            Ribbon_Main.writeTwitterToken(accessToken.getToken(), accessToken.getTokenSecret());
            return accessToken;
        }

        protected void onPostExecute(AccessToken result) {
            super.onPostExecute(result);
            progress.dismiss();
            activity.finish();
            if(!Ribbon_oAuth.oAuthState.isFinishing()){
                Ribbon_oAuth.oAuthState.finish();
            }

            Ribbon_Main.updateAdaptors();
        }
}
