package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;

import static com.agroexpert.expert.Utils.Utility.checkConnectivity;


public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView txt_name,txt_email,txt_mobile,txt_zone,txt_unit,txt_district;
    Spinner spn_language;
    ProgressDialog pd;
    Activity activity;
    Button Login_btn;
    int language;


    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),HomeTabActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_mobile = findViewById(R.id.txt_mobile);
        txt_zone = findViewById(R.id.txt_zone);
        txt_unit = findViewById(R.id.txt_unit);
       // txt_district = findViewById(R.id.txt_district);
        Login_btn = findViewById(R.id.Login_btn);
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(language){
                    case 0:
                        Utility.set_lang(getApplicationContext(),"gu",1);
                        Log.e("utility lang",Utility.get_lang_no(getApplicationContext()));
                        Log.e("finish","gu");
                        break;
                    case 1:
                        Utility.set_lang(getApplicationContext(),"hi",2);
                        Log.e("utility lang",Utility.get_lang_no(getApplicationContext()));
                        Log.e("finish","hi");
                        break;
                    case 2:
                        Utility.set_lang(getApplicationContext(),"en",3);
                        Log.e("utility lang",Utility.get_lang_no(getApplicationContext()));
                        Log.e("finish","en");
                        break;
                }

                Toast.makeText(activity, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ProfileActivity.this,HomeTabActivity.class);
                startActivity(intent);
                finish();


            }
        });

        activity = this;
        pd = Utility.getDialog(activity);


        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.profiledetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray obj = jsonObject.getJSONArray("profile");
                        if(obj.getJSONObject(0).getString("status").equalsIgnoreCase("success")){
                            pd.dismiss();
                            Log.e("Displaay profile ->", response);
                            //Log.e("Displaay profile ->", obj.getJSONObject(0).getString("name"));
                            txt_name.setText(obj.getJSONObject(0).getString("name"));
                            txt_email.setText(obj.getJSONObject(0).getString("email"));
                            txt_mobile.setText(obj.getJSONObject(0).getString("phoneno"));
                            txt_zone.setText(obj.getJSONObject(0).getString("zone"));
                            txt_unit.setText(obj.getJSONObject(0).getString("unit"));
                            spn_language.setSelection(Integer.parseInt(Utility.get_lang_no(getApplicationContext()))-1);

                            //txt_district.setText(obj.getJSONObject(0).getString("district"));
                            //Toast.makeText(getApplicationContext(),"Answer posted",Toast.LENGTH_SHORT).show();
                            //finish();

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
                    map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));
                    map.put("key",AppConstants.key);

                    Log.e("lid", Utility.get_uid(getApplicationContext()));
                    Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));
                    Log.e("key",AppConstants.key);

                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            showPromptDlg();
        }

        spn_language = (Spinner) findViewById(R.id.spn_language);

        spn_language.setOnItemSelectedListener(this);
        List<String> categorie = new ArrayList<String>();

        categorie.add("ગુજરાતી");
        categorie.add("હિન્દી");
        categorie.add("English");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorie);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_language.setAdapter(dataAdapter1);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        language=i;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


