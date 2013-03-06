package com.zlab.ribbon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.zlab.ribbon.Ribbon_Elements;

import java.util.List;

public class Ribbon_ListElemetsAdaptor extends ArrayAdapter<Ribbon_Elements> {

    private Context c;
    private int id;
    private List<Ribbon_Elements> items;
    private boolean[] sel;

    TextView text_username;
    TextView text_realusername;
    TextView text_message;
    TextView text_timestamp;
    ImageView img_avatar;
    RelativeLayout ll;
    int resID;

    public Ribbon_ListElemetsAdaptor(Context context, int LayoutID, List<Ribbon_Elements> objects) {
        super(context, LayoutID, objects);
        c = context;
        id = LayoutID;
        items = objects;
    }

    public Ribbon_Elements getItem(int i)
    {
        return items.get(i);
    }

    public List getAllItems()
    {
        return items;
    }

    @Override
    public View getView(final int position, View view,final ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }

        final Ribbon_Elements o = items.get(position);

        if(items.size()==position+1){
            twitter_constant.TWITTER_PAGING.setPage(twitter_constant.TWITTER_PAGING.getPage()+1);
            new twitter_build_list(Ribbon_Main.mActivity).execute("");
        }

        if (o != null) {
            text_username = (TextView) v.findViewById(R.id.textUserName);
            text_realusername = (TextView) v.findViewById(R.id.textRealUserName);
            text_message = (TextView) v.findViewById(R.id.textMessage);
            text_timestamp = (TextView) v.findViewById(R.id.textTimeStamp);
            img_avatar = (ImageView) v.findViewById(R.id.imgAvatar);

            //ll = (RelativeLayout) v.findViewById(R.id.itemsofrow);

            if(text_username!=null){
                text_username.setText(o.getUserName());}
            if(text_realusername!=null){
                text_realusername.setText(o.getUserID());}
            if(text_message!=null){
                text_message.setText(o.getMessage());}
            if(text_timestamp!=null){
                text_timestamp.setText(o.getDate());}
            if(img_avatar!=null){
                img_avatar.setImageDrawable(o.getAvatar());}
        }

        return v;
    }

}
