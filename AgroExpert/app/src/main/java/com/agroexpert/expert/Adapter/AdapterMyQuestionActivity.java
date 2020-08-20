package com.agroexpert.expert.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.agroexpert.expert.Activity.ActivityAddQuestion;
import com.agroexpert.expert.Bean.BeanSelectMyQuestion;
import com.agroexpert.expert.R;
import com.agroexpert.expert.Utils.AppConstants;
import com.agroexpert.expert.Utils.Utility;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaina on 14/12/17.
 */

public class AdapterMyQuestionActivity extends RecyclerView.Adapter<AdapterMyQuestionActivity.ViewHolder> {

    Context activity;
    ArrayList<BeanSelectMyQuestion> list;
    String Flag;

    MediaController mediacontroller;
    Uri uri;
    boolean isContinuously = false;
    MediaPlayer mediaplayer;


    public AdapterMyQuestionActivity(ArrayList<BeanSelectMyQuestion> list, Context activity){
        super();
        this.activity = activity;
        this.list = list;
        this.Flag = Flag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_viw1,parent,false);
        ViewHolder item = new ViewHolder(itemView);
        return item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.tv_title_home.setText(list.get(position).question);
        holder.tv_category.setText(list.get(position).category + " -> ");
//        holder.tv_sub_category.setText(list.get(position).subcategory);
        holder.tv_timestamp.setText(list.get(position).timestamp);
        holder.tv_far_phone.setText(Utility.get_phone(activity));


        if (Flag.equalsIgnoreCase("PQ")) {
            holder.ln_answer.setVisibility(View.GONE);
            holder.tv_answer.setVisibility(View.GONE);
            holder.ln_write_answer.setVisibility(View.VISIBLE);

            if (list.get(position).contentflag.equalsIgnoreCase("I")) {
                holder.ig_home_img.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchimageurl + list.get(position).image;
                Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ig_home_img);
            } else if (list.get(position).contentflag.equalsIgnoreCase("V")) {
                holder.ll_video.setVisibility(View.VISIBLE);
                holder.vv.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchvideourl + list.get(position).image;
                holder.vv.setVideoPath(url);

                mediacontroller = new MediaController(activity);
                mediacontroller.setAnchorView(holder.vv);
                uri = Uri.parse(url);

                holder.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.vv.setMediaController(mediacontroller);
                        holder.vv.start();
                    }
                });

            } else if (list.get(position).contentflag.equalsIgnoreCase("A")) {
                holder.rl_audio.setVisibility(View.VISIBLE);
                final String url = AppConstants.fetchimageurl + list.get(position).image;
                mediaplayer = new MediaPlayer();
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                holder.buttonStart.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        try {
                            mediaplayer.setDataSource(url);
                            mediaplayer.prepare();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        mediaplayer.start();
                    }
                });

                holder.buttonStop.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mediaplayer.stop();
                    }
                });

            }


            holder.ln_write_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ActivityAddQuestion.class);
                    intent.putExtra("question", list.get(position).question);
                    intent.putExtra("qid", list.get(position).qid);
                    Log.e("click", "clicking....");
                    activity.startActivity(intent);
                }
            });


        } else if (Flag.equalsIgnoreCase("SQ")) {
            holder.main_ll1.setVisibility(View.VISIBLE);
            holder.tv_answer.setText(list.get(position).answer);
            holder.answer.setVisibility(View.GONE);
            holder.tv_category.setText(list.get(position).category);

            Log.e("stared",list.get(position).stared);
            if(list.get(position).stared.equalsIgnoreCase("1"))
            {
                holder.ln_starred.setBackgroundColor(activity.getColor(R.color.green));
                holder.tv_add_remove_starred.setText("Remove Starred");
            }

            if(list.get(position).contentflag.equalsIgnoreCase("I")){
                holder.ig_home_img.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchimageurl + list.get(position).image;
                Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ig_home_img);
            }else if (list.get(position).contentflag.equalsIgnoreCase("V")){
                holder.ll_video.setVisibility(View.VISIBLE);
                holder.vv.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchvideourl + list.get(position).image;
                holder.vv.setVideoPath(url);

                mediacontroller = new MediaController(activity);
                mediacontroller.setAnchorView(holder.vv);
                uri = Uri.parse(url);


                holder.vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(isContinuously){
                            //holder.vv.start();
                        }
                    }
                });


                holder.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.vv.setMediaController(mediacontroller);
                        holder.vv.start();
                    }
                });

                Log.e("Video_url",url);
            }else if(list.get(position).contentflag.equalsIgnoreCase("A")){
                holder.rl_audio.setVisibility(View.VISIBLE);
                final String url = AppConstants.fetchaudiourl + list.get(position).image;
                mediaplayer = new MediaPlayer();
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                holder.buttonStart.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        try {

                            mediaplayer.setDataSource(url);
                            mediaplayer.prepare();


                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        mediaplayer.start();


                    }
                });

                holder.buttonStop.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub


                        mediaplayer.stop();


                    }
                });

            }


            if(list.get(position).excontentflag.equalsIgnoreCase("I")){
                holder.ig_home_img1.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchimageurl + list.get(position).eximage;
                Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ig_home_img1);
            }else if (list.get(position).excontentflag.equalsIgnoreCase("V")){
                holder.ll_video1.setVisibility(View.VISIBLE);
                holder.vv1.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchvideourl + list.get(position).eximage;
                holder.vv1.setVideoPath(url);

                mediacontroller = new MediaController(activity);
                mediacontroller.setAnchorView(holder.vv1);
                uri = Uri.parse(url);


                holder.vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(isContinuously){
                            //holder.vv.start();
                        }
                    }
                });


                holder.vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.vv.setMediaController(mediacontroller);
                        holder.vv.start();
                    }
                });

                Log.e("Video_url",url);
            }else if(list.get(position).excontentflag.equalsIgnoreCase("A")){
                holder.rl_audio1.setVisibility(View.VISIBLE);
                final String url = AppConstants.fetchaudiourl + list.get(position).eximage;
                mediaplayer = new MediaPlayer();
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                holder.buttonStart1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        try {

                            mediaplayer.setDataSource(url);
                            mediaplayer.prepare();


                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        mediaplayer.start();


                    }
                });

                holder.buttonStop1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub


                        mediaplayer.stop();


                    }
                });





