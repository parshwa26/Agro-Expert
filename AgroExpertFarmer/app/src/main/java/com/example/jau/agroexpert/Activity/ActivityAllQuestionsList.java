package com.example.jau.agroexpert.Activity;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.Adapter.AdapterMyQuestionActivity;
import com.example.jau.agroexpert.Bean.BeanSelectMyQuestion;
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

/**
 * Created by Jaina on 19/03/18.
 */

public class ActivityAllQuestionsList extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    static RecyclerView rv_home_activity;
    private static RequestQueue mRequestQueue;
    static ArrayList<BeanSelectMyQuestion> beanSelectMyQuestions = new ArrayList<>();
    static AdapterMyQuestionActivity adapter;
    static Activity activity;
    LinearLayout lnback;
    ArrayList<BeanSelectMyQuestion> beanStarredQuestion = new ArrayList<>();


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        activity = this;

        //rv_home_activity = (RecyclerView) findViewById(R.id.rv_home_activity);
        lnback = (LinearLayout) findViewById(R.id.lnback);

        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));
        beanStarredQuestion.add(new BeanSelectMyQuestion("This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"," ","Answer is to be written here This is text box which displays the question. Whole Question Goes here. No matter how long the question is it will displayed here. Length does not matter while uploading question as this is demo question"));


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

        adapter = new AdapterMyQuestionActivity(beanSelectMyQuestions,getApplicationContext(),"All Questions");
        rv_home_activity.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRefresh() {
        get_all_question();
    }


    public static void get_all_question(){

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

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lid",""+ Utility.get_uid(activity.getApplicationContext()));

                return map;
            }
        };

        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


    }

}
