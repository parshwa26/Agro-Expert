package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.ContextWrapper;
import com.agroexpert.expert.Utils.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.system.Os.mkdir;
import static com.agroexpert.expert.Utils.AppConstants.detailinstruction;

public class ActivityInstruction extends AppCompatActivity {

    WebView ww1,ww2;
    String flag, qid, iid;
    Activity activity;
    LinearLayout lnback;

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        ww1=findViewById(R.id.ww1);
        ww2=findViewById(R.id.ww2);
        lnback=findViewById(R.id.lnback);
        activity = this;
        Bundle bundle = getIntent().getExtras();
        flag = bundle.getString("Flag");
        qid = bundle.getString("qid");
        iid = bundle.getString("iid");
        lnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //ww1.loadUrl("http://online.jau.in:2015/agriex/common/abcd.php");
//        ww2.getSettings().setDomStorageEnabled(true);
//        ww2.getSettings().setAppCacheEnabled(true);
//        ww2.getSettings().setAppCachePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache");
//        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache");
//        if(!file.exists())
//        {
//            file.mkdirs();
//        }
//        Log.e("PATH",Environment.getExternalStorageDirectory().getAbsolutePath());
//        ww2.getSettings().setDatabaseEnabled(true);
//        ww2.getSettings().setDatabasePath(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/databases");
//        File file1=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache");
//        if(!file1.exists())
//        {
//            file1.mkdirs();
//        }
        WebSettings webSettings = ww2.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        ww2.getSettings().setJavaScriptEnabled(true);

        ww2.loadUrl(detailinstruction+"?iid="+iid+"&key="+AppConstants.key+"&lang_flag="+Utility.get_lang_no(activity.getApplicationContext()));
        ww2.setWebChromeClient(new WebChromeClient(){});


    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && ww2.canGoBack())
        {
            ww2.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}

//    private void get_instruction_question() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.detailinstruction, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Instruction", "" + response);
//
//                try {
//                    JSONObject obj=new JSONObject(response);
//                    JSONArray obj1=obj.getJSONArray("detailinstruction");
//
//
//                    if(obj1!=null) {
//
//
//
//                    }
//
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
//
//
//            }
//
//
//        },new Response.ErrorListener()
//
//        {
//            @Override
//            public void onErrorResponse (VolleyError error){
//                Log.e("instruction", "Error at sign in : " + error.getMessage());
//            }
//        })
//
//        {
//            @Override
//            protected Map<String, String> getParams () throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("iid", iid);
//                map.put("key", AppConstants.key);
//                map.put("lang_flag", Utility.get_lang_no(activity.getApplicationContext()));
//
//                Log.e("SHA-1 Key", AppConstants.key);
//                Log.e("iid",  iid);
//                Log.e("Flag",  flag);
//
//
//
//                return map;
//            }
//        };
//        Utility.SetvollyTime30Sec(stringRequest);
//        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);
//    }




