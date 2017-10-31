package com.maa.ca.kitchensink;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by sugsh04 on 10/12/17.
 */

class TempJSInterface {

    WeakReference<Handler> handlerHook = null;
    WeakReference<Context> mCtx = null;


    public TempJSInterface(WebView wvWEbPage, Handler handler, Context ctx) {
        handlerHook = new WeakReference<Handler>(handler);
        mCtx = new WeakReference<Context>(ctx);
    }

    @JavascriptInterface
    public void showMessage(final String message) {
        if(handlerHook.get()!=null){
            handlerHook.get().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mCtx.get(),message,Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
