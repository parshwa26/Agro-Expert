package com.agroexpert.expert;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agroexpert.expert.Utils.ContextWrapper;
import com.agroexpert.expert.Utils.Utility;

import java.util.Locale;


public class AboutUs extends AppCompatActivity {

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.agroexpert.expert.R.layout.activity_about_us);
    }
}
