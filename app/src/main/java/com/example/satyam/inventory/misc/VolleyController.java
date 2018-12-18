package com.example.satyam.inventory.misc;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

import static android.R.attr.tag;

/**
 * Created by kshivang on 17/12/16.
 *
 */

public class VolleyController {

    private static VolleyController mInstance;
    private String TAG = "G";
    private RequestQueue mRequestQueue;
    private Context mContext;

    private VolleyController(Context context) {
        mContext = context;
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyController getInstance (Context context) {
        if (mInstance == null) {
            mInstance = new VolleyController(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        //req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tab) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}


