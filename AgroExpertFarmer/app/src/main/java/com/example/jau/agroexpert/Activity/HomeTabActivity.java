package com.example.jau.agroexpert.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;

import java.util.Locale;

import cn.refactor.lib.colordialog.PromptDialog;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;
import static com.google.android.gms.auth.api.signin.GoogleSignIn.hasPermissions;

public class HomeTabActivity extends Activity {

    LinearLayout lndrawer;
    CardView cv_ask_question,about_us,cv_profile,cv__answeresd_questions,cv_unanswered_questions,cv_search_question,cv_starred_question,cv_faq,cv_info;
    int permissions_code = 42;


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Utility.setLocale(getApplicationContext(),);


       /* Log.e("Camera", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)));
        Log.e("Audio", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)));
        Log.e("REAd", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)));
        Log.e("Write", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)));
        Log.e("Granted =", String.valueOf(PackageManager.PERMISSION_GRANTED));
        Log.e("Value of IF", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED));

            // Log.e("After Perms","LOGGED");*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},permissions_code);
        }



        if(checkConnectivity(getApplicationContext())){

        }else {
            showPromptDlg();
        }


        cv_ask_question = findViewById(R.id.ask_question);
        cv_ask_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),SelectSubCategory.class);

                    intent.putExtra("c_id","0");
                    //intent.putExtra("is_next","success");
                    intent.putExtra("catList","");

                    startActivity(intent);
                    finish();
                }else {
                    showPromptDlg();
                }
            }
        });

        cv_profile = findViewById(R.id.cv_profile);
        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnectivity(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                }else {
                    showPromptDlg();
                }
            }
        });


        cv_unanswered_questions = findViewById(R.id.unanswered_questions);
        cv_unanswered_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                    intent.putExtra("Flag","PQ");
                    startActivity(intent);
                }else {
                    showPromptDlg();
                }


            }
        });

        cv__answeresd_questions = findViewById(R.id.answered_questions);
        cv__answeresd_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                    intent.putExtra("Flag","AQ");
                    startActivity(intent);
                }else {
                    showPromptDlg();
                }


            }
        });

        cv_search_question = findViewById(R.id.cv_search_question);
        cv_search_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionsList.class);
                    intent.putExtra("Flag","SQ");
                    intent.putExtra("c_id","1");
                    startActivity(intent);
                }else {
                    showPromptDlg();
                }

            }
        });

        cv_starred_question = findViewById(R.id.cv_starred_question);
        cv_starred_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                    intent.putExtra("Flag","STQ");
                    startActivity(intent);
                }else {
                    showPromptDlg();
                }

            }
        });

        cv_faq = findViewById(R.id.cv_faq);
        cv_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionsList.class);
                    intent.putExtra("Flag","FAQ");
                    startActivity(intent);
                }else {
                    showPromptDlg();
                }
            }
        });

        about_us = findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConnectivity(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(),AboutUs.class));
                }
                else {
                    showPromptDlg();
                }
            }
        });

        cv_info = findViewById(R.id.cv_info);
        cv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),InformationActivity.class);
                    //intent.putExtra("Flag","FAQ");
                    intent.putExtra("c_id","0");
                    intent.putExtra("is_next","success");
                    intent.putExtra("pos",0);

                    startActivity(intent);
                }else {
                    showPromptDlg();
                }
            }
        });



    }





    @Override
    public void onBackPressed() {
        finish();
    }

    //UQ -> Unanswered Questions
    //AQ -> Answered Questions


    private void showPromptDlg() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("AGRO EXPERT")
                .setContentText("Please Check Your Internet Connection")
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

}