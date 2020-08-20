package com.example.jau.agroexpert.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.Adapter.AdapterSubCategory;
import com.example.jau.agroexpert.Bean.BeanSubCategory;
import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class WelcomeActivity extends AppCompatActivity {

    com.uncopt.android.widget.text.justify.JustifiedTextView tv_welcome;
    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_welcome = findViewById(R.id.tv_welcome);
        cont = findViewById(R.id.cont);
        getWelcomeMessage();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }



    public void getWelcomeMessage(){

        //pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST , AppConstants.welcome, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("welcome");


                    if (jsonArray.length() != 0) {
                        Log.e("JSON String",jsonArray.getJSONObject(0).getString("message").toString());
                        tv_welcome.setText(jsonArray.getJSONObject(0).getString("message").toString());


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //pd.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
               // pd.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));
                map.put("key",AppConstants.key);

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));

                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(getApplicationContext()).add(stringRequest);

    }

}
