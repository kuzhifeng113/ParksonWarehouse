package com.woyun.httptools.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Aosen  .
 */
public abstract class HSBackRequestInterface {
    public Context mContext;
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;

    public abstract void backSuccess(String responseResult);

    public abstract void backError(VolleyError error);

    public HSBackRequestInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.mContext = context;
        this.mListener = listener;
        this.mErrorListener = errorListener;

    }

    public Response.Listener<String> loadingListener() {
        this.mListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                backSuccess(s);

            }
        };
        return this.mListener;
    }

    public Response.ErrorListener backErrorListener() {
        this.mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                backError(volleyError);
            }
        };
        return this.mErrorListener;
    }
}
