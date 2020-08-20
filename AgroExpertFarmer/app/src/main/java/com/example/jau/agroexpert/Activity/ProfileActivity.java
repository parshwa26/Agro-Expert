package com.example.jau.agroexpert.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.jau.agroexpert.Adapter.AdapterCategory;
import com.example.jau.agroexpert.Adapter.AdapterDistrict;
import com.example.jau.agroexpert.Bean.BeanCategory;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.jau.agroexpert.Activity.ActivityAllQuestionsList.activity;
import static com.example.jau.agroexpert.Utils.AppConstants.district;
import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;


public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText edt_fname, edt_lname, edt_phone;
    Button Login_btn;
    Spinner spn_district, spn_language;
    ArrayList<BeanDistrict> beanDistricts = new ArrayList<>();
    AdapterDistrict adapterDistrict;
    String did, district_name;
    int language;
    ProgressDialog pd;

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = this;
        pd = Utility.getDialog(activity);
        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
        pd.show();

        getdistrict();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Toast.makeText(ProfileActivity.this, "delay", Toast.LENGTH_SHORT);
            }
        }, 10000);

        pd.dismiss();
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_phone = findViewById(R.id.edt_phone);
        Login_btn = findViewById(R.id.Login_btn);

        spn_district = (Spinner) findViewById(R.id.spn_district);
        spn_language = (Spinner) findViewById(R.id.spn_language);


        if (checkConnectivity(getApplicationContext())) {
            pd.show();
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.profiledetails, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("RESPONSE", "" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.e("obj", obj.toString());
                        JSONArray obj1 = obj.getJSONArray("profile");
                        Log.e("obj1", obj1.toString());
                        if (obj1.getJSONObject(0).getString("status").equalsIgnoreCase("success")) {
                            pd.dismiss();
                            Log.e("Displaay profile ->", response);
                            Log.e("Displaay profile ->", obj1.getJSONObject(0).getString("fname"));
                            edt_fname.setText(obj1.getJSONObject(0).getString("fname"));
                            edt_lname.setText(obj1.getJSONObject(0).getString("lname"));
                            edt_phone.setText(obj1.getJSONObject(0).getString("phoneno"));
                            spn_language.setSelection(Integer.parseInt(obj1.getJSONObject(0).getString("lang_flag")) - 1);
                            spn_district.setSelection(Integer.parseInt(obj1.getJSONObject(0).getString("district")) - 1);
                            Log.e("district", obj1.getJSONObject(0).getString("district"));
                            Log.e("language", obj1.getJSONObject(0).getString("lang_flag"));


                        }


                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("unsuccess", error.toString());

                    pd.dismiss();
                    try {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("VolleyError", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("VolleyError", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("VolleyError", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("VolleyError", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("VolleyError", "ParseError");
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
                    map.put("key", AppConstants.key);
                    map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));
                    map.put("edit", "display");


                    Log.e("SHA-1 Key", AppConstants.key);
                    Log.e("update_district", "Request");
                    Log.e("lid", "-> " + Utility.get_uid(getApplicationContext()));
                    Log.e("district", "-> " + did);
                    Log.e("lang_flag", "-> " + Utility.get_lang_no(getApplicationContext()));
                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);
            pd.dismiss();

        }



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

        spn_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                language = position;

                switch (language) {
                    case 0:
                        Utility.set_lang(getApplicationContext(), "gu", 1);
                        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
                        break;
                    case 1:
                        Utility.set_lang(getApplicationContext(), "hi", 2);
                        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
                        break;
                    case 2:
                        Utility.set_lang(getApplicationContext(), "en", 3);
                        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> categorie = new ArrayList<String>();
        categorie.add("ગુજરાતી");
        categorie.add("हिन्दी");
        categorie.add("English");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorie);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//         attaching data adapter to spinner
        spn_language.setAdapter(dataAdapter1);

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfile(edt_fname.getText().toString(), edt_lname.getText().toString(), edt_phone.getText().toString(), did);
            }
        });
    }



    private void addProfile(final String fname, final String lname, final String phone, final String did) {


        if (checkConnectivity(getApplicationContext())) {

            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.profiledetails, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Registeration ->", response);
                    try {
                        JSONObject obj = new JSONObject(response);

                        JSONArray obj1 = obj.getJSONArray("profile");
                        if (obj1.getJSONObject(0).getString("status").equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), "profile successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), HomeTabActivity.class);
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
                    Log.e("unsuccess", error.toString());
                    pd.dismiss();
                    try {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("VolleyError", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("VolleyError", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("VolleyError", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("VolleyError", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("VolleyError", "ParseError");
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
                    map.put("district", did);
                    map.put("key", AppConstants.key);
                    map.put("fname", fname);
                    map.put("lname", lname);
                    map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));
                    map.put("edit", "update");
                    map.put("phoneno", phone);


                    Log.e("SHA-1 Key", AppConstants.key);
                    Log.e("update_district", "Request");
                    Log.e("lid", "-> " + Utility.get_uid(getApplicationContext()));
                    Log.e("district", "-> " + did);
                    Log.e("fname", "-> " + fname);
                    Log.e("lname", "-> " + lname);
                    Log.e("lang_flag", "-> " + Utility.get_lang_no(getApplicationContext()));
                    Log.e("mobile", "-> " + phone);
                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            pd.dismiss();
        }
    }

    public void getdistrict() {
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.districtlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("districtlist");

                    if (jsonArray != null) {

                        beanDistricts.clear();
                        beanDistricts.addAll((Collection<? extends BeanDistrict>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanDistrict>>() {
                        }.getType()));

                        adapterDistrict = new AdapterDistrict(beanDistricts, getApplicationContext());
                        spn_district.setAdapter(adapterDistrict);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("lid", Utility.get_uid(getApplicationContext()));
                map.put("district", district);
                map.put("key", AppConstants.key);
                map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));

                Log.e("SHA-1 Key", AppConstants.key);
                Log.e("update_district", "Request");
                Log.e("lid", "-> " + Utility.get_uid(getApplicationContext()));
                Log.e("district", "-> " + district);


                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(getApplicationContext()).add(stringRequest);
        pd.dismiss();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
