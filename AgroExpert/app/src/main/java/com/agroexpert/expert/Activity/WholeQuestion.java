package com.agroexpert.expert.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.agroexpert.expert.Adapter.AdapterCategory;
import com.agroexpert.expert.Adapter.AdapterMyQuestionActivity;
import com.agroexpert.expert.Bean.BeanCategory;
import com.agroexpert.expert.Bean.BeanSelectMyQuestion;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.ContextWrapper;
import com.agroexpert.expert.Utils.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.res.Resources.*;


public class WholeQuestion extends AppCompatActivity {

    static RecyclerView rv_home_activity;
    static ArrayList<BeanSelectMyQuestion> beanSelectMyQuestions = new ArrayList<>();
    static ArrayList<BeanSelectMyQuestion> beanSelectFAQS = new ArrayList<>();
    static ArrayList<BeanSelectMyQuestion> beanSelectAnswered = new ArrayList<>();
    static ArrayList<BeanSelectMyQuestion> beanStarredQuestion = new ArrayList<>();
    static ArrayList<BeanSelectMyQuestion> beanSelectUnAnswered = new ArrayList<>();
    static ArrayList<BeanSelectMyQuestion> beanSelectSearch = new ArrayList<>();

    static AdapterMyQuestionActivity adapter;
    static SwipeRefreshLayout swipeRefreshLayout;
    static Activity activity;
    LinearLayout lnback, ll_video, ll_video1, ln_answer, ll_main, rl_audio, ln_starred, ln_like, rl_audio1, ln_write_answer, main_ll;
    static String Flag;
    static ProgressDialog pd;
    TextView tv_header, tv_timestamp, tv_far_phone, tv_category, tv_title_home, tv_add_remove_like, tv_add_remove_starred, tv_answer_by, tv_answer, answer;
    ImageView ig_home_img, ig_home_img1;
    VideoView vv, vv1;
    Spinner spn_category;
    ArrayList<BeanCategory> beanCategories = new ArrayList<>();
    AdapterCategory adapterCategory;
    String c_id = "1", category_name;
    String flag, qid, iid;
    ProgressBar progressBar, progressBar1;
    Button buttonStart, buttonStop, buttonStop1, buttonStart1;

    MediaController mediacontroller;
    Uri uri;
    boolean isContinuously = false;
    MediaPlayer mediaplayer;

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_list_view);
        activity = this;
        String lang = Utility.get_lang(getApplicationContext());
        Utility.setLocale(getApplicationContext(), lang);
        pd = Utility.getDialog(activity);
        tv_timestamp = findViewById(R.id.tv_timestamp);
        tv_far_phone = findViewById(R.id.tv_far_phone);
        tv_category = findViewById(R.id.tv_category);
        tv_answer_by = findViewById(R.id.tv_answer_by);
        tv_answer = findViewById(R.id.tv_answer);
        tv_title_home = findViewById(R.id.tv_title_home);
        ln_answer = findViewById(R.id.ln_answer);
        answer = findViewById(R.id.answer);
        ig_home_img = findViewById(R.id.ig_home_img);
        ll_video = findViewById(R.id.ll_video);
        ll_video1 = findViewById(R.id.ll_video1);
        tv_header = findViewById(R.id.tv_header);
        main_ll = findViewById(R.id.main_ll);

        // ll_main=findViewById(R.id.ll_main);
        ln_starred = findViewById(R.id.ln_starred);
        //ln_like=findViewById(R.id.ln_like);
        ig_home_img1 = findViewById(R.id.ig_home_img1);
        rl_audio = findViewById(R.id.rl_audio);
        rl_audio1 = findViewById(R.id.rl_audio1);
        // tv_add_remove_like=findViewById(R.id.tv_add_remove_like);
        tv_add_remove_starred = findViewById(R.id.tv_add_remove_starred);
        ln_write_answer = findViewById(R.id.ln_write_answer);
        vv = findViewById(R.id.vv);
        vv1 = findViewById(R.id.vv1);
        lnback = findViewById(R.id.lnback);
        buttonStart = findViewById(R.id.button1);
        buttonStop = findViewById(R.id.button2);
        buttonStart1 = findViewById(R.id.button11);
        buttonStop1 = findViewById(R.id.button21);
        progressBar = findViewById(R.id.progrss);
        progressBar1 = findViewById(R.id.progrss1);
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

        if (flag.equalsIgnoreCase("PQ")) {
            get_pending_question();
        } else if (flag.equalsIgnoreCase("AQ")) {
            get_answered_question();
        } else if (flag.equalsIgnoreCase("SQ")) {
            get_search_question();

        } else if (flag.equalsIgnoreCase("STQ")) {
            get_starred_question();
        } else if (flag.equalsIgnoreCase("INS")) {
            get_instruction_question();
        }
    }

    private void get_instruction_question() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.detailinstruction, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Instruction", "" + response);

                try {
                    JSONObject obj=new JSONObject(response);
                    JSONArray obj1=obj.getJSONArray("detailinstruction");

                    if(obj1!=null) {
                        main_ll.setVisibility(View.VISIBLE);
                        tv_far_phone.setVisibility(View.GONE);
                        tv_timestamp.setVisibility(View.GONE);
                        tv_category.setVisibility(View.GONE);
                        answer.setVisibility(View.GONE);
                        tv_title_home.setText(obj1.getJSONObject(0).getString("title"));
                        ln_answer.setVisibility(View.VISIBLE);
                        tv_answer.setVisibility(View.VISIBLE);
                        tv_answer.setText(obj1.getJSONObject(0).getString("content"));
                        ig_home_img.setVisibility(View.GONE);
                        tv_answer_by.setVisibility(View.GONE);
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }



    },new Response.ErrorListener()

    {
        @Override
        public void onErrorResponse (VolleyError error){
        Log.e("instruction", "Error at sign in : " + error.getMessage());
    }
    })

    {
        @Override
        protected Map<String, String> getParams () throws AuthFailureError {
        Map<String, String> map = new HashMap<String, String>();
        map.put("iid", iid);
        map.put("key", AppConstants.key);
        map.put("lang_flag",Utility.get_lang_no(activity.getApplicationContext()));

        Log.e("SHA-1 Key", AppConstants.key);
        Log.e("iid",  iid);
        Log.e("Flag",  flag);



        return map;
    }
    };
        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);
}



