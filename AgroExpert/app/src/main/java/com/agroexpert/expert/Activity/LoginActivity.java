package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.agroexpert.expert.Utils.Utility.checkConnectivity;

public class LoginActivity extends Activity {

    Button btn_verify;
    TextView tv_forget_pass;
    EditText edt_phone_number;
    Random rand;
    String lang;
    String otp;
    Activity activity;
    ProgressDialog pd;

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

        edt_phone_number = findViewById(R.id.edt_phone_number);
        btn_verify = findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_phone_number.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter Phone number",Toast.LENGTH_LONG).show();
                }else {
                    if(Utility.isValidMobile(edt_phone_number.getText().toString())){
                    //    Login(edt_phone_number.getText().toString(),lang,otp," ");
                        login_otp(edt_phone_number.getText().toString(),otp);
                        }else {
                        Toast.makeText(getApplicationContext(),"Enter Valid Phone Number",Toast.LENGTH_LONG).show();
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
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.exlogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response",response);
                    try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("Registration")) {

                        JSONArray array1 = object.getJSONArray("Registration");
                                                //   JSONArray array = object.getJSONArray("Registration")
                            if(array1.getJSONObject(0).getString("status").toString().equalsIgnoreCase("success")){
                                StringRequest request = new StringRequest(Request.Method.POST, AppConstants.otpverify, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
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

                                pd.dismiss();
                                Intent intent = new Intent(getApplicationContext(),EnterOTPActivity.class);
                                intent.putExtra("otp",otp);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                pd.dismiss();
                                showPromptDlg1();
                            }
                        }
                        }
                        catch (JSONException e) {
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

                    map.put("otp", otp);
                    map.put("edt_phone_number",phone);
                    map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));

                    Log.e("lang_flag","-> " + lang);
                    Log.e("otp_verify","Request");
                    Log.e("edt_phone_number","-> " + phone);
                    Log.e("otp", otp);



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

    private void showPromptDlg1() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText("AGRO EXPERT")
                .setContentText("This number is not registered.Contact agroexpert@jau.in to register")
                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
