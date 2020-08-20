package com.example.jau.agroexpert.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jau.agroexpert.Adapter.AdapterCategory;
import com.example.jau.agroexpert.Adapter.AdapterSubCategory;
import com.example.jau.agroexpert.Bean.BeanCategory;
import com.example.jau.agroexpert.Bean.BeanSubCategory;
import com.example.jau.agroexpert.R;
import com.example.jau.agroexpert.Utils.AppConstants;
import com.example.jau.agroexpert.Utils.ContextWrapper;
import com.example.jau.agroexpert.Utils.Upload;
import com.example.jau.agroexpert.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.example.jau.agroexpert.Utils.Utility.checkConnectivity;


public class ActivityAddQuestion extends AppCompatActivity {

    EditText edt_ques;
    Button btn_add,btn_play;
    Activity activity;
    String flag="A",subject,note,id,Udate;
    TextView date,iv_add_audio,tv_catList;
    LinearLayout ln_audio,lnback,ln_cross,ln_cross1,ln_add_video,iv_add_Image;
    ImageView iv_image,img_delete,img_delete1,img_change_audio_state;
    byte[] byteArray;
    Uri audio_uri = Uri.EMPTY ;
    Uri image_uri = Uri.EMPTY;
    Uri video_uri = Uri.EMPTY;
    String filename = " ";



    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2,REQUEST_VIDEO_CAPTURED_NEXUS = 3;
    private RequestQueue mRequestQueue;
    String lid,img_base64=" ";
    ProgressDialog pd;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Uri fileUri;
    VideoView videoView;
    MediaController mediaController;
    String video_name;
    static int i=0,j=0;
    Spinner spn_category,spn_sub_category;
    String image_name;

    ArrayList<BeanCategory> beanCategories;
    ArrayList<BeanSubCategory> beanSubCategories;
    AdapterCategory adapterCategory;
    AdapterSubCategory adapterSubCategory;
    String c_id,p_id,category_name,sub_category_name;
    int k=0;
    int last_position=0,recent_position=0;
    String catList;