private void get_answered_question(){

        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConstants.exdetailansweredquestion,new Response.Listener<String>(){
@Override
public void onResponse(String response){

        Log.e("Answered question",""+response);

        try{
        JSONObject obj=new JSONObject(response);
final JSONArray obj1=obj.getJSONArray("answerquestion");
        main_ll.setVisibility(View.VISIBLE);

        if(obj1!=null)
        {

        Log.e("stared",obj1.getJSONObject(0).getString("stared"));
        if(obj1.getJSONObject(0).getString("stared").equalsIgnoreCase("1"))
        {
        ln_starred.setBackgroundColor(activity.getColor(R.color.green));
        tv_add_remove_starred.setText("Remove Starred");
        }

        tv_answer_by.setText("Answer By : "+obj1.getJSONObject(0).getString("exname"));
        tv_header.setText(getString(R.string.answered_questions));
        ln_write_answer.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ln_answer.setVisibility(View.VISIBLE);
        tv_answer.setVisibility(View.VISIBLE);
        tv_answer.setText(obj1.getJSONObject(0).getString("answer"));
        tv_far_phone.setText(obj1.getJSONObject(0).getString("phoneno"));
        tv_timestamp.setText(obj1.getJSONObject(0).getString("timestamp"));
        tv_category.setText(obj1.getJSONObject(0).getString("category"));
        tv_title_home.setText(obj1.getJSONObject(0).getString("question"));
        Log.e("category",obj1.getJSONObject(0).getString("category"));
        Log.e("flag",obj1.getJSONObject(0).getString("contentflag"));
        if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("I"))
        {
        Log.e("image",obj1.getJSONObject(0).getString("image"));
        ig_home_img.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("image");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img);
        pd.dismiss();
        }
        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("V"))
        {
        ll_video.setVisibility(View.VISIBLE);
        vv.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("image");
        vv.setVideoPath(url);
        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv);
        uri=Uri.parse(url);
        pd.dismiss();

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar.setVisibility(View.GONE);
        vv.setMediaController(mediacontroller);
        vv.start();
        }
        });
        Log.e("Video_url",url);
        }

        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("A")){
        rl_audio.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("image");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        buttonStart.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();


        }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("I")){
        ig_home_img1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("eximage");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img1);
        Log.e("Image_URL",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("V")){
        ll_video1.setVisibility(View.VISIBLE);
        vv1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("eximage");
        vv1.setVideoPath(url);

        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv1);
        uri=Uri.parse(url);

        vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar1.setVisibility(View.GONE);
        vv1.setMediaController(mediacontroller);
        vv1.start();
        }
        });

        Log.e("Video_url",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("A")){
        rl_audio1.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("eximage");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.e("Audio_url",url);
        buttonStart1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();

        }
        });

        buttonStop1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        ln_starred.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
        try{
        add_starred(obj1.getJSONObject(0).getString("qid"),activity);
        }catch(JSONException e){
        e.printStackTrace();
        }
        }
        });

