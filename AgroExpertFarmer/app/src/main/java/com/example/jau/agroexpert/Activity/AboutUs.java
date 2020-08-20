package com.example.jau.agroexpert.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;


import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;
import java.util.Locale;


public class AboutUs extends AppCompatActivity {

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
}

