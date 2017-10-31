package com.maa.ca.kitchensink;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//import com.ca.android.app.CaMDOApplication;
//import com.ca.mdo.SDK;
import com.facebook.stetho.Stetho;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by kanch06 on 5/28/15.
 */
public class KitchenSinkApp extends Application implements Application.ActivityLifecycleCallbacks, View.OnTouchListener{


/*
    static URLStreamHandler streamHandler;
    static {
        Class<?> handlerClass;
        try {
            handlerClass = Class.forName("com.android.okhttp.HttpHandler");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading class for default http streamHandler.", e);
        }
        Object handlerInstance;
        try {
            handlerInstance = handlerClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Error instantiating default http streamHandler.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing default http streamHandler.", e);
        }
        if (! (handlerInstance instanceof URLStreamHandler)) {
            throw new RuntimeException("Wrong class type, " + handlerInstance.getClass().getName());
        } else {
            streamHandler = (URLStreamHandler) handlerInstance;
        }

    }*/

    public static final String TAG = KitchenSinkApp.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MYAPPLICATIONTAG", "KitchenSinkApp.onCreate");
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
        //Added for the Sample to trust any certificate.
        trustAllCertificates();
        registerActivityLifecycleCallbacks(this);
        /*URLConnection.setContentHandlerFactory(new ContentHandlerFactory() {
            @Override
            public ContentHandler createContentHandler(String mimetype) {
                return new ContentHandler() {
                    @Override
                    public Object getContent(URLConnection urlc) throws IOException {
                        Log.i("Asda","asdasd");
                        return null;
                    }
                };
            }
        });*/
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("KitchenSinkApp", "KitchenSinkApp.onLowMemory");

    }

    @Override
    public void onTerminate() {
        Log.d("KitchenSinkApp", "KitchenSinkApp.onTerminate");
        super.onTerminate();


    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("KitchenSinkApp", "KitchenSinkApp.onTrimMemory(level)");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("KitchenSinkApp", "KitchenSinkApp.onConfigurationChanged(config)");

    }

    /**
     * Trust all the SSL certificates. This is NOT a recomended practice, but done here for debugging
     * and test purposes.
     */
    private void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");

            sc.init(null, trustAllCerts, new SecureRandom());
            /*HttpsURLConnection.setContentHandlerFactory(new ContentHandlerFactory() {
                @Override
                public ContentHandler createContentHandler(String mimetype) {
                    ContentHandlerFactory.
                    return null;
                }
            });*/

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }



    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: "+activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(TAG, "onActivityStarted: "+activity);
        View rootView = activity.findViewById(android.R.id.content);
        if(rootView!=null){
            rootView.getRootView().setOnTouchListener(this);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed: "+activity);

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityPaused: "+activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i(TAG, "onActivityStopped: "+activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState: "+activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onActivityDestroyed: "+activity);
        View rootView = activity.findViewById(android.R.id.content);
        if(rootView!=null){
            rootView.getRootView().setOnTouchListener(null);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //SDK.getAgent(this).dispatchTouchEventOfActivity(event);
        return false;
    }
}