//
        }

        //swipeRefreshLayout.setRefreshing(false);
        }catch(JSONException e){
        e.printStackTrace();

        }
        }

        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.e("Answered question","Error at sign in : "+error.getMessage());
        pd.dismiss();
        }
        }){
@Override
protected Map<String, String> getParams()throws AuthFailureError{
        Map<String, String> map=new HashMap<String, String>();
        map.put("lid",Utility.get_uid(activity.getApplicationContext()));
        map.put("qid",qid);
        map.put("key",AppConstants.key);
        map.put("lang_flag",Utility.get_lang_no(activity.getApplicationContext()));


        Log.e("SHA-1 Key",AppConstants.key);
        Log.e("lid",""+Utility.get_uid(getApplicationContext()));
        Log.e("qid",""+qid);


        return map;
        }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);
        pd.dismiss();

        }

private void get_search_question(){

        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConstants.detailsearchquestion,new Response.Listener<String>(){
@Override
public void onResponse(String response){

        Log.e("Answered question",""+response);

        try{
        JSONObject obj=new JSONObject(response);
final JSONArray obj1=obj.getJSONArray("detailsearchquestion");
        main_ll.setVisibility(View.VISIBLE);

        if(obj1!=null)
        {

        Log.e("stared",obj1.getJSONObject(0).getString("stared"));
        if(obj1.getJSONObject(0).getString("stared").equalsIgnoreCase("1"))
        {
        ln_starred.setBackgroundColor(activity.getColor(R.color.green));
        tv_add_remove_starred.setText("Remove Starred");
        }

        tv_answer_by.setText("Answer By : "+obj1.getJSONObject(0).getString("exname"));
        tv_header.setText(getString(R.string.answered_questions));
        ln_write_answer.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ln_answer.setVisibility(View.VISIBLE);
        tv_answer.setVisibility(View.VISIBLE);
        tv_answer.setText(obj1.getJSONObject(0).getString("answer"));
        tv_far_phone.setText(obj1.getJSONObject(0).getString("phoneno"));
        tv_timestamp.setText(obj1.getJSONObject(0).getString("timestamp"));
        tv_category.setText(obj1.getJSONObject(0).getString("category"));
        tv_title_home.setText(obj1.getJSONObject(0).getString("question"));
        Log.e("category",obj1.getJSONObject(0).getString("category"));
        Log.e("flag",obj1.getJSONObject(0).getString("contentflag"));
        if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("I"))
        {
        Log.e("image",obj1.getJSONObject(0).getString("image"));
        ig_home_img.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("image");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img);
        pd.dismiss();
        }
        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("V"))
        {
        ll_video.setVisibility(View.VISIBLE);
        vv.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("image");
        vv.setVideoPath(url);
        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv);
        uri=Uri.parse(url);
        pd.dismiss();

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar.setVisibility(View.GONE);
        vv.setMediaController(mediacontroller);
        vv.start();
        }
        });
        Log.e("Video_url",url);
        }

        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("A")){
        rl_audio.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("image");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        buttonStart.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();


        }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("I")){
        ig_home_img1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("eximage");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img1);
        Log.e("Image_URL",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("V")){
        ll_video1.setVisibility(View.VISIBLE);
        vv1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("eximage");
        vv1.setVideoPath(url);

        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv1);
        uri=Uri.parse(url);

        vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar1.setVisibility(View.GONE);
        vv1.setMediaController(mediacontroller);
        vv1.start();
        }
        });

        Log.e("Video_url",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("A")){
        rl_audio1.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("eximage");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.e("Audio_url",url);
        buttonStart1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();

        }
        });

        buttonStop1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        ln_starred.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
        try{
        add_starred(obj1.getJSONObject(0).getString("qid"),activity);
        }catch(JSONException e){
        e.printStackTrace();
        }
        }
        });

//
        }

        //swipeRefreshLayout.setRefreshing(false);
        }catch(JSONException e){
        e.printStackTrace();

        }
        }

        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.e("Answered question","Error at sign in : "+error.getMessage());
        pd.dismiss();
        }
        }){
@Override
protected Map<String, String> getParams()throws AuthFailureError{
        Map<String, String> map=new HashMap<String, String>();
        map.put("lid",Utility.get_uid(activity.getApplicationContext()));
        map.put("qid",qid);
        map.put("key",AppConstants.key);
        map.put("lang_flag",Utility.get_lang_no(activity.getApplicationContext()));


        Log.e("SHA-1 Key",AppConstants.key);
        Log.e("lid",""+Utility.get_uid(getApplicationContext()));
        Log.e("qid",""+qid);


        return map;
        }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);
        pd.dismiss();
        }

