package com.pklabs.twitertestapp001;

import java.io.Serializable;

/**
 * Created by Kaushik on 8/13/15.
 */
public class TwitterAccess implements Serializable {
    private String consumerKey;
    private String consumerSecret;
    private String oAuthTokenKey;
    private String oAuthTokenSecret;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getoAuthTokenKey() {
        return oAuthTokenKey;
    }

    public void setoAuthTokenKey(String oAuthTokenKey) {
        this.oAuthTokenKey = oAuthTokenKey;
    }

    public String getoAuthTokenSecret() {
        return oAuthTokenSecret;
    }

    public void setoAuthTokenSecret(String oAuthTokenSecret) {
        this.oAuthTokenSecret = oAuthTokenSecret;
    }

    public TwitterAccess() {
        this("", "", "", "");
    }

    public TwitterAccess(String consumerKey, String consumerSecret, String oAuthTokenKey, String oAuthTokenSecret) {
        if (consumerKey == null) {
            throw new IllegalArgumentException("consumerKey");
        }
        if (consumerSecret == null) {
            throw new IllegalArgumentException("consumerSecret");
        }
        if (oAuthTokenKey == null) {
            throw new IllegalArgumentException("oAuthTokenKey");
        }
        if (oAuthTokenSecret == null) {
            throw new IllegalArgumentException("oAuthTokenSecret");
        }

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.oAuthTokenKey = oAuthTokenKey;
        this.oAuthTokenSecret = oAuthTokenSecret;
    }
}
