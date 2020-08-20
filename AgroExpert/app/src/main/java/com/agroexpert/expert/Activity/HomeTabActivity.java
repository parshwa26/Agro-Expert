package com.agroexpert.expert.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;

import com.agroexpert.expert.AboutUs;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.ContextWrapper;
import com.agroexpert.expert.Utils.Utility;

import java.util.Locale;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.agroexpert.expert.Utils.Utility.checkConnectivity;

public class HomeTabActivity extends Activity {

    CardView about_us,cv_ask_question,cv_profile,cv_other,cv__answeresd_questions,cv_unanswered_questions,cv_search_question,cv_starred_question,cv_faq;
    int permissions_code = 42;

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utility.setLocale(getApplicationContext(),Utility.get_lang(getApplicationContext()));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},permissions_code);
        }

        if (checkConnectivity(getApplicationContext())) {

        }
        else {
            showPromptDlg();
        }

//        cv_profile = findViewById(R.id.cv_profile);
//        cv_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkConnectivity(getApplicationContext())) {
//                    startActivity(new Intent(HomeTabActivity.this,ProfileActivity.class));
//                    finish();
//
//                }
//                else {
//                    showPromptDlg();
//                }
//            }
//        });
        cv_other = findViewById(R.id.cv_other);
        cv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConnectivity(getApplicationContext())) {
                    startActivity(new Intent(HomeTabActivity.this,Activity_other.class));

                }
                else {
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

        cv_unanswered_questions = findViewById(R.id.unanswered_questions);
        cv_unanswered_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                    intent.putExtra("Flag","PQ");
                    startActivity(intent);

                }
                else {
                    showPromptDlg();
                }
            }
        });

        cv__answeresd_questions = findViewById(R.id.answered_questions);
        cv__answeresd_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConnectivity(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                    intent.putExtra("Flag","AQ");
                    startActivity(intent);
                }
                else {
                    showPromptDlg();
                }
            }
        });

        cv_search_question = findViewById(R.id.cv_search_question);
        cv_search_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityQuestionsList.class);
                intent.putExtra("Flag","SQ");
                startActivity(intent);
            }
        });

        cv_starred_question = findViewById(R.id.cv_starred_question);
        cv_starred_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityQuestionShortLIst.class);
                intent.putExtra("Flag","STQ");
                startActivity(intent);
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