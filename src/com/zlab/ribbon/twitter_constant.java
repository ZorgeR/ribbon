package com.zlab.ribbon;

import twitter4j.Paging;

public class twitter_constant {
    static String CONSUMER_KEY = "hP9YBH8shiaD20vdlXdwBA";
    static String CONSUMER_SECRET = "r4kEJDyKiS0soMNbNeIVhbMyU0S7EJyZhvYdc9Qcg"; /* TODO Защитить ключ */

    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_SECRET = "twitter_oauth_token_secret";
    static final String PREF_KEY_TOKEN = "twitter_oauth_token";

    static final String CALLBACK_URL = "oauth://ribbontwitter";

    static final String IEXTRA_AUTH_URL = "auth_url";
    static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
    static final String IEXTRA_OAUTH_TOKEN = "oauth_token";

    public static Paging TWITTER_PAGING = new Paging(1,40);
}
