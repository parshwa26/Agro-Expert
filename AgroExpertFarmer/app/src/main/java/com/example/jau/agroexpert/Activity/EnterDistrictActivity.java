package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.jau.agroexpert.Adapter.AdapterDistrict;
import com.example.jau.agroexpert.Bean.BeanDistrict;
import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;

/**
 * Created by Jaina on 12/03/18.
 */

public class EnterDistrictActivity extends Activity {

    EditText edt_district;
    Button btn_submit;
    String lang;
    Activity activity;
    Spinner spn_district;
    ProgressDialog pd;
    ArrayList<BeanDistrict> beanDistricts = new ArrayList<>();
    AdapterDistrict adapterDistrict;
    String did,district_name;

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_district);
        activity = this;
        pd = Utility.getDialog(activity);
        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
        getdistrict();


        spn_district = findViewById(R.id.spn_district);
        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                did = beanDistricts.get(position).did;
                district_name = beanDistricts.get(position).district;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_district = findViewById(R.id.edt_district);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_district(did);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void add_district(final  String district) {
        pd.show();
        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.updatedistrict, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Registeration ->", response);
                    try {
                        JSONObject obj = new JSONObject(response);
                      /*  JSONObject obj1 = obj.getJSONObject("updatedistrict");
                        if(obj1.getString("status").equalsIgnoreCase("District Updated")){
                            Toast.makeText(getApplicationContext(),"SuccessFull Login",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
                            startActivity(intent);
                            finish();

                        }*/


                        JSONArray obj1 = obj.getJSONArray("updatedistrict");
                        if(obj1.getJSONObject(0).getString("status").equalsIgnoreCase("District Updated")){
                            Toast.makeText(getApplicationContext(),"SuccessFull Login",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                            startActivity(intent);
                            finish();

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
                    map.put("lid", Utility.get_uid(getApplicationContext()));
                    map.put("district", district);
                    map.put("key",AppConstants.key);

                    Log.e("SHA-1 Key",AppConstants.key);
                    Log.e("update_district","Request");
                    Log.e("lid","-> " + Utility.get_uid(getApplicationContext()));
                    Log.e("district","-> " +district);


                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            pd.dismiss();
        }
    }

    public void getdistrict(){

        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST , AppConstants.districtlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("districtlist");

                    if (jsonArray != null) {

                        beanDistricts.clear();
                        beanDistricts.addAll((Collection<? extends BeanDistrict>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanDistrict>>(){}.getType()));

                        adapterDistrict = new AdapterDistrict(beanDistricts,getApplicationContext());
                        spn_district.setAdapter(adapterDistrict);

                        pd.dismiss();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
                pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("lid", Utility.get_uid(getApplicationContext()));
                map.put("key",AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));
                Log.e("lid", Utility.get_uid(getApplicationContext()));

                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(getApplicationContext()).add(stringRequest);

    }

}