private void get_pending_question(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConstants.exdetailunansweredquestion,new Response.Listener<String>(){
@Override
public void onResponse(String response){

        Log.e("pending_questions",""+response);

        try{
        JSONObject obj=new JSONObject(response);
final JSONArray obj1=obj.getJSONArray("unanswerquestion");

        main_ll.setVisibility(View.VISIBLE);
        tv_far_phone.setText(obj1.getJSONObject(0).getString("phoneno"));
        ln_starred.setVisibility(View.GONE);
        tv_header.setText(getString(R.string.pending_questions));
        ln_answer.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        tv_timestamp.setText(obj1.getJSONObject(0).getString("timestamp"));
        tv_category.setText(obj1.getJSONObject(0).getString("category"));
        tv_title_home.setText(obj1.getJSONObject(0).getString("question"));
        answer.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
        Intent intent=new Intent(WholeQuestion.this,ActivityAddQuestion.class);
        try{
        intent.putExtra("question",obj1.getJSONObject(0).getString("question"));
        }catch(JSONException e){
        e.printStackTrace();
        }
        intent.putExtra("qid",qid);
        Log.e("click","clicking....");
        activity.startActivity(intent);
        }
        });
        Log.e("category",obj1.getJSONObject(0).getString("category"));
        Log.e("flag",obj1.getJSONObject(0).getString("contentflag"));
        if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("I"))
        {
        Log.e("image","_"+obj1.getJSONObject(0).getString("image"));
        ig_home_img.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("image");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img);
        }
        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("V"))
        {
        ll_video.setVisibility(View.VISIBLE);
        vv.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("image");
        vv.setVideoPath(url);
        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv);
        uri=Uri.parse(url);

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar.setVisibility(View.GONE);
        vv.setMediaController(mediacontroller);
        vv.start();
        }
        });
        Log.e("Video_url",url);
        }

        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("A")){
        rl_audio.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("image");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.e("Audio_url",url);
        buttonStart.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();


        }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }


        //swipeRefreshLayout.setRefreshing(false);
        }catch(JSONException e){
        e.printStackTrace();
        }
        }

        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.e("Select_Question","Error at sign in : "+error.getMessage());
        //  pd.dismiss();

        }
        }){
@Override
protected Map<String, String> getParams()throws AuthFailureError{
        Map<String, String> map=new HashMap<String, String>();
        map.put("lid",Utility.get_uid(activity.getApplicationContext()));
        map.put("qid",qid);
        map.put("key",AppConstants.key);
        map.put("lang_flag",Utility.get_lang_no(activity.getApplicationContext()));


        Log.e("SHA-1 Key",AppConstants.key);
        Log.e("lid",""+Utility.get_uid(getApplicationContext()));
        Log.e("qid",""+qid);


        return map;
        }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


        }


public void add_starred(final String qid,final Context activity){

        //   pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConstants.addtostar,new Response.Listener<String>(){
@Override
public void onResponse(String response){

        Log.e("Answered_Question",""+response);

        try{
        JSONArray obj=new JSONArray(response);
        String status=obj.getJSONObject(0).getString("status");

        main_ll.setVisibility(View.VISIBLE);
        if(status.equalsIgnoreCase("success")){
        Toast.makeText(activity,"Starred",Toast.LENGTH_SHORT).show();
        ln_starred.setBackgroundColor(activity.getColor(R.color.green));
        tv_add_remove_starred.setText("Remove Starred");

        }else if(status.equalsIgnoreCase("unsuccess")){
        Toast.makeText(activity,"Removed",Toast.LENGTH_SHORT).show();
        ln_starred.setBackgroundColor(activity.getColor(R.color.white));
        tv_add_remove_starred.setText("Add to Starred");

        }


        }catch(JSONException e){
        e.printStackTrace();
        }

        }
        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.e("Select_Question","Error at sign in : "+error.getMessage());
        }
        }){
@Override
protected Map<String, String> getParams()throws AuthFailureError{
        Map<String, String> map=new HashMap<String, String>();
        map.put("lid",Utility.get_uid(activity));
        map.put("qid",qid);
        map.put("key",AppConstants.key);

        Log.e("SHA-1 Key",AppConstants.key);
        Log.e("lid",""+Utility.get_uid(activity));
        Log.e("qid",""+qid);


        return map;
        }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity).add(stringRequest);


        }
