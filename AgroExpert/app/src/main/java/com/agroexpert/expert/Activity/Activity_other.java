package com.agroexpert.expert.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.agroexpert.expert.R;

public class Activity_other extends AppCompatActivity {

    LinearLayout ll_profile,ll_suggesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ll_profile=findViewById(R.id.ll_profile);
        ll_suggesion=findViewById(R.id.ll_suggesions);

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_other.this,ProfileActivity.class));
            }
        });
        ll_suggesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_other.this,Activity_Suggestion.class));
            }
        });
    }
}
