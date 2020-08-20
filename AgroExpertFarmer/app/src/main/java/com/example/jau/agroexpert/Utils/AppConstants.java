package com.example.jau.agroexpert.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.jau.agroexpert.R;

/**
 * Created by Jaina on 17/03/18.
 */

public class AppConstants {

    public  static SharedPreferences preferences;


    //public static final String URL = "http://architectmihirthaker.com/architectmihirthaker.com/gh01/";
    //public static final String URL = "http://pshah.in/";
//    public static final String URL = "http://online.jau.in:2015/agriex/common/";
  //  public static final String URL2 = "http://online.jau.in:2015/agriex/";
    //public static final String URL1 = "http://online.jau.in:2015/agriex/farmer/";
    //public static final String URL3 = "http://online.jau.in:2015/agriex/admin/examples/uploads/";

    public static final String URL = "http://pshah.in/agriex/common/";
    public static final String URL2 = "http://pshah.in/agriex/";
    public static final String URL1 = "http://pshah.in/agriex/farmer/";
    public static final String URL3 = "http://pshah.in/agriex/admin/examples/uploads/";


    //public static final String exlogin = URL + "exlogin.php";
    public static final String insertquestion = URL1 + "insertquestion.php";
    //public static final String insertquestion2 = URL + "insertquestion2.php";
    //public static final String Registration = URL + "Registration.php";

    public static final String Registration1 = URL1 + "Registration1.php"; // used time of EnterOtpActivity


    public static final String key = "4336f0f199bd35b3275e7b77e6471da3985f963b";
    //public static final String DEVELOPER_KEY = "AIzaSyBQw0FnOFpp6M5eA1BXcc7SWWEveL_m7Xs";
    //public static final String DEVELOPER_KEY = "AIzaSyAr87YiJDgwg-JJGTJo94v_f0ZWKJfEnnM";
    public static final String DEVELOPER_KEY = "AIzaSyDH1A0WOrws3nolGBG977VhvSeRo1Ho_hY";


    public static final String logindetails = URL + "logindetails.php";
    public static final String responselid = URL + "responselid.php";
    public static final String selectallquestion = URL + "selectallquestion.php";
    public static final String selectquestion = URL1 + "selectquestion.php";
    public static final String updatedistrict = URL + "updatedistrict.php";
    public static final String otpverify = URL + "otpverify.php"; //Used in LoginActivity
    public static final String category = URL + "category.php";   //used getting category from database
    //public static final String subcategory = URL + "subcategory.php";//used getting subcategory from database
    public static final String subcategory = URL + "subcategory.php";//used getting subcategory from database
    public static final String answeredquestion = URL1 + "answeredquestion.php";
    public static final String unansweredquestion = URL + "unansweredquestion.php";
    public static final String faqs = URL + "faqs.php";
    public static final String searchquestion = URL + "searchquestion.php";
    public static final String starredlist = URL + "starredlist.php";
    public static final String addlike = URL + "addlike.php";
    public static final String faddlike = URL + "faddlike.php";
    public static final String totallike = URL + "totallike.php";
    public static final String addtostar = URL + "addtostar.php";
    public static final String faddtostar = URL + "faddtostar.php";
    public static final String districtlist = URL + "districtlist.php";
    public static final String profiledetails = URL1 + "profiledetails.php";
    public static final String detailunanswered = URL + "detailunanswered.php";
    public static final String detailansweredquestion = URL1 + "detailansweredquestion.php";
    public static final String detailstarredlist = URL + "detailstarredlist.php";
    public static final String faqlist = URL + "searchfaq.php";
    public static final String detailedfaq = URL + "detailfaq.php";
    public static final String welcome = URL1 + "welcome.php";


    public static final String info = URL + "informative.php";
    public static final String infofetchimg = URL3 + "image/";
    public static final String infofetchpdf = URL3 + "pdf/";



    public static final String uploadvideo = URL + "upload.php";
    public static final String uploadaudio = URL + "uploadaudio.php";
    public static final String uploadimage = URL + "uploadimage.php";


    public static final String fetchimageurl = URL2 + "images/";
    public static final String fetchvideourl = URL2 + "video/";
    public static final String fetchaudiourl = URL2 + "audio/";


    public static final String exfetchaudiourl = URL + "images/";

    public static final String district = URL + "district.php";







}
