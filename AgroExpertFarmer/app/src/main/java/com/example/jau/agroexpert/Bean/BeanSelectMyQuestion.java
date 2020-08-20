package com.example.jau.agroexpert.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jaina on 13/03/18.
 */

public class BeanSelectMyQuestion implements Parcelable{

    @SerializedName("qid")
    public String qid;


    @SerializedName("fid")
    public String fid;


   @SerializedName("question")
    public String question;

    @SerializedName("image")
    public String image;

    @SerializedName("contentflag")
    public String contentflag;

    @SerializedName("eximage")
    public String eximage;

    @SerializedName("excontentflag")
    public String excontentflag;

    @SerializedName("answer")
    public String answer;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("category")
    public String category;

    @SerializedName("subcategory")
    public String subcategory;

    @SerializedName("exname")
    public String exname;


    @SerializedName("liked")
    public String liked;


    @SerializedName("stared")
    public String stared;


    @SerializedName("totallikes")
    public String totallikes;


    public BeanSelectMyQuestion(String question, String image,String answer) {
        this.question = question;
        this.image = image;
        this.answer = answer;
    }


    protected BeanSelectMyQuestion(Parcel in) {
        question = in.readString();
        image = in.readString();
        contentflag = in.readString();
        eximage = in.readString();
        excontentflag = in.readString();
        answer = in.readString();
        timestamp = in.readString();
        category = in.readString();
        subcategory = in.readString();
        exname = in.readString();
        qid = in.readString();
        liked = in.readString();
        stared = in.readString();
        totallikes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(image);
        dest.writeString(contentflag);
        dest.writeString(eximage);
        dest.writeString(excontentflag);
        dest.writeString(answer);
        dest.writeString(timestamp);
        dest.writeString(category);
        dest.writeString(subcategory);
        dest.writeString(exname);
        dest.writeString(qid);
        dest.writeString(liked);
        dest.writeString(stared);
        dest.writeString(totallikes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeanSelectMyQuestion> CREATOR = new Creator<BeanSelectMyQuestion>() {
        @Override
        public BeanSelectMyQuestion createFromParcel(Parcel in) {
            return new BeanSelectMyQuestion(in);
        }

        @Override
        public BeanSelectMyQuestion[] newArray(int size) {
            return new BeanSelectMyQuestion[size];
        }
    };
}
