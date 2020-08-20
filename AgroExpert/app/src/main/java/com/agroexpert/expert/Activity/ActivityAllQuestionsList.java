package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.agroexpert.expert.Utils.ContextWrapper;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.agroexpert.expert.Adapter.AdapterMyQuestionActivity;
import com.agroexpert.expert.Bean.BeanSelectMyQuestion;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.Utility;
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

/**
 * Created by Jaina on 19/03/18.
 */

public class ActivityAllQuestionsList extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    static RecyclerView rv_home_activity;
    FloatingActionButton fab;
    private static RequestQueue mRequestQueue;
    static ArrayList<BeanSelectMyQuestion> beanSelectMyQuestions = new ArrayList<>();
    static AdapterMyQuestionActivity adapter;
    static SwipeRefreshLayout swipeRefreshLayout;
    static Activity activity;
    LinearLayout lnback;

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        activity = this;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        lnback = (LinearLayout) findViewById(R.id.lnback);

        lnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get_all_question();

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home_activity.setLayoutManager(llm);

        adapter = new AdapterMyQuestionActivity(beanSelectMyQuestions,getApplicationContext());
        rv_home_activity.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {}
                                }
        );

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRefresh() {
        get_all_question();
    }


    public void get_all_question(){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.selectallquestion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Select_Question","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("selectquestion");

                    if (jsonArray != null) {

                        beanSelectMyQuestions.clear();
                        beanSelectMyQuestions.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>(){}.getType()));

                    }
                    rv_home_activity.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Select-All-Question", "Error at sign in : " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("lid", Utility.get_uid(activity.getApplicationContext()));
                map.put("key",AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));
                Log.e("lid",""+ Utility.get_uid(activity.getApplicationContext()));

                return map;
            }
        };

        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);

    }

}
