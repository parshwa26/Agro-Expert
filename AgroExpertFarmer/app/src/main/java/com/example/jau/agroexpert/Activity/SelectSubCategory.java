package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.Adapter.AdapterCategory;
import com.example.jau.agroexpert.Adapter.AdapterMyQuestionActivity;
import com.example.jau.agroexpert.Adapter.AdapterSubCategory;
import com.example.jau.agroexpert.Adapter.AdepterQuestionShortList;
import com.example.jau.agroexpert.Bean.BeanCategory;
import com.example.jau.agroexpert.Bean.BeanSelectMyQuestion;
import com.example.jau.agroexpert.Bean.BeanSubCategory;
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

import cn.refactor.lib.colordialog.PromptDialog;


import static com.example.jau.agroexpert.Activity.ActivityQuestionsList.Flag;

public class SelectSubCategory extends AppCompatActivity{


    ProgressDialog pd;
    Activity activity;
    LinearLayout lnback;
    TextView tv_catList;
    AdepterQuestionShortList adepter;
    ListView lst_faq;

    public final String Flag="FAQ";


    String c_id,category_name,is_next;

    ArrayList<BeanCategory> beanCategories;
    AdapterCategory adapterCategory;
    ArrayList<BeanSubCategory> beanSubCategories;
    static ArrayList<BeanSelectMyQuestion> beanSelectFAQS = new ArrayList<>();


    AdapterSubCategory adapterSubCategory;
    static String catList;

    //ListView lst_category;
    ListView lst_sub_category;


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub_category);

        Bundle b = getIntent().getExtras();
        c_id = b.getString("c_id");
        category_name = b.getString("category_name");

        catList = b.getString("catList");
        Log.e("catList",catList);

        lnback = (LinearLayout) findViewById(R.id.lnback);
        tv_catList = (TextView) findViewById(R.id.tv_catList);
        activity = this;
        pd = Utility.getDialog(activity);
        lst_sub_category = findViewById(R.id.lst_sub_category);
        lst_faq = findViewById(R.id.lst_faq);

        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));




        beanSubCategories = new ArrayList<>();

        getSubCatList(c_id);
        get_faq_questions(c_id);

        if(!(c_id.equalsIgnoreCase("0"))){
            tv_catList.setVisibility(View.VISIBLE);
        }
        lst_sub_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                c_id = beanSubCategories.get(position).pid;
                category_name = beanSubCategories.get(position).category_flag;
                //category_name = beanCategories.get(position).status;


                    Intent intent = new Intent(getApplicationContext(),SelectSubCategory.class);
                    intent.putExtra("c_id",c_id);

                    if(catList.equalsIgnoreCase("")){
                        catList += category_name;
                        intent.putExtra("catList",catList);

                    }
                    else{
                        catList += "-->"+category_name;
                        intent.putExtra("catList",catList);

                    }

                    startActivity(intent);
                    finish();


            }
        });


        lnback.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_catList.setText(catList);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
        startActivity(intent);
        finish();
    }

    public void getSubCatList(final String cid){

        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST , AppConstants.subcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("subcategory");
                    //Log.e("subcat array", String.valueOf(jsonArray != null));


                    if (jsonArray.length() != 0) {
                        Log.e("JSON String",jsonArray.getJSONObject(0).getString("status").toString());

                        beanSubCategories.clear();
                        beanSubCategories.addAll((Collection<? extends BeanSubCategory>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSubCategory>>(){}.getType()));

                        adapterSubCategory = new AdapterSubCategory(beanSubCategories,getApplicationContext());
                        lst_sub_category.setAdapter(adapterSubCategory);

                        pd.dismiss();
//                        Log.e("out of if",is_next);

                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),ActivityAddQuestion.class);
                        intent.putExtra("c_id",c_id);
                        intent.putExtra("catList",catList);
                        Log.e("catList",catList);

                        startActivity(intent);
                        finish();
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
                map.put("cid",cid);
                map.put("lid", Utility.get_uid(getApplicationContext()));
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));
                map.put("key",AppConstants.key);

                Log.e("SHA-1 Key",AppConstants.key);
                //Log.e("","");
                Log.e("cid",cid);
                Log.e("pid",Utility.get_uid(getApplicationContext()));
                Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));

                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(getApplicationContext()).add(stringRequest);

    }

    public void get_faq_questions(final String cid){

        //   pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.faqlist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Search_Questions","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("searchfaq");
                    Log.e("FAQW JSON Array",jsonArray.toString());
                    //String status=obj.getString("status").toString();

                    //Log.e("Status: ",status);


                    if (jsonArray.length()!=0) {

                        beanSelectFAQS.clear();
                        beanSelectFAQS.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>(){}.getType()));

                        adepter = new AdepterQuestionShortList(beanSelectFAQS,activity,Flag);
                        lst_faq.setAdapter(adepter);
                    }
                    //rv_home_activity.setAdapter(adapter);
                    //pd.dismiss();

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
                map.put("cid",cid);
                map.put("key",AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));

                Log.e("SHA-1 Key",AppConstants.key);
                Log.e("lid",""+ Utility.get_uid(activity.getApplicationContext()));
                Log.e("cid",""+cid);


                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


    }





}




