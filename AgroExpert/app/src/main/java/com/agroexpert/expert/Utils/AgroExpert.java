package com.agroexpert.expert.Utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;


public class AgroExpert extends MultiDexApplication {

    public static final String TAG = AgroExpert.class.getSimpleName();
    public static Context context;
    public static AgroExpert rest;
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        rest = this;
        context = getApplicationContext();
    }

    public static synchronized AgroExpert getInstance() {
        return rest;
    }

    public static Context getAppContext() {
        return AgroExpert.context;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
