package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;

import java.util.Locale;


public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

       /* Locale[] locales = Locale.getAvailableLocales();
        for(int i=0;i<locales.length;i++)
            Log.e("Localelist", String.valueOf(locales[i]));
*/

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                if (Utility.get_uid(getApplicationContext()).toString().equalsIgnoreCase("")){
                    Intent i = new Intent(getApplicationContext(), ActivitySelectLanguage.class);
                    startActivity(i);
                    finish();
                }else {

                    String lang = Utility.get_lang(getApplicationContext());
                    Utility.setLocale(getApplicationContext(),lang);

                    Intent i = new Intent(getApplicationContext(), HomeTabActivity.class);
                    i.putExtra("i",0);
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}