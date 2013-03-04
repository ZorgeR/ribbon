package com.zlab.ribbon;

import java.text.SimpleDateFormat;

public class Ribbon_Elements {
    private String username;
    private String userid;
    private String messagetype;
    private String network;
    private long postdate;

    public Ribbon_Elements(String username, String userid, String messagetype, String network, long postdate)
    {
        username = username;
        userid = userid;
        messagetype = messagetype;
        network = network;
        postdate = postdate;
    }

    public String getUserName()
    {
        return username;
    }
    public String getUserID()
    {
        return userid;
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
        return sdf.format(postdate);
    }
}
