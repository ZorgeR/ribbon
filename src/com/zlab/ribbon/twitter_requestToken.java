package com.zlab.ribbon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class twitter_requestToken extends AsyncTask<String, Void, RequestToken> {

    public Ribbon_oAuth activity;
    private Twitter twitter;
    private RequestToken requestToken;
    ProgressDialog progress;

    public twitter_requestToken(Ribbon_oAuth a)
    {
        activity = a;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activity, "Please wait...",
                "Get requestToken!", true);
    }

    @Override
    protected RequestToken doInBackground(String... params) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(twitter_constant.CONSUMER_KEY);
        configurationBuilder.setOAuthConsumerSecret(twitter_constant.CONSUMER_SECRET);
        configurationBuilder.setUseSSL(true);
        Configuration configuration = configurationBuilder.build();
        twitter = new TwitterFactory(configuration).getInstance();

        try {
            requestToken = twitter.getOAuthRequestToken(twitter_constant.CALLBACK_URL);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return requestToken;
    }

    protected void onPostExecute(RequestToken result) {
        super.onPostExecute(result);
        if(requestToken!=null){
        //Ribbon_Main.setTwitter(Ribbon_Main.TWITTER,twitter);
        Ribbon_Main.setTwitterRequestToken(Ribbon_Main.TWITTER,requestToken);
        /*
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(requestToken.getAuthenticationURL()));
        activity.startActivity(intent);    */

        Intent intent = new Intent(activity, Ribbon_oAuthBrowser.class);
        intent.putExtra("uri", requestToken.getAuthenticationURL());
        activity.startActivity(intent);
        } else {
            Ribbon_Main.notify_toast(Ribbon_Main.mContext.getResources().getString(R.string.notify_connection_error),"error");
        }
        progress.dismiss();
        /*
        WebView twitterSite = new WebView(this);
        twitterSite.loadUrl();

        activity.startActivity(
                new Intent(
                        Intent.ACTION_VIEW,
                        )
        );  */
    }
}