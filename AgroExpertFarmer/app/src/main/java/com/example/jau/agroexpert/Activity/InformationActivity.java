package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jau.agroexpert.Adapter.AdapterCategory;
import com.example.jau.agroexpert.Adapter.AdapterSubCategory;
import com.example.jau.agroexpert.Bean.BeanCategory;
import com.example.jau.agroexpert.Bean.BeanSubCategory;
import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Utility;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InformationActivity extends YouTubeFailureRecoveryActivity {


    ProgressDialog pd;
    Activity activity;
    LinearLayout lnback,pdfBox;
    TextView tv_content,tv_title,tv_pdf;
    ImageView imageView1;
    String url,fname;
    YouTubePlayerView youTubeView;
    Context mContext;
    Button OpenPDF;







    String c_id,category_name,is_next;

    ArrayList<BeanCategory> beanCategories;
    AdapterCategory adapterCategory;
    ArrayList<BeanSubCategory> beanSubCategories;
    AdapterSubCategory adapterSubCategory;

    //ListView lst_category;
    ListView lst_sub_category;


    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Bundle b = getIntent().getExtras();
        c_id = b.getString("c_id");
        //    is_next="fail";
        is_next = b.getString("is_next");
        category_name = b.getString("category_name");
        Log.e("check",c_id+" "+is_next+ " "+ category_name);
        lnback = (LinearLayout) findViewById(R.id.lnback);
        activity = this;
        pd = Utility.getDialog(activity);
        lst_sub_category = findViewById(R.id.lst_sub_category);
        Utility.setLocale(getApplicationContext(), Utility.get_lang(getApplicationContext()));
        mContext=this;
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        imageView1 = findViewById(R.id.imageView1);
        OpenPDF = findViewById(R.id.Openpdf);
        tv_pdf = findViewById(R.id.tv_pdf);
        pdfBox = findViewById(R.id.pdfBox);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        //beanCategories = new ArrayList<>();
        beanSubCategories = new ArrayList<>();

        getSubCatList(c_id);
        getInfo(c_id);

        lst_sub_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                c_id = beanSubCategories.get(position).pid;
                category_name = beanSubCategories.get(position).category_flag;
                //category_name = beanCategories.get(position).status;
               // Toast.makeText(getApplicationContext(),"g"+c_id,Toast.LENGTH_LONG).show();
                if(is_next.equalsIgnoreCase("success")){
                    Intent intent = new Intent(getApplicationContext(),InformationActivity.class);
                    intent.putExtra("c_id",c_id);
                    intent.putExtra("category_name",category_name);
                    intent.putExtra("is_next",is_next);
                    startActivity(intent);
                }else if(is_next.equalsIgnoreCase("fail")){

                    Intent intent = new Intent(getApplicationContext(),PlayerViewDemoActivity.class);
                    intent.putExtra("c_id",c_id);
                    intent.putExtra("category_name",category_name);
                    startActivity(intent);
                }

            }
        });


        lnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        OpenPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "pdf");
                folder.mkdir();
                File file = new File(folder, fname);
                Log.e("File saved here", String.valueOf(file));
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Downloader.DownloadFile(url, file);

                showPdf(file);

            }
        });
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


                    if (jsonArray != null) {
//                        Log.e("JSON String",jsonArray.getJSONObject(0).getString("status").toString());
//                        is_next=jsonArray.getJSONObject(0).getString("status").toString();

                        beanSubCategories.clear();
                        beanSubCategories.addAll((Collection<? extends BeanSubCategory>) new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<ArrayList<BeanSubCategory>>(){}.getType()));

                        adapterSubCategory = new AdapterSubCategory(beanSubCategories,getApplicationContext());
                        lst_sub_category.setAdapter(adapterSubCategory);

                        pd.dismiss();
//                        Log.e("out of if",is_next);
                        if(is_next.equalsIgnoreCase("fail")) {
                            Log.e("if",is_next);
                            Intent intent = new Intent(getApplicationContext(), ActivityAddQuestion.class);
                            intent.putExtra("c_id", c_id);
                            intent.putExtra("category_name", category_name);

                        }
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


    public void getInfo(final String cid){

        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST , AppConstants.info, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE","" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("informative");
                    Log.e("jsonArray", String.valueOf(jsonArray));


                    if (jsonArray.length() != 0) {
                        Log.e("JSON String",jsonArray.getJSONObject(0).getString("status").toString());
                        if(jsonArray.getJSONObject(0).getString("status").toString().equalsIgnoreCase("success")){

                            if(!(jsonArray.getJSONObject(0).getString("content_title").equalsIgnoreCase(""))){
                                tv_title.setText(jsonArray.getJSONObject(0).getString("content_title"));
                            }
                            if(!(jsonArray.getJSONObject(0).getString("content_desc").equalsIgnoreCase(""))){
                                tv_content.setText(jsonArray.getJSONObject(0).getString("content_desc"));
                            }

                            if(!(jsonArray.getJSONObject(0).getString("image").equalsIgnoreCase("Not Uploaded"))){
                                imageView1.setVisibility(View.VISIBLE);
                                String url = AppConstants.infofetchimg + jsonArray.getJSONObject(0).getString("image");
                                Log.e("URL FOR IMAGE",url);
                                Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView1);
                            }

                            if(!(jsonArray.getJSONObject(0).getString("pdf").equalsIgnoreCase("Not Uploaded"))) {
                                url = AppConstants.infofetchpdf + jsonArray.getJSONObject(0).getString("pdf");
                                fname = jsonArray.getJSONObject(0).getString("pdf");
                                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                                File folder = new File(extStorageDirectory, "pdf");
                                folder.mkdir();
                                File file1 = new File(folder,fname);
                                try {
                                    file1.createNewFile();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                Downloader.DownloadFile(url, file1);

                                pdfBox.setVisibility(View.VISIBLE);
                                tv_pdf.setVisibility(View.VISIBLE);
                                tv_pdf.setText("Document "+fname+" is available.");
                                OpenPDF.setVisibility(View.VISIBLE);



                            }
                            if(!(jsonArray.getJSONObject(0).getString("video").equalsIgnoreCase("Not Uploaded"))){
                                final String s = jsonArray.getJSONObject(0).getString("video");
                                youTubeView.setVisibility(View.VISIBLE);
                                youTubeView.initialize("Your API Key", new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                        if(!b){
                                            //String videoId = getIntent().getExtras().getString("videoID");
                                            youTubePlayer.cueVideo(s);
                                        }
                                    }


                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                        //Toast.makeText(getApplicationContext(), getString(R.string.failed), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                       pd.dismiss();
//                        Log.e("out of if",is_next);

                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),ActivityAddQuestion.class);
                        intent.putExtra("c_id",c_id);
                        //intent.putExtra("catList",catList);
                        //Log.e("catList",catList);

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

    public void showPdf(File file1)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            String newFilePath = file1.getAbsolutePath().replaceAll("%20", " ");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                intent.setDataAndType(Uri.parse(newFilePath), "application/pdf");
            } else {
                Uri uri = Uri.parse(newFilePath);
                File file = new File(uri.getPath());
                if (file.exists()) {
                    uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".video", file);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }

            startActivity(intent);

        } catch (Exception e) {

        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }
}




