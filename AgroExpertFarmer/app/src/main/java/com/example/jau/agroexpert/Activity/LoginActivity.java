/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.AgroExpert;
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;


public class LoginActivity extends AppCompatActivity{

    Button btn_verify;
    TextView tv_forget_pass;
    EditText edt_phone_number;
    Random rand;
    String lang;
    String otp;
    Activity activity;
    ProgressDialog pd;
    private SmsVerifyCatcher smsVerifyCatcher;


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;
        pd = Utility.getDialog(activity);


        Random rand = new Random();
        otp = String.format("%04d", rand.nextInt(10000));
        Log.e("otp",otp);
        lang = Utility.get_lang(getApplicationContext());


        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {

            }
        });



        edt_phone_number = findViewById(R.id.edt_phone_number);
        btn_verify = findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_phone_number.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter Phone number",Toast.LENGTH_SHORT).show();
                }else {
                    if(Utility.isValidMobile(edt_phone_number.getText().toString())){
                    //    Login(edt_phone_number.getText().toString(),lang,otp," ");
                        login_otp(edt_phone_number.getText().toString(),otp);
                    }else {
                        Toast.makeText(getApplicationContext(),"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public void login_otp(final String phone,final String otp) {
        pd.show();
        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.otpverify, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("Response",response);
                    pd.dismiss();
                    Intent intent = new Intent(getApplicationContext(),EnterOTPActivity.class);
                    intent.putExtra("otp",otp);
                    intent.putExtra("phone",phone);
                    intent.putExtra("edt_phone_number", String.valueOf(edt_phone_number));
                    startActivity(intent);
                    finish();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Login_Error", error.toString());
                    pd.dismiss();
                    try {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("VolleyError","NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("VolleyError","AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("VolleyError","ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("VolleyError","NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("VolleyError","ParseError");
                        }
                    } catch (Exception e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();

                    map.put("otp", otp);
                    map.put("edt_phone_number",phone);
                    map.put("key",AppConstants.key);

                    Log.e("otp_verify","Request");
                    Log.e("edt_phone_number","-> " + phone);
                    Log.e("otp", otp);
                    Log.e("SHA-1 Key",AppConstants.key);



                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            showPromptDlg();
        }
    }

    private void showPromptDlg() {
        pd.dismiss();
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

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
