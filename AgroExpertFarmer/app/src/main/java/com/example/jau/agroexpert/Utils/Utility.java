package com.example.jau.agroexpert.Utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jau.agroexpert.R;

import java.util.Locale;

/**
 * Created by Jaina on 17/03/18.
 */

public class Utility {

    private static ProgressDialog pd;
    private static SharedPreferences preferences;
    private static RequestQueue mRequestQueue;



    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Log.e("setLocale",lang);
    }


    public static boolean checkConnectivity(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (cm == null) {
                return false;
            } else if (info == null) {
                return false;
            } else if (info.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static ProgressDialog getDialog(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setMessage("Please Wait...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        return pd;
    }


    public static void get_preference_class(Context con){
    }


    public static String get_uid(Context con) {
        preferences=null;
        preferences = con.getSharedPreferences("IDPreference", Context.MODE_PRIVATE);
        String s = preferences.getString("uid", "");
        return s;
    }


    public static void set_uid(Context con,String s) {
        preferences=null;
        preferences = con.getSharedPreferences("IDPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", s);
        editor.commit();
    }


    public static String get_status(Context context) {
        preferences = null;
        preferences = context.getSharedPreferences("Status",0);
        String s = preferences.getString("uid", null);
        return s;
    }


    public static void set_status(Context context,String s) {
        preferences = null;
        preferences = context.getSharedPreferences("Status",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", "");
        editor.commit();
    }


    public static void set_lang(Context context,String s,int l) {
        setLocale(context,s);
        preferences = null;
        preferences = context.getSharedPreferences("UserLanguage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lang", s);
        editor.putString("lang_no", String.valueOf(l));
        editor.commit();
    }


    public static String get_lang(Context context) {
        preferences = null;
        preferences = context.getSharedPreferences("UserLanguage", Context.MODE_PRIVATE);
        String s = preferences.getString("lang", "");
        return s;
    }

    public static String get_lang_no(Context context) {
        preferences = null;
        preferences = context.getSharedPreferences("UserLanguage", Context.MODE_PRIVATE);
        String s = preferences.getString("lang_no", "");
        return s;
    }


    public static String get_phone(Context context) {
        preferences = null;
        preferences = context.getSharedPreferences("UserPhone", Context.MODE_PRIVATE);
        String s = preferences.getString("phone_no", "");
        return s;
    }


    public static void set_phone(Context context,String s) {
        preferences = null;
        preferences = context.getSharedPreferences("UserPhone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone_no", s);
        editor.commit();
    }

    public static String get_district(Context context) {
        preferences = null;
        preferences = context.getSharedPreferences("UserDistrict", Context.MODE_PRIVATE);
        String s = preferences.getString("district", "");
        return s;
    }

    public static void set_district(Context context,String s) {
        preferences = null;
        preferences = context.getSharedPreferences("UserDistrict", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("district", s);
        editor.commit();
    }

    public static void SetvollyTime30Sec(StringRequest request) {
        int socketTimeout = 600000;//30 seconds waiting time
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if(!(phone.length()==10)) {
            // if(phone.length() != 10) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }





}