    String FLAG = "";

    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Utility.get_lang(newBase)));
        super.attachBaseContext(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        activity = this;
        pd = Utility.getDialog(activity);
        Utility.setLocale(getApplicationContext(),Utility.get_lang(getApplicationContext()));


        videoView =(VideoView)findViewById(R.id.videoView1);
        videoView.setVisibility(View.GONE);
        Bundle b = getIntent().getExtras();
        mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        beanCategories = new ArrayList<>();
        beanSubCategories = new ArrayList<>();

        lnback = (LinearLayout) findViewById(R.id.lnback);
        ln_add_video = (LinearLayout) findViewById(R.id.ln_add_video);
        ln_cross = (LinearLayout) findViewById(R.id.ln_cross);
        ln_audio = (LinearLayout) findViewById(R.id.ln_audio);
        ln_cross1 = (LinearLayout) findViewById(R.id.ln_cross1);
        edt_ques = (EditText) findViewById(R.id.edt_ques);
        btn_add = (Button) findViewById(R.id.btn_add);
        //btn_play = (Button) findViewById(R.id.btn_play);
        iv_add_Image = (LinearLayout) findViewById(R.id.iv_add_Image);
        iv_add_audio = (TextView) findViewById(R.id.iv_add_audio);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        img_change_audio_state = (ImageView) findViewById(R.id.img_change_audio_state);
        img_delete = (ImageView) findViewById(R.id.img_delete);
        img_delete1 = (ImageView) findViewById(R.id.img_delete1);
        tv_catList = (TextView) findViewById(R.id.tv_catList);
        //btn_play.setVisibility(View.GONE);

        iv_add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        random = new Random();
        //getCatList();
        c_id = b.getString("c_id");
        catList = b.getString("catList");
        tv_catList.setText(catList);


        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byteArray = null;
                Bitmap bit = null;
                ((ImageView) findViewById(R.id.iv_image)).setImageBitmap(bit);
                ln_cross.setVisibility(View.GONE);
                iv_image.setVisibility(View.GONE);
            }
        });

        img_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video_name = null;
                ln_cross1.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                video_uri = Uri.EMPTY;
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_ques.getText().toString().equalsIgnoreCase("")){
                    FLAG = "";
                }else{
                    FLAG = "T";
                }


                if (!Uri.EMPTY.equals(video_uri)) {
                    Log.e("Video","Video Uploading");
                    String path = video_uri.toString();
                    Log.e("video_path",""+path);
                    filename = path.substring(path.lastIndexOf("/")+1);
                    uploadVideo(video_uri,"video");
                    FLAG = "V";
                }

                if (!Uri.EMPTY.equals(image_uri)) {
                    Log.e("Image","Image Uploading");
                    String path = image_uri.toString();
                    Log.e("image_uri",""+path);
                    filename = path.substring(path.lastIndexOf("/")+1);
                    uploadVideo(image_uri,"image");
                    FLAG = "I";

                }

                if (!Uri.EMPTY.equals(audio_uri)) {
                    Log.e("Audio","Audio Uploading");
                    String path = audio_uri.toString();
                    Log.e("audio_uri",""+path);
                    filename = path.substring(path.lastIndexOf("/")+1);
                    uploadVideo(audio_uri,"audio");
                    FLAG = "A";
                }

                add_question(edt_ques.getText().toString(),filename);
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



        ln_add_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideoDialog();
            }
        });

        ln_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0){
                        ln_audio.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.green));
                        iv_add_audio.setText("Stop Recording");
                        img_change_audio_state.setImageResource(R.drawable.muted);

                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                        MediaRecorderReady();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        i=i+1;


                }else if(i==1){

                    ln_audio.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    iv_add_audio.setText("Add audio to your question");
                    img_change_audio_state.setImageResource(R.drawable.microphone);

                    mediaRecorder.stop();
                    i=i-1;
                    Toast.makeText(getApplicationContext(), "Recording Completed",
                            Toast.LENGTH_SHORT).show();
                    //btn_play.setVisibility(View.VISIBLE);
                }


            }
        });





 /*       btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(j==0){
                    btn_play.setBackgroundResource(R.drawable.pause_button);
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(AudioSavePathInDevice);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();

                    j=j+1;
                }else if(j==1){

                    if(mediaPlayer != null){
                        btn_play.setBackgroundResource(R.drawable.play_button);
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        MediaRecorderReady();
                        j=j-1;

                    }


                }
            }
        });

*/
        if(flag.equalsIgnoreCase("U")){
            id = getIntent().getExtras().getString("id");

            if(byteArray != null){
                ln_cross.setVisibility(View.VISIBLE);
                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                ((ImageView) findViewById(R.id.iv_image)).setImageBitmap(bit);
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
        startActivity(intent);
        finish();
    }

    public  String getCurrentDate(){

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.e("Date",""+date);
        return  date;

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void showVideoDialog(){
        AlertDialog.Builder videoDialog = new AlertDialog.Builder(this);
        videoDialog.setTitle("Select Action");
        String[] videoDialogItems = {
                "Select Video from gallery",
                "Capture Video from camera"};
        videoDialog.setItems(videoDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        videoDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(galleryIntent, GALLERY);
        //startActivityForResult(Intent.createChooser(galleryIntent, "Select Video"), GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);  // create a file to save the video in specific folder (this works for video only)
        Log.e("Video_Uri",fileUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURED_NEXUS);
    }

    /*private void videoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFile(MEDIA_TYPE_VIDEO);  // create a file to save the video in specific folder (this works for video only)
        Log.e("Video_Uri",fileUri.toString());

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

        // start the Video Capture Intent
        startActivityForResult(intent, REQUEST_VIDEO_CAPTURED_NEXUS);
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
/*
                    uri = getImageUri(getApplicationContext(),bitmap);
                    Log.e("uri",uri.toString());*/

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    //bitmap = getResizedBitmap(bitmap,400);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.e("str",encoded);
                    Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    image_uri = Uri.parse(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY + "/" + image_name);
                   /* String pathh = image_uri.toString();
                    Log.e("image_uri",""+pathh);
                    filename = pathh.substring(pathh.lastIndexOf("/")+1);*/
                    iv_image.setImageBitmap(bitmap);
                    ln_cross.setVisibility(View.VISIBLE);
                    img_base64 = encoded;
                    //Log.e("Bitmap","" + img_base64);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            //Log.e("str",encoded);
            iv_image.setImageBitmap(thumbnail);
            ln_cross.setVisibility(View.VISIBLE);
            saveImage(thumbnail);
            image_uri = Uri.parse(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY + "/" + image_name);
            Log.e("IMAGE-URI", String.valueOf(image_uri));
           /* String path = image_uri.toString();
            Log.e("image_uri",""+path);
            filename = path.substring(path.lastIndexOf("/")+1);*/
            Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
            //img_base64 = encoded;
        }  else if (resultCode == Activity.RESULT_OK) {
            this.videoFromCamera(resultCode, data);
        } else if(resultCode == REQUEST_VIDEO_CAPTURED_NEXUS){
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //byte[] b1 = new byte[0];
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);



        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            image_name = Calendar.getInstance().getTimeInMillis() + ".jpg";
            File f = new File(wallpaperDirectory, image_name);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

   /* public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
*/
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    public void add_question(final String question,final String image) {
        pd.show();
        if (checkConnectivity(getApplicationContext())) {
            StringRequest request = new StringRequest(Request.Method.POST, AppConstants.insertquestion, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                    Log.e("Insertion ->", response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray obj1 = obj.getJSONArray("insertquestion");
                        if(obj1.getJSONObject(0).getString("status").equalsIgnoreCase("Question posted")){
                            Toast.makeText(getApplicationContext(),"Question posted",Toast.LENGTH_SHORT).show();
                            finish();



                            startActivity(new Intent(getApplicationContext(),HomeTabActivity.class));
                            pd.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"Unsucessfull",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                        /*JSONArray jsonArray = obj.getJSONArray("updatedistrict");

                        if(jsonArray!=null){
                            Intent intent = new Intent(getApplicationContext(),HomeTabActivity.class);
                            startActivity(intent);
                        }*/

                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Login_Error", error.toString());
                    pd.dismiss();
                    try {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.e("VolleyError","NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("VolleyError","AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("VolleyError","ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("VolleyError","NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("VolleyError","ParseError");
                        } else {
                        }
                    } catch (Exception e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("lid",Utility.get_uid(getApplicationContext()));
                    map.put("question",question);
                    map.put("image",image);
                    //map.put("pid",p_id);
                    map.put("flag",FLAG);
                    map.put("key",AppConstants.key);
                    map.put("cid",c_id);

                    Log.e("SHA-1 Key",AppConstants.key);
                    Log.e("lid",""+Utility.get_uid(getApplicationContext()));
                    Log.e("question",""+question);
                    Log.e("image",""+image);
                    Log.e("cid",""+c_id);
                    Log.e("flag","" + FLAG);


                    return map;
                }
            };
            Utility.SetvollyTime30Sec(request);
            Utility.getRequestQueue(getApplicationContext()).add(request);

        } else {
            pd.dismiss();
        }
    }

    private void videoFromCamera(int resultCode, Intent data) {

        if(fileUri != null) {
            Log.d("Video Add", "Video saved to:\n" + fileUri);
            Log.d("Video Add" , "Video path:\n" + fileUri.getPath());

            Log.e("hello","hello");
            videoView.setVisibility(View.VISIBLE);
            ln_cross1.setVisibility(View.VISIBLE);
            video_uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/SMW_VIDEO/" + video_name);

           // new VideoCompressor().execute();

          /*  String path = video_uri.toString();
            Log.e("video_uri",""+path);
            filename = path.substring(path.lastIndexOf("/")+1);*/

            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video_uri);
            videoView.requestFocus();
            videoView.start();

          //  Log.d("Video Add", "Video name:\n" + getName(fileUri));
            // use uri.getLastPathSegment() if store in folder
            //use the file Uri.
        }
    }

    public Uri getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted

        if(Environment.getExternalStorageState() != null) {
            // this works for Android 2.2 and above

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/", "SMW_VIDEO");

            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    Log.d("Video Add", "failed to create directory");
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if(type == MEDIA_TYPE_VIDEO) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "VID_"+ timeStamp + ".mp4");

                video_name = "VID_" + timeStamp + ".mp4";

            } else {
                return null;
            }
            Log.e("PackageName",getApplicationContext().getPackageName());
            //return Uri.fromFile(mediaFile);
           return  FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()+".video", mediaFile);
        }

        return null;
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);

        audio_uri = Uri.parse(AudioSavePathInDevice);
        /*String path = audio_uri.toString();
        Log.e("audio_uri",""+path);
        filename = path.substring(path.lastIndexOf("/")+1);*/

    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ActivityAddQuestion.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getApplicationContext(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }







    private void uploadVideo(final Uri uri,final String type) {
        class UploadVideo extends AsyncTask<Void, Void, String> {

//            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // uploading = ProgressDialog.show(ActivityAddQuestion.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // uploading.dismiss();
               /* textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());*/
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(uri.toString(),type);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
        uv.doInBackground();
    }


   /* public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/


    /*private class VideoCompressor extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return MediaController.getInstance().convertVideo(video_uri.toString());
        }

        @Override
        protected void onPostExecute(Boolean compressed) {
            super.onPostExecute(compressed);
            if (compressed) {
                Log.e("Compression", "Compression successfully!");
                Log.e("Compressed File Path", "" + MediaController.cachedFile.getPath());

            }

        }
    }*/



    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

}