private void get_starred_question(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST,AppConstants.detailstarredlist,new Response.Listener<String>(){
@Override
public void onResponse(String response){

        Log.e("Answered question",""+response);

        try{
        JSONObject obj=new JSONObject(response);
final JSONArray obj1=obj.getJSONArray("answerquestion");
        main_ll.setVisibility(View.VISIBLE);
        ln_starred.setBackgroundColor(activity.getColor(R.color.green));
        tv_add_remove_starred.setText("Remove Starred");


        tv_answer_by.setText("Answer By : "+obj1.getJSONObject(0).getString("exname"));
        ln_write_answer.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        ln_answer.setVisibility(View.VISIBLE);
        tv_answer.setVisibility(View.VISIBLE);
        tv_answer.setText(obj1.getJSONObject(0).getString("answer"));
        tv_far_phone.setText(Utility.get_phone(activity));
        tv_timestamp.setText(obj1.getJSONObject(0).getString("timestamp"));
        tv_category.setText(obj1.getJSONObject(0).getString("category"));
        tv_title_home.setText(obj1.getJSONObject(0).getString("question"));
        Log.e("category",obj1.getJSONObject(0).getString("category"));
        Log.e("flag",obj1.getJSONObject(0).getString("contentflag"));
        if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("I"))
        {
        Log.e("image",obj1.getJSONObject(0).getString("image"));
        ig_home_img.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("image");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img);
        }
        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("V"))
        {
        ll_video.setVisibility(View.VISIBLE);
        vv.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("image");
        vv.setVideoPath(url);
        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv);
        uri=Uri.parse(url);

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar.setVisibility(View.GONE);
        vv.setMediaController(mediacontroller);
        vv.start();
        }
        });
        Log.e("Video_url",url);
        }

        else if(obj1.getJSONObject(0).getString("contentflag").equalsIgnoreCase("A")){
        rl_audio.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("image");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        buttonStart.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();

        }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("I")){
        ig_home_img1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchimageurl+obj1.getJSONObject(0).getString("eximage");
        Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(ig_home_img1);
        Log.e("Image_URL",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("V")){
        ll_video1.setVisibility(View.VISIBLE);
        vv1.setVisibility(View.VISIBLE);
        String url=AppConstants.fetchvideourl+obj1.getJSONObject(0).getString("eximage");
        vv1.setVideoPath(url);

        mediacontroller=new MediaController(activity);
        mediacontroller.setAnchorView(vv1);
        uri=Uri.parse(url);

        vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
@Override
public void onCompletion(MediaPlayer mp){
        if(isContinuously){
        //holder.vv.start();
        }
        }
        });


        vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
// Close the progress bar and play the video
public void onPrepared(MediaPlayer mp){
        progressBar1.setVisibility(View.GONE);
        vv1.setMediaController(mediacontroller);
        vv1.start();
        }
        });

        Log.e("Video_url",url);
        }else if(obj1.getJSONObject(0).getString("excontentflag").equalsIgnoreCase("A")){
        rl_audio1.setVisibility(View.VISIBLE);
final String url=AppConstants.fetchaudiourl+obj1.getJSONObject(0).getString("eximage");
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.e("Audio_url",url);
        buttonStart1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub

        try{

        mediaplayer.setDataSource(url);
        mediaplayer.prepare();


        }catch(IllegalArgumentException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(SecurityException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IllegalStateException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }catch(IOException e){
        // TODO Auto-generated catch block
        e.printStackTrace();
        }

        mediaplayer.start();

        }
        });

        buttonStop1.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View v){
        // TODO Auto-generated method stub


        mediaplayer.stop();


        }
        });

        }

        ln_starred.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
        try{
        add_starred(obj1.getJSONObject(0).getString("qid"),activity);
        }catch(JSONException e){
        e.printStackTrace();
        }
        }
        });


        //swipeRefreshLayout.setRefreshing(false);
        }catch(JSONException e){
        e.printStackTrace();

        }
        }

        },new Response.ErrorListener(){
@Override
public void onErrorResponse(VolleyError error){
        Log.e("Answered question","Error at sign in : "+error.getMessage());

        }
        }){
@Override
protected Map<String, String> getParams()throws AuthFailureError{
        Map<String, String> map=new HashMap<String, String>();
        map.put("lid",Utility.get_uid(activity.getApplicationContext()));
        map.put("qid",qid);
        map.put("key",AppConstants.key);
        map.put("lang_flag",Utility.get_lang_no(activity.getApplicationContext()));


        Log.e("SHA-1 Key",AppConstants.key);
        Log.e("lid",""+Utility.get_uid(getApplicationContext()));
        Log.e("qid",""+qid);


        return map;
        }
        };

        Utility.SetvollyTime30Sec(stringRequest);
        Utility.getRequestQueue(activity.getApplicationContext()).add(stringRequest);


        }
        }



