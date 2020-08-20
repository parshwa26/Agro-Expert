package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
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
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;

/**
 * Created by Jaina on 12/03/18.
 */

public class EnterOTPActivity extends Activity {

    EditText edt_verify;
    String lang,isNew;
    Button btn_verify;
    ProgressDialog pd;
    Activity activity;
    String text;
    String message_otp,edt_phone_number;
    String otp;
    TextView tv_phone,tv_chng_phn,tv_otp_again,tv_cd_timer;

    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        activity = this;

        pd = Utility.getDialog(activity);

        Bundle b = getIntent().getExtras();
        otp = b.getString("otp");
        final String phone = b.getString("phone");

        btn_verify = findViewById(R.id.btn_verify);
        edt_verify = findViewById(R.id.edt_verify);
        tv_chng_phn = findViewById(R.id.tv_chng_no);
        tv_cd_timer = findViewById(R.id.tv_cd_timer);
        tv_otp_again = findViewById(R.id.tv_otp_again);
        isNew = edt_verify.getText().toString();
        tv_phone = findViewById(R.id.tv_phone);
        tv_phone.setText(phone);




        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                Toast.makeText(getApplicationContext(),"OTP: " + code,Toast.LENGTH_LONG).show();

                edt_verify.setText(code);//set code in edit text
                //then you can send verification code to server
                btn_verify.performClick();
            }
        });


        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_cd_timer.setText( getString(R.string.wait_timer1)+" " + millisUntilFinished / 1000+" "+getString(R.string.wait_timer2));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tv_cd_timer.setVisibility(View.GONE);
                tv_chng_phn.setVisibility(View.VISIBLE);
                tv_otp_again.setVisibility(View.VISIBLE);

            }

        }.start();


        Utility.setLocale(getApplicationContext(),Utility.get_lang(getApplicationContext()));

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp.equalsIgnoreCase(edt_verify.getText().toString())){
                    Login(phone,Utility.get_lang_no(getApplicationContext()),otp,"");
                }else{
                    Toast.makeText(getApplicationContext(),"Enter Valid OTP",Toast.LENGTH_LONG).show();

                }

            }
         });



        tv_chng_phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        tv_otp_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_otp(phone,otp);

            }
        });

    }


    @Override
    public void onBackPressed() {
        finish();
    }


    public void Login(final String phone, final String lang,final String otp,final String district) {
        pd.show();
        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.Registration1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Registeration ->", response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.has("Registration")) {

                            //JSONObject array = object.getJSONObject("Registration");
                            JSONArray array = object.getJSONArray("Registration");

                            if (array.getJSONObject(0).getString("registered").equalsIgnoreCase("Registration Successful")) {
                                JSONArray array1 = array.getJSONObject(0).optJSONArray("detail");
                                // JSONObject array1 =  array.getJSONObject("detail");

                                Utility.set_uid(getApplicationContext(),array1.getJSONObject(0).getString("lid").toString());
                                Utility.set_phone(getApplicationContext(),array1.getJSONObject(0).getString("phoneno").toString());


                                pd.dismiss();
                                Intent intent = new Intent(getApplicationContext(),EnterDistrictActivity.class);
                                intent.putExtra("otp",otp);
                                startActivity(intent);
                                finish();

                                //   login_otp(Utility.get_phone(getApplicationContext()),otp,"new");


                            }else if(array.getJSONObject(0).getString("registered").equalsIgnoreCase("Already Registered")){

                                JSONArray array1 = array.getJSONObject(0).optJSONArray("detail");
                                // JSONObject array1 =  array.getJSONObject("detail");

                                Utility.set_uid(getApplicationContext(),array1.getJSONObject(0).getString("lid").toString());
                                Utility.set_phone(getApplicationContext(),array1.getJSONObject(0).getString("phoneno").toString());
                                Utility.set_district(getApplicationContext(),array1.getJSONObject(0).getString("district").toString());

                                // login_otp(Utility.get_phone(getApplicationContext()),otp,"old");

                                pd.dismiss();
                                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                                intent.putExtra("otp",otp);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                pd.dismiss();
                                Toast.makeText(activity, "Unsucessfull", Toast.LENGTH_LONG).show();
                            }


                            pd.dismiss();
                        }

                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
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
                        } else {
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
                    map.put("edt_phone_number", phone);
                    map.put("lang_flag", lang);
                    map.put("otp", otp);
                    map.put("edt_district","");
                    map.put("key",AppConstants.key);

                    Log.e("LoginRequest","Request");
                    Log.e("edt_phone_number","-> " + phone);
                    Log.e("lang_flag","-> " + lang);
                    Log.e("otp","-> " + otp);
                    Log.e("edt_district","-> null");
                    Log.e("SHA-1 Key",AppConstants.key);



                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            pd.dismiss();
        }
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



    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
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
