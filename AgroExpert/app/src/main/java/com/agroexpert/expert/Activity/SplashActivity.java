package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.Utility;


public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (Utility.get_uid(getApplicationContext()).toString().equalsIgnoreCase("")){
                    Intent i = new Intent(getApplicationContext(), ActivitySelectLanguage.class);
                    startActivity(i);
                    finish();
                }else {

                    String lang = Utility.get_lang(getApplicationContext());
                    Utility.setLocale(getApplicationContext(),lang);

                    Intent i = new Intent(getApplicationContext(), HomeTabActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}