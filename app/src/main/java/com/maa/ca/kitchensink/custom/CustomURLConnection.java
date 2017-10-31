package com.maa.ca.kitchensink.custom;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;


/**
 * Created by sugsh04 on 9/18/17.
 */

public class CustomURLConnection extends HttpsURLConnection {

    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    protected CustomURLConnection(URL url) {
        super(url);
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public String getCipherSuite() {
        return null;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return new Certificate[0];
    }

    @Override
    public void connect() throws IOException {
    }
}
