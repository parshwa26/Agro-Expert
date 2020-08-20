package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.agroexpert.expert.Utils.ContextWrapper;
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
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.Utility;
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

import static com.agroexpert.expert.Utils.Utility.checkConnectivity;

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
    String message_otp;
    String otp;
    TextView tv_phone,tv_chng_phn,tv_otp_again,tv_cd_timer;

    private SmsVerifyCatcher smsVerifyCatcher;



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
        tv_phone = findViewById(R.id.tv_phone);
        tv_phone.setText(phone);

        btn_verify = findViewById(R.id.btn_verify);
        edt_verify = findViewById(R.id.edt_verify);
        isNew = edt_verify.getText().toString();

        tv_chng_phn = findViewById(R.id.tv_chng_no);
        tv_cd_timer = findViewById(R.id.tv_cd_timer);
        tv_otp_again = findViewById(R.id.tv_otp_again);




        Utility.setLocale(getApplicationContext(),Utility.get_lang(getApplicationContext()));

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



        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp.equalsIgnoreCase(edt_verify.getText().toString())){
                    login_otp(phone,otp);
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


    public void login_otp(final String phone,final String otp) {
         pd.show();
        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.exlogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Registeration ->", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.has("Registration")) {

                                  JSONArray array1 = object.getJSONArray("Registration");
                             //   JSONArray array = object.getJSONArray("Registration")

                                    Utility.set_uid(getApplicationContext(),array1.getJSONObject(0).getString("lid").toString());
                                    Utility.set_phone(getApplicationContext(),array1.getJSONObject(0).getString("phoneno").toString());

                                    pd.dismiss();
                                    Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
                                    intent.putExtra("otp",otp);
                                    startActivity(intent);
                                    finish();
                            }else{

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
                    map.put("otp", otp);
                    map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));

                    Log.e("lang_flag","-> " + lang);
                    Log.e("LoginRequest","Request");
                    Log.e("edt_phone_number","-> " + phone);
                    Log.e("otp","-> " + otp);


                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            pd.dismiss();
        }
    }
    private void showPromptDlg1() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("AGRO EXPERT")
                .setContentText("This number is not registered.Contact djpatel@jau.in to register")
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
