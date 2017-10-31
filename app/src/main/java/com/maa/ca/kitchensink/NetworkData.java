/**
 * Copyright (C) 2015, CA.  All rights reserved.
 */
package com.maa.ca.kitchensink;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chaitanya Kannali on 5/20/15.
 */
public class NetworkData {

    public String data;
    public String requestUrl;
    public String method;
    public String networkCallType;
    public URL aURL;
    public boolean isCustomUrl;
    public String size;

    public URL getURL() {
        if (aURL != null) {
            return aURL;
        }
        URL aURL = null;
        try {
            aURL = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return aURL;
    }

    public String getHostName() {
        return getURL().getHost();
    }

    public int getPort() {
        return getURL().getPort();
    }

    /**
     * Make it html so text rendered shows up good.
     *
     * @return
     */
    @Override
    public String toString() {
        String requrl = " of <a href=" + "\"requestUrl :\"" + " >" + requestUrl + "</a>";
        return "Call  " + method + (isCustomUrl ? requrl : requrl + " with :<i>" + size + "</i>") + " using <b>" + networkCallType + "</b>";

    }
}