//            holder.ln_like.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    add_like(list.get(position).qid,activity,holder);
//                }
//            });


        }

            holder.ln_starred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_starred(list.get(position).qid,activity,holder);
                }
            });

        } else if (Flag.equalsIgnoreCase("FAQ")) {

        } else if (Flag.equalsIgnoreCase("AQ")) {
            holder.ln_write_answer.setVisibility(View.GONE);
            holder.ln_like_starred.setVisibility(View.GONE);
            holder.tv_answer_by.setText("Answer By : " + list.get(position).exname);
            holder.ln_answer.setVisibility(View.VISIBLE);
            holder.tv_answer.setVisibility(View.VISIBLE);
            holder.tv_answer.setText(list.get(position).answer);

            if(list.get(position).contentflag.equalsIgnoreCase("I")){
                holder.ig_home_img.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchimageurl + list.get(position).image;
                Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ig_home_img);
            }else if (list.get(position).contentflag.equalsIgnoreCase("V")){
                holder.ll_video.setVisibility(View.VISIBLE);
                holder.vv.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchvideourl + list.get(position).image;
                holder.vv.setVideoPath(url);

                mediacontroller = new MediaController(activity);
                mediacontroller.setAnchorView(holder.vv);
                uri = Uri.parse(url);


                holder.vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(isContinuously){
                            //holder.vv.start();
                        }
                    }
                });


                holder.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.vv.setMediaController(mediacontroller);
                        holder.vv.start();
                    }
                });

                Log.e("Video_url",url);
            }else if(list.get(position).contentflag.equalsIgnoreCase("A")) {
                holder.rl_audio.setVisibility(View.VISIBLE);
                final String url = AppConstants.fetchimageurl + list.get(position).image;
                Log.e("Audio_url",url);
                mediaplayer = new MediaPlayer();
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                holder.buttonStart.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        try {

                            mediaplayer.setDataSource(url);
                            mediaplayer.prepare();


                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        mediaplayer.start();


                    }
                });

                holder.buttonStop.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub


                        mediaplayer.stop();


                    }
                });

            }



            if(list.get(position).excontentflag.equalsIgnoreCase("I")){
            holder.ig_home_img1.setVisibility(View.VISIBLE);
            String url = AppConstants.fetchimageurl + list.get(position).eximage;
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ig_home_img1);
            }else if (list.get(position).excontentflag.equalsIgnoreCase("V")){
                holder.ll_video1.setVisibility(View.VISIBLE);
                holder.vv1.setVisibility(View.VISIBLE);
                String url = AppConstants.fetchvideourl + list.get(position).eximage;
                holder.vv1.setVideoPath(url);

                mediacontroller = new MediaController(activity);
                mediacontroller.setAnchorView(holder.vv1);
                uri = Uri.parse(url);


                holder.vv1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(isContinuously){
                            //holder.vv.start();
                        }
                    }
                });



                holder.vv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        holder.progressBar1.setVisibility(View.GONE);
                        holder.vv1.setMediaController(mediacontroller);
                        holder.vv1.start();
                    }
                });

                Log.e("Video_url",url);
            }else if(list.get(position).excontentflag.equalsIgnoreCase("A")){
                holder.rl_audio1.setVisibility(View.VISIBLE);
                final String url = AppConstants.fetchimageurl + list.get(position).eximage;
                mediaplayer = new MediaPlayer();
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                holder.buttonStart1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        try {

                            mediaplayer.setDataSource(url);
                            mediaplayer.prepare();


                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        mediaplayer.start();


                    }
                });

                holder.buttonStop1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub


                        mediaplayer.stop();


                    }
                });

        }} else if (Flag.equalsIgnoreCase("STQ")) {

        }



        }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ig_home_img,ig_home_img1;
        TextView tv_answer_heading,tv_add_remove_starred,tv_add_remove_like,tv_category,tv_sub_category,tv_timestamp,tv_far_phone,tv_answer_by,answer;
        TextView tv_answer,tv_title_home,tv_question;
        LinearLayout ln_answer,main_ll1,ln_like_starred,ln_like,ln_starred,ln_profile,ll_video,rl_audio,ln_write_answer,ll_video1,rl_audio1;
        Button buttonStart,buttonStop,buttonStart1,buttonStop1;
        VideoView vv,vv1;
        ProgressBar progressBar,progressBar1;



        public ViewHolder(View view){
            super(view);
            main_ll1=view.findViewById(R.id.main_ll1);
            ig_home_img = (ImageView)view.findViewById(R.id.ig_home_img);
            ig_home_img1 = (ImageView)view.findViewById(R.id.ig_home_img1);
            tv_title_home = (JustifiedTextView)view.findViewById(R.id.tv_title_home);
            tv_answer = (JustifiedTextView)view.findViewById(R.id.tv_answer);
            tv_category = (TextView) view.findViewById(R.id.tv_category);
            tv_sub_category = (TextView) view.findViewById(R.id.tv_sub_category);
            tv_timestamp = (TextView) view.findViewById(R.id.tv_timestamp);
            tv_far_phone = (TextView) view.findViewById(R.id.tv_far_phone);
            tv_answer_by = (TextView) view.findViewById(R.id.tv_answer_by);
            tv_add_remove_starred = (TextView) view.findViewById(R.id.tv_add_remove_starred);
            ln_answer = (LinearLayout) view.findViewById(R.id.ln_answer);
            ln_like_starred = (LinearLayout) view.findViewById(R.id.ln_like_starred);
            ln_like = (LinearLayout) view.findViewById(R.id.ln_like);
            ln_starred = (LinearLayout) view.findViewById(R.id.ln_starred);
            ln_profile = (LinearLayout) view.findViewById(R.id.ln_profile);
            ll_video = (LinearLayout) view.findViewById(R.id.ll_video);
            ln_write_answer = (LinearLayout) view.findViewById(R.id.ln_write_answer);
            tv_add_remove_starred = (TextView)view.findViewById(R.id.tv_add_remove_starred);
            tv_question=view.findViewById(R.id.tv_question);
            answer=view.findViewById(R.id.answer);
            // vv_video_view = view.findViewById(R.id.vv_video_view);

            ll_video1 = (LinearLayout) view.findViewById(R.id.ll_video1);
            rl_audio1 = (LinearLayout) view.findViewById(R.id.rl_audio1);

            progressBar = (ProgressBar) view.findViewById(R.id.progrss);
            progressBar1 = (ProgressBar) view.findViewById(R.id.progrss1);

            vv = (VideoView) view.findViewById(R.id.vv);
            vv1 = (VideoView) view.findViewById(R.id.vv1);


            buttonStart = (Button)view.findViewById(R.id.button1);
            buttonStop = (Button)view.findViewById(R.id.button2);
            buttonStart1 = (Button)view.findViewById(R.id.button11);
            buttonStop1 = (Button)view.findViewById(R.id.button21);
            rl_audio = view.findViewById(R.id.rl_audio);



        }

    }



        public void add_starred(final String qid, final Context activity,final ViewHolder holder){

            //   pd.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.addtostar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("searched","" + response);

                    try {
                        JSONArray obj = new JSONArray(response);
                        String status = obj.getJSONObject(0).getString("status");

                        if(status.equalsIgnoreCase("success")){
                            Toast.makeText(activity,"Starred",Toast.LENGTH_SHORT).show();
                            holder.ln_starred.setBackgroundColor(activity.getColor(R.color.green));
                            holder.tv_add_remove_starred.setText("Remove Starred");

                        }else if(status.equalsIgnoreCase("unsuccess")) {
                            Toast.makeText(activity,"Removed",Toast.LENGTH_SHORT).show();
                            holder.ln_starred.setBackgroundColor(activity.getColor(R.color.white));
                            holder.tv_add_remove_starred.setText("Add to Starred");

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Select_Question", "Error at sign in : " + error.getMessage());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("lid", Utility.get_uid(activity));
                    map.put("qid", qid);
                    map.put("key",AppConstants.key);

                    Log.e("SHA-1 Key",AppConstants.key);
                    Log.e("lid",""+ Utility.get_uid(activity));
                    Log.e("qid",""+qid);


                    return map;
                }
            };

            Utility.SetvollyTime30Sec(stringRequest);
            Utility.getRequestQueue(activity).add(stringRequest);


        }


}


