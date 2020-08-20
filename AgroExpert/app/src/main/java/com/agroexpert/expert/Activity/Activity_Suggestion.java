package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agroexpert.expert.Adapter.AdapterMyQuestionActivity;
import com.agroexpert.expert.Adapter.AdapterSuggestion;
import com.agroexpert.expert.Bean.BeanSelectMyQuestion;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.agroexpert.expert.Activity.WholeQuestion.activity;
import static com.agroexpert.expert.Activity.WholeQuestion.pd;
import static com.agroexpert.expert.Activity.WholeQuestion.swipeRefreshLayout;

public class Activity_Suggestion extends AppCompatActivity  {

    AdapterSuggestion adapterSuggestion;
    static ArrayList<BeanSelectMyQuestion> beanSelectMyQuestions = new ArrayList<>();
    TextView tv_suggestion;
    static Activity activity;
    LinearLayout lnback;
    static SwipeRefreshLayout swipeRefreshLayout;
    ListView lv_suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__suggestion);
        lv_suggestion=findViewById(R.id.lv_suggestion);
        lnback=findViewById(R.id.lnback);
        activity=this;
        get_suggestion();

        lnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//       {
//           tv_suggestion.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT));
//       }
//       else
//       {
//           tv_suggestion.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
//       }
    }

    public void get_suggestion(){

        //   pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.instruction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("FAQ Response","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("instruction");

                    if (jsonArray != null) {

                        beanSelectMyQuestions.clear();
                        beanSelectMyQuestions.addAll((Collection<? extends BeanSelectMyQuestion>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSelectMyQuestion>>(){}.getType()));

                        adapterSuggestion= new AdapterSuggestion(beanSelectMyQuestions,activity);
                        lv_suggestion.setAdapter(adapterSuggestion);
                    }

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
                map.put("key", AppConstants.key);
                map.put("lang_flag",Utility.get_lang_no(getApplicationContext()));

                Log.e("lang_flag",Utility.get_lang_no(getApplicationContext()));
                Log.e("SHA-1 Key",AppConstants.key);

                return map;
            }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);

    }

}
