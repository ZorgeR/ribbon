package com.zlab.ribbon;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class twitter_accessToken extends AsyncTask<RequestToken, Void, AccessToken> {
        public Ribbon_Main activity;
        private Twitter twitter;
        private AccessToken accessToken;
        private RequestToken requestToken;
        ProgressDialog progress;

    public twitter_accessToken(Ribbon_Main a)
        {
            activity = a;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(activity, "Please wait...",
                    "Get accessToken!", true);
        }

        @Override
        protected AccessToken doInBackground(RequestToken... params) {
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setOAuthConsumerKey(twitter_constant.CONSUMER_KEY);
            configurationBuilder.setOAuthConsumerSecret(twitter_constant.CONSUMER_SECRET);
            Configuration configuration = configurationBuilder.build();
            twitter = new TwitterFactory(configuration).getInstance();

            requestToken=params[0];

            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, Ribbon_Main.TokenVerifier);
                Ribbon_Main.TokenVerifier="";
                Ribbon_Main.writeTwitterToken(accessToken.getToken(), accessToken.getTokenSecret());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return accessToken;
        }

        protected void onPostExecute(AccessToken result) {
            super.onPostExecute(result);
            progress.dismiss();

            /**
            if(!Ribbon_oAuth.oAuthState.isFinishing()){
                Ribbon_oAuth.oAuthState.finish();
            }
            */
            if(accessToken!=null){
                Ribbon_Main.updateAdaptors();
            } else {
                Ribbon_Main.notify_toast(Ribbon_Main.mContext.getResources().getString(R.string.notify_connection_error),"error");
            }
        }
}
