package com.zlab.ribbon;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.util.ByteArrayBuffer;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class twitter_build_list extends AsyncTask<String, Void, List<twitter4j.Status>> {
    public Ribbon_Main activity;
    private Twitter twitter;
    ProgressDialog progress;

    public twitter_build_list(Ribbon_Main a)
    {
        activity = a;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progress = ProgressDialog.show(activity, "Please wait...",
                "Update ribbon content!", true);
    }

    @Override
    protected List<twitter4j.Status> doInBackground(String... params) {
        String oauthAccessToken = params[0];
        String oAuthAccessTokenSecret = params[1];

        ConfigurationBuilder confbuilder = new ConfigurationBuilder();
        Configuration conf = confbuilder
                .setOAuthConsumerKey(twitter_constant.CONSUMER_KEY)
                .setOAuthConsumerSecret(twitter_constant.CONSUMER_SECRET)
                .setOAuthAccessToken(oauthAccessToken)
                .setOAuthAccessTokenSecret(oAuthAccessTokenSecret)
                .build();
        twitter = new TwitterFactory(conf).getInstance();
        List<twitter4j.Status> statusList = null;

        try {
            statusList=twitter.getHomeTimeline(twitter_constant.TWITTER_PAGING);
            for (twitter4j.Status list : statusList) {
                try {
                    if(Ribbon_SectionFragment.ribbon_listelements==null){
                        Ribbon_SectionFragment.ribbon_listelements = new ArrayList<Ribbon_Elements>();
                    }
                        Ribbon_SectionFragment.ribbon_listelements.add(
                                new Ribbon_Elements(
                                        list.getUser().getName(),
                                        "@"+list.getUser().getScreenName(),
                                        drawableFromUrl(list.getUser().getProfileImageURLHttps(),list.getUser().getScreenName()),
                                        list.getText(),
                                        "timeline",
                                        "twitter",
                                        list.getCreatedAt()
                                        )
                        );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return statusList;
    }

    protected void onPostExecute(List<twitter4j.Status> result) {
        super.onPostExecute(result);
        if(result!=null){
            if(Ribbon_SectionFragment.adaptor==null){
                Ribbon_SectionFragment.adaptor = new Ribbon_ListElemetsAdaptor(Ribbon_Main.mContext,R.layout.ribbon_elements,Ribbon_SectionFragment.ribbon_listelements);
                Ribbon_SectionFragment.ribbon_list.setAdapter(Ribbon_SectionFragment.adaptor);
            } else {
                Ribbon_SectionFragment.adaptor.notifyDataSetChanged();
            }
        }
        progress.dismiss();
    }

    public Drawable drawableFromUrl(String url, String name) throws IOException {
        //Bitmap x;
        boolean finded=false;

        File dir = activity.getFilesDir();
            for(File ff : dir.listFiles()){
                if(ff.getName().equals("twitter_avatar_"+name)){
                    finded=true;
            }
        }
        if(!finded){
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(input);
            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(dir.getPath()+"/"+"twitter_avatar_"+name);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
            input.close();
        }

        //FileInputStream fis = activity.openFileInput("twitter_avatar_"+name);
        //x = BitmapFactory.decodeStream(fis);
        //fis.close();
        //return new BitmapDrawable(x);

        return Drawable.createFromPath(dir.getPath()+"/"+"twitter_avatar_"+name);
    }

    public void writeFileToInternalStorage(Context context, Bitmap outputImage, String name) {
        String fileName = Long.toString(System.currentTimeMillis()) + ".png";
        final FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }

    private void readFileFromInternalStorage(String filename) {
        File cacheDir = activity.getCacheDir();
    }
}
