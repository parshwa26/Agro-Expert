package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.Adapter.AdapterMyQuestionActivity;
import com.example.jau.agroexpert.Adapter.AdepterQuestionShortList;
import com.example.jau.agroexpert.Bean.BeanSelectMyQuestion;
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

import static com.example.jau.agroexpert.Activity.ActivityQuestionsList.beanSelectAnswered;
import static com.example.jau.agroexpert.Activity.ActivityQuestionsList.beanSelectUnAnswered;
import static com.example.jau.agroexpert.Activity.ActivityQuestionsList.beanStarredQuestion;

public class ActivityQuestionShortLIst extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    static Activity activity;
    ListView lv_question;
    static  String Flag;
    static SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView rv_home_activity;
    LinearLayout lnback;
    TextView tv_header;


    AdepterQuestionShortList adepterQuestionShortList;

    static ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_short_list);
        lv_question = findViewById(R.id.lv_question);
        lnback = findViewById(R.id.lnback);
        tv_header = findViewById(R.id.tv_header);
        activity = this;
        pd = Utility.getDialog(activity);
        Bundle bundle = getIntent().getExtras();
        Flag = bundle.getString("Flag");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        rv_home_activity = (RecyclerView) findViewById(R.id.rv_home_activity);
        lnback = (LinearLayout) findViewById(R.id.lnback);
        tv_header = findViewById(R.id.tv_header);

        lnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_home_activity.setLayoutManager(llm);
//
        swipeRefreshLayout.setOnRefreshListener(ActivityQuestionShortLIst.this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                }
        );

        if (Flag.equalsIgnoreCase("PQ")) {
            tv_header.setText(getApplicationContext().getResources().getString(R.string.pending_questions));
            // get_question();
            get_unanswered_questions();
        } else if (Flag.equalsIgnoreCase("AQ")) {
            tv_header.setText(getApplicationContext().getResources().getString(R.string.answered_questions));
            get_answered_questions();
        }
//        else if(Flag.equalsIgnoreCase("SQ")){
//            tv_header.setText(getApplicationContext().getResources().getString(R.string.search_question));
//            spn_category.setVisibility(View.VISIBLE);
//            getCatList();
//            get_search_questions(c_id);
//    }


        else if (Flag.equalsIgnoreCase("STQ")) {
            tv_header.setText(getApplicationContext().getResources().getString(R.string.starred_questions));
            get_starred_questions();
          /*  adapter = new AdapterMyQuestionActivity(beanStarredQuestion,getApplicationContext(),Flag);
            rv_home_activity.setAdapter(adapter);*/
        } else if (Flag.equalsIgnoreCase("FAQ")) {
            tv_header.setText(getApplicationContext().getResources().getString(R.string.FAQs));
            //get_faq_questions();


        }
    }


        @Override
        public void onRefresh () {
            if (Flag.equalsIgnoreCase("UQ")) {
              //  get_question();
            } else if (Flag.equalsIgnoreCase("AQ")) {
                get_answered_questions();
            } else if (Flag.equalsIgnoreCase("SQ")) {
                // get_search_questions();
                swipeRefreshLayout.setRefreshing(false);
            } else if (Flag.equalsIgnoreCase("STQ")) {
                get_starred_questions();
                swipeRefreshLayout.setRefreshing(false);

            } else if (Flag.equalsIgnoreCase("FAQ")) {
              //  get_faq_questions();
            }
        }

    public void onBackPressed() {
        finish();
    }


        private void get_answered_questions () {
            swipeRefreshLayout.setRefreshing(true);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.answeredquestion, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    pd.show();
                    Log.e("Answered_Question", "" + response);

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("answerquestion");

                        if (jsonArray != null) {

                            beanSelectAnswered.clear();
                            beanSelectAnswered.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>() {
                            }.getType()));
                            adepterQuestionShortList = new AdepterQuestionShortList(beanSelectAnswered, ActivityQuestionShortLIst.this,Flag);
                            lv_question.setAdapter(adepterQuestionShortList);
                        }
                        //rv_home_activity.setAdapter(adapter);
                        pd.dismiss();

                          swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Select_Question", "Error at sign in : " + error.getMessage());
                    pd.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("lid", Utility.get_uid(activity.getApplicationContext()));
                    map.put("key", AppConstants.key);
                    map.put("lang_flag", Utility.get_lang_no(getApplicationContext()));


                    Log.e("SHA-1 Key", AppConstants.key);
                    Log.e("lid", "" + Utility.get_uid(activity.getApplicationContext()));

                    return map;
                }
            };

            Utility.SetvollyTime30Sec(stringRequest);
            Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


        }

    public void get_unanswered_questions(){

        swipeRefreshLayout.setRefreshing(true);
        //   pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.unansweredquestion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.show();

                Log.e("UnAnswered_Question","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("unanswerquestion");

                    if (jsonArray != null) {

                        beanSelectUnAnswered.clear();
                        beanSelectUnAnswered.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>(){}.getType()));

                        adepterQuestionShortList = new AdepterQuestionShortList(beanSelectUnAnswered, ActivityQuestionShortLIst.this, Flag);
                        lv_question.setAdapter(adepterQuestionShortList);
                    }
                    //rv_home_activity.setAdapter(adapter);
                    pd.dismiss();

                   swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Select_Question", "Error at sign in : " + error.getMessage());
                pd.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("lid", Utility.get_uid(activity.getApplicationContext()));
                map.put("key",AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));


                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lid",""+ Utility.get_uid(activity.getApplicationContext()));

                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


    }

    public void get_starred_questions(){

        swipeRefreshLayout.setRefreshing(true);
        //   pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.starredlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Starred_Question","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("answerquestion");

                    if (jsonArray != null) {

                        beanStarredQuestion.clear();
                        beanStarredQuestion.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>(){}.getType()));

                        adepterQuestionShortList = new AdepterQuestionShortList(beanStarredQuestion, ActivityQuestionShortLIst.this, Flag);
                        lv_question.setAdapter(adepterQuestionShortList);
//                        adapter = new AdapterMyQuestionActivity(beanStarredQuestion,activity,Flag);
//                        rv_home_activity.setAdapter(adapter);
                    }
                    //rv_home_activity.setAdapter(adapter);
                    //pd.dismiss();

                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Select_Question", "Error at sign in : " + error.getMessage());
                pd.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("lid", Utility.get_uid(activity.getApplicationContext()));
                map.put("key",AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lid",""+ Utility.get_uid(activity.getApplicationContext()));



                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


    }

    }

