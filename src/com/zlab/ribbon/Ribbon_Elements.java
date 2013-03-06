package com.zlab.ribbon;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ribbon_Elements {
    private String username;
    private String userid;
    private String messagetype;
    private String message;
    private String network;
    private String postdate;
    private Date postDate;
    private Drawable avatar;

    public Ribbon_Elements(String Username, String UserID, Drawable Avatar, String Message, String MessageType, String Network, Date Postdate)
    {
        username = Username;
        userid = UserID;
        avatar = Avatar;
        message = Message;
        messagetype = MessageType;
        network = Network;
        postDate = Postdate;
    }

    public String getUserName()
    {
        return username;
    }
    public String getUserID()
    {
        return userid;
    }
    public Drawable getAvatar(){
        return avatar;
    }
    public String getMessage(){
        return message;
    }
    public String getMessageType()
    {
        return messagetype;
    }
    public String getNetwork()
    {
        return network;
    }
    public String getDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm',' d MMM'.'");
        postdate = sdf.format(postDate);
        return postdate;//sdf.format(postdate);
    }
}
